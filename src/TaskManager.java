import java.util.HashMap;

public class TaskManager {
    int nextId = 1;
    HashMap<Integer, Task> simpleTask = new HashMap<>();
    HashMap<Integer, Epic> collectionTask = new HashMap<>();
    HashMap<Integer, Subtask> itemTask = new HashMap<>();

   private void generateNextId() {
       nextId++;
   }


    public void add(Task simpletask, StatusTask status) {
        simpletask.id = nextId;
        simpletask.status = status;
        generateNextId();
        simpleTask.put(simpletask.id, simpletask);
    }

    public int add(Epic collection, StatusTask status) {
        collection.id = nextId;
        collection.status = status;
        generateNextId();
        collectionTask.put(collection.id, collection);
        return collection.id;
    }

    public void add(Subtask item,int ids, StatusTask status) {
        item.id = nextId;
        item.status = status;
        item.colId = ids;
        generateNextId();
        itemTask.put(item.id, item);

        Epic collection = collectionTask.get(item.colId);
        collection.itemIds.add(item.id);
    }

    public void update(Task simpleTasks, StatusTask status) {
        simpleTasks.status = status;
        simpleTask.put(simpleTasks.id, simpleTasks);
    }

    public void update(Epic collectionTasks) {
        collectionTask.put(collectionTasks.id, collectionTasks);
    }

    public void update(Subtask item, StatusTask status) {
        item.status = status;
        itemTask.put(item.id, item);

        Epic collection = collectionTask.get(item.colId);
        int statusDONE = 0;
        int statusNEW = 0;
        for (int i=0; i < collection.itemIds.size(); i++)
        {
            item = itemTask.get(collection.itemIds.get(i));
            if (item.status == StatusTask.DONE)
            {
                statusDONE += 1;
            }
            if (item.status == StatusTask.NEW)
            {
                statusNEW += 1;
            }
        }
        if (collection.itemIds.size() == statusDONE)
        {
            collection.status = StatusTask.DONE;
        }else
        {
            if(collection.itemIds.size() == statusNEW)
            {
                collection.status = StatusTask.NEW;
            } else {
                collection.status = StatusTask.IN_PROGRESS;
            }
        }
    }

    public HashMap<Integer, Task> getSimpleTask()  // Получение списка всех простых задач
    {
        return simpleTask;
    }

    public HashMap<Integer, Epic> getCollectionTask() // Получение списка всех эпик задач
    {
        return collectionTask;
    }

    public HashMap<Integer, Subtask> getItemTask() // Получение списка всех подзадач
    {
        return itemTask;
    }

    public HashMap<Integer, Subtask> getItemTask(Epic collectionTasks) // Получение списка всех подзадач определёного эпика
    {
        HashMap<Integer, Subtask> itemTasks = new HashMap<>();
        for(int i=0; i < collectionTasks.itemIds.size(); i++)
        {
            itemTasks.put(collectionTasks.itemIds.get(i), itemTask.get(collectionTasks.itemIds.get(i)));
        }

        return itemTasks;
    }

    public Task getTask(int id) // Получение по идентификатору.
    {
        return simpleTask.get(id);
    }

    public Epic getEpic(int id) // Получение по идентификатору.
    {
        return collectionTask.get(id);
    }

    public Subtask getSubTask(int id) // Получение по идентификатору.
    {
        return itemTask.get(id);
    }

    public void clearTask () //Удаление всех задач
    {
        simpleTask.clear();
    }

    public void clearEpic () //Удаление всех эпик задач и так как эпики удалены то подзадачи тоже
    {
        collectionTask.clear();
        itemTask.clear();
    }

    public void clearSubtask () //Удаление всех подзадач эпики остаються пустыми и NEW
    {
        for(Epic epics : collectionTask.values())
        {
            epics.status = StatusTask.NEW;
        }
        itemTask.clear();
    }

    public void removeSimpleTask(int id)  // Удаление по идентификатору.
    {
        simpleTask.remove(id);
    }

    public void removeCollectionTask(int id) // Удаление по идентификатору эпик задач и при этом всех его подзадач
    {
        for(Subtask subtask : itemTask.values())
        {
          if(subtask.colId == id)
          {
              for(int idss : itemTask.keySet())
              {
                  if(itemTask.get(idss) == subtask) {
                      itemTask.remove(idss);
                  }
              }

          }
        }
        collectionTask.remove(id);
    }

    public void removeItemTask(int id) // Удаление по идентификатору подзадачи.
    {
        itemTask.remove(id);
    }
}
