package tracker.controllers;

import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;
import tracker.util.StatusTask;

import java.util.ArrayList;

public interface TaskManager {
    void add(Task simpletask, StatusTask status);

    int add(Epic collection);

    void add(Subtask item, int ids, StatusTask status);

    void update(Task simpleTasks, StatusTask status);

    void update(Epic collectionTasks);

    void update(Subtask item, StatusTask status);

    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<Subtask> getSubtasks();

    ArrayList<Subtask> getItemTask(Epic collectionTasks) // Получение списка всех подзадач определёного эпика
    ;

    Task getTask(int id) // Получение по идентификатору.
    ;

    Epic getEpic(int id) // Получение по идентификатору.
    ;

    Subtask getSubTask(int id) // Получение по идентификатору.
    ;

    void clearTask() //Удаление всех задач
    ;

    void clearEpic() //Удаление всех эпик задач и так как эпики удалены то подзадачи тоже
    ;

    void clearSubtask() //Удаление всех подзадач эпики остаються пустыми и NEW
    ;

    void removeSimpleTask(int id)  // Удаление по идентификатору.
    ;

    void removeCollectionTask(int id) // Удаление по идентификатору эпик задач и при этом всех его подзадач
    ;

    void removeItemTask(int id) // Удаление по идентификатору подзадачи.
    ;
}
