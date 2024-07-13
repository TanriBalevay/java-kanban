package tracker.controllers;

import tracker.util.StatusTask;
import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;


import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private HistoryManager historyManager = Managers.getDefaultHistory();
    private int nextId = 1;
    private HashMap<Integer, Task> simpleTask = new HashMap<>();
    private HashMap<Integer, Epic> collectionTask = new HashMap<>();
    private HashMap<Integer, Subtask> itemTask = new HashMap<>();

   private void generateNextId() {
       nextId++;
   }


    @Override
    public void add(Task simpletask, StatusTask status) {
        simpletask.updateID(nextId);
        simpletask.updateSTATUS(status);
        simpleTask.put(nextId, simpletask);
        generateNextId();
    }

    @Override
    public int add(Epic collection) {
        collection.updateID(nextId);
        collection.updateSTATUS(StatusTask.NEW);  // При добавление эпика он поступает пустым и априоре должен быть NEW
        collectionTask.put(nextId, collection);
        generateNextId();
        return collection.getID();
    }

    @Override
    public void add(Subtask item, int ids, StatusTask status) {
        item.updateID(nextId);
        item.updateSTATUS(status);
        item.updateCOLID(ids);
        itemTask.put(nextId, item);
        generateNextId();

        Epic collection = collectionTask.get(item.getCOLID());
        collection.getITEMIDS().add(item.getID());

        checkStatusEpic(item.getCOLID());  //при добавление подзадачи тоже происходить проверка на статуст эпика
    }

    @Override
    public void update(Task simpleTasks, StatusTask status) {
        simpleTasks.updateSTATUS(status);
        simpleTask.put(simpleTasks.getID(), simpleTasks);
    }

    @Override
    public void update(Epic collectionTasks) {
        collectionTask.put(collectionTasks.getID(), collectionTasks);
    }

    @Override
    public void update(Subtask item, StatusTask status) {
        item.updateSTATUS(status);
        itemTask.put(item.getID(), item);

        checkStatusEpic(item.getCOLID());
    }

    private void checkStatusEpic (int colId)  //вынес отдельно просчёт статуса эпика чтобы не дублировать его в коде
    {
        Subtask item;
        Epic collection = collectionTask.get(colId);
        int statusDONE = 0;
        int statusNEW = 0;
        for (int i=0; i < collection.getITEMIDS().size(); i++)
        {
            item = itemTask.get(collection.getITEMIDS().get(i));
            if (item.getSTATUS() == StatusTask.DONE)
            {
                statusDONE += 1;
            }
            if (item.getSTATUS() == StatusTask.NEW)
            {
                statusNEW += 1;
            }
        }
        if (collection.getITEMIDS().size() == statusDONE)
        {
            collection.updateSTATUS(StatusTask.DONE);
        }else
        {
            if(collection.getITEMIDS().size() == statusNEW)
            {
                collection.updateSTATUS(StatusTask.NEW);
            } else {
                collection.updateSTATUS(StatusTask.IN_PROGRESS);
            }
        }
    }

    @Override
    public ArrayList<Task> getTasks() {             // Получение списка всех простых задач
        return new ArrayList<>(simpleTask.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {             // Получение списка всех эпик задач
        return new ArrayList<>(collectionTask.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {             // Получение списка всех подзадач
        return new ArrayList<>(itemTask.values());
    }

    @Override
    public ArrayList<Subtask> getItemTask(Epic collectionTasks) // Получение списка всех подзадач определёного эпика
    {
        ArrayList<Subtask> itemTasks = new ArrayList<>();
        for(int i=0; i < collectionTasks.getITEMIDS().size(); i++)
        {
            itemTasks.add(itemTask.get(collectionTasks.getITEMIDS().get(i)));
        }

        return itemTasks;
    }

    @Override
    public Task getTask(int id) // Получение по идентификатору.
    {
        historyManager.add(simpleTask.get(id));
        return simpleTask.get(id);
    }

    @Override
    public Epic getEpic(int id) // Получение по идентификатору.
    {
        historyManager.add(collectionTask.get(id));
        return collectionTask.get(id);
    }

    @Override
    public Subtask getSubTask(int id) // Получение по идентификатору.
    {
        historyManager.add(itemTask.get(id));
        return itemTask.get(id);
    }

    @Override
    public void clearTask() //Удаление всех задач
    {
        simpleTask.clear();
    }

    @Override
    public void clearEpic() //Удаление всех эпик задач и так как эпики удалены то подзадачи тоже
    {
        collectionTask.clear();
        itemTask.clear();
    }

    @Override
    public void clearSubtask() //Удаление всех подзадач эпики остаються пустыми и NEW
    {
        for(Epic epics : collectionTask.values())
        {
            epics.updateSTATUS(StatusTask.NEW);
            epics.deleteArraylist();
        }
        itemTask.clear();
    }

    @Override
    public void removeSimpleTask(int id)  // Удаление по идентификатору.
    {
        simpleTask.remove(id);
    }

    @Override
    public void removeCollectionTask(int id) // Удаление по идентификатору эпик задач и при этом всех его подзадач
    {
        for(int idSub : collectionTask.get(id).getITEMIDS())
        {
           itemTask.remove(idSub);
        }
        collectionTask.remove(id);
    }

    @Override
    public void removeItemTask(int id) // Удаление по идентификатору подзадачи.
    {
        Subtask subtasks = itemTask.get(id);
        int ids = subtasks.getCOLID();
        itemTask.remove(id);

        checkStatusEpic(ids);
    }

    @Override
    public HistoryManager getHistory(){
       return historyManager;
    }
}
