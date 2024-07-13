package tracker.controllers;


public class Managers {


    public static TaskManager getDefault()
    {
        TaskManager taskManager = new InMemoryTaskManager();
        return taskManager;
    }

    static HistoryManager getDefaultHistory()
    {
        HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        return inMemoryHistoryManager;
    }
}
