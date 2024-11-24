import tracker.controllers.InMemoryTaskManager;
import tracker.file.FileBackedTaskManager;
import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;
import tracker.util.StatusTask;
import java.io.File;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        int idEpic;
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task = new Task("Titlee","descriptionn");
        FileBackedTaskManager taskManager1 = new FileBackedTaskManager(taskManager, new File("TasksFile.csv"));
        taskManager.add(task, StatusTask.NEW);

        task = new Task("Titlee1","descriptionn1");
        taskManager.add(task, StatusTask.DONE);

        Epic epic = new Epic("Epictest1","Epictest1");
        epic.updateSTATUS(StatusTask.NEW);
        idEpic = taskManager.add(epic);

        Subtask subtask = new Subtask("Subtasktest1","Subtasktest1");
        taskManager.add(subtask, idEpic, StatusTask.DONE);

        subtask = new Subtask("Subtasktest2","Subtasktest2");
        taskManager.add(subtask, idEpic, StatusTask.DONE);

        subtask = new Subtask("Subtasktest3","Subtasktest3");
        taskManager.add(subtask, idEpic, StatusTask.DONE);

        epic = new Epic("Epictest2","Epictest2");
        epic.updateSTATUS(StatusTask.NEW);
        taskManager.add(epic);

        taskManager1.safeall();
    }
}