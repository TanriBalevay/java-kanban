package tracker.controllers;


public class Managers {


    public static TaskManager getDefault()
    {
        TaskManager taskManager = new InMemoryTaskManager();
        return taskManager;
    }

    static InMemoryHistoryManager getDefaultHistory()
    {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }
}
