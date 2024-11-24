package tracker.file;
import org.junit.jupiter.api.Test;
import tracker.controllers.InMemoryTaskManager;
import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;
import tracker.util.StatusTask;
import java.io.File;
import java.io.IOException;

class FileBackedTaskManagerTest {

    @Test
    void CheckEmptySaveAndLoadFile() {
        try {
            File newFile = File.createTempFile("text", ".txt", new File("temp"));
            FileBackedTaskManager taskManager1 = new FileBackedTaskManager(newFile);
            taskManager1.safeall();
            InMemoryTaskManager inMemoryTaskManager = taskManager1.loadFromFile1(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    void CheckSaveFile() {
        try {
            File newFile = File.createTempFile("text", ".txt", new File("temp"));
            FileBackedTaskManager taskManager1 = new FileBackedTaskManager(newFile);
            int idEpic;
            taskManager1.add(new Task("Titlee","descriptionn",StatusTask.NEW));

            taskManager1.add(new Task("Titlee1", "descriptionn1", StatusTask.DONE));

            idEpic = taskManager1.add(new Epic("Epictest1","Epictest1",StatusTask.NEW));

            taskManager1.add(new Subtask("Subtasktest1","Subtasktest1",StatusTask.DONE, idEpic));

            taskManager1.add(new Subtask("Subtasktest2","Subtasktest2",StatusTask.DONE,idEpic));

            taskManager1.add(new Subtask("Subtasktest3","Subtasktest3", StatusTask.DONE, idEpic));

            idEpic = taskManager1.add(new Epic("Epictest2","Epictest2",StatusTask.NEW));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void CheckLoadFile() {
        try {
            File newFile = File.createTempFile("text", ".txt", new File("temp"));
            FileBackedTaskManager taskManager1 = new FileBackedTaskManager(newFile);
            int idEpic;
            taskManager1.add(new Task("Titlee","descriptionn",StatusTask.NEW));

            taskManager1.add(new Task("Titlee1", "descriptionn1", StatusTask.DONE));

            idEpic = taskManager1.add(new Epic("Epictest1","Epictest1",StatusTask.NEW));

            taskManager1.add(new Subtask("Subtasktest1","Subtasktest1",StatusTask.DONE, idEpic));

            taskManager1.add(new Subtask("Subtasktest2","Subtasktest2",StatusTask.DONE,idEpic));

            taskManager1.add(new Subtask("Subtasktest3","Subtasktest3", StatusTask.DONE, idEpic));

            idEpic = taskManager1.add(new Epic("Epictest2","Epictest2",StatusTask.NEW));

            InMemoryTaskManager inMemoryTaskManager = taskManager1.loadFromFile1(newFile);

            inMemoryTaskManager.add(new Task("test", "test", StatusTask.DONE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}