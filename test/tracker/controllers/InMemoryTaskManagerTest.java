package tracker.controllers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;
import tracker.util.StatusTask;
import java.util.ArrayList;

class InMemoryTaskManagerTest {
    @Test
    void checkInMemoryTaskManagerID() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        int idEpic;
        taskManager.add(new Task("Tasktest1","Tasktest1",StatusTask.NEW));
        idEpic = taskManager.add(new Epic("Epictest1","Epictest1",StatusTask.NEW));
        taskManager.add(new Subtask("Subtasktest1","Subtasktest1",StatusTask.NEW,idEpic));
        //сразу проверяю поиск по id, и то что возвращает, то что я отправлял
        Assertions.assertEquals("Tasktest1",taskManager.getTask(1).getTITLE(), "Вернулся не Task)))");
        Assertions.assertEquals("Epictest1",taskManager.getEpic(2).getTITLE(), "Вернулся не Epic)))");
        Assertions.assertEquals("Subtasktest1",taskManager.getSubTask(3).getTITLE(), "Вернулся не Subtask)))");
    }

    @Test
    void checkERORid() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.add(new Task("","",StatusTask.NEW));
        Task task = new Task("","",StatusTask.NEW);
        taskManager.add(task);
        task.updateID(1);
        boolean b = true;
        for (Task task1 : taskManager.getTasks()) {
            if (task1.getID() != 1) {
                b = false;
            }
        }
        Assertions.assertTrue(b,"Не удалось обновить ID");
        //таски будут конфликтовать если нельзя обновить ID
        //если не получиться обновить ID то тогда выведит ошибку
    }

    @Test
    void checkAddTaskInMemoryTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.add(new Task("Task test1","test1",StatusTask.NEW));
        Assertions.assertEquals("Task test1",taskManager.getTask(1).getTITLE(),"Поле TITLE не выводит что должно");
        Assertions.assertEquals("test1",taskManager.getTask(1).getDescription(),"Поле Description не выводит что должно");
        Assertions.assertEquals(StatusTask.NEW,taskManager.getTask(1).getSTATUS(),"Поле STATUS не выводит что должно");
        Assertions.assertEquals(1,taskManager.getTask(1).getID(),"Поле ID не выводит что должно");
        //сравниваю то что отправил и что вытащил
    }

    @Test
    void checkSuptaskID() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        int idEpic;
        Epic epic = new Epic("Epictest1","Epictest1",StatusTask.NEW);
        idEpic = taskManager.add(epic);
        Subtask subtask = new Subtask("Subtasktest1","Subtasktest1", StatusTask.DONE,idEpic);
        taskManager.add(subtask);
        taskManager.removeItemTask(2);
        Assertions.assertEquals(-1,subtask.getID(),"Удаление работает не правильно"); //все ID при удаление становяться -1
    }

    @Test
    void checkEpicIdSubTask() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        int idEpic;
        Epic epic = new Epic("Epictest1","Epictest1",StatusTask.NEW);
        idEpic = taskManager.add(epic);
        Subtask subtask = new Subtask("Subtasktest1","Subtasktest1", StatusTask.DONE,idEpic);
        taskManager.add(subtask);
        Subtask subtask1 = new Subtask("Subtasktest2","Subtasktest2",StatusTask.DONE,idEpic);
        taskManager.add(subtask1);
        Subtask subtask2 = new Subtask("Subtasktest3","Subtasktest3",StatusTask.DONE,idEpic);
        taskManager.add(subtask2);
        Subtask subtask4 = new Subtask("Subtasktest4","Subtasktest4",StatusTask.DONE,idEpic);
        taskManager.add(subtask4);
        taskManager.removeItemTask(3);
        ArrayList<Integer> test = new ArrayList<>();
        test.add(2);
        test.add(4);
        test.add(5);
        for (int i = 0; i < 3; i++) {
            Assertions.assertEquals(test.get(i),taskManager.getItemTask(epic).get(i).getID(),"Старые ID подзадач не удаляються");
        }
    }
}