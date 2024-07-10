import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tracker.controllers.InMemoryTaskManager;
import tracker.controllers.Managers;
import tracker.controllers.TaskManager;
import tracker.model.Task;
import tracker.util.StatusTask;
import tracker.model.Epic;
import tracker.model.Subtask;

import java.util.ArrayList;

public class ManagerTest {

    @Test
    void checkClassManagers() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        //Если в метод getDefault класса Managers внесли изменения то уже будет возвращаться не то что требовало тз
        //И можно считать что утилитарный класс возвращает не готовый экземпляр
        Assertions.assertTrue(inMemoryTaskManager instanceof InMemoryTaskManager, "В методе getDefault класса Managers внесли изменения");
    }

    @Test
    void checkInMemoryTaskManagerID(){
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        int idEpic;

        Task task = new Task("Tasktest1","Tasktest1");
        taskManager.add(task, StatusTask.NEW);

        Epic epic = new Epic("Epictest1","Epictest1");
        idEpic = taskManager.add(epic);

        Subtask subtask = new Subtask("Subtasktest1","Subtasktest1");
        taskManager.add(subtask, idEpic, StatusTask.NEW);

        //сразу проверяю поиск по id, и то что возвращает, то что я отправлял

        Assertions.assertEquals("Tasktest1",taskManager.getTask(1).getTITLE(), "Вернулся не Task)))");
        Assertions.assertEquals("Epictest1",taskManager.getEpic(2).getTITLE(), "Вернулся не Epic)))");
        Assertions.assertEquals("Subtasktest1",taskManager.getSubTask(3).getTITLE(), "Вернулся не Subtask)))");
    }

    @Test
    void checkERORid(){
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task = new Task("","");
        taskManager.add(task, StatusTask.NEW);

        task = new Task("","");
        taskManager.add(task, StatusTask.NEW);
        task.updateID(1);

        boolean b = true;

        for(Task task1 : taskManager.getTasks()){
            if(task1.getID() != 1)
            {
                b = false;
            }
        }

        Assertions.assertTrue(b,"Не удалось обновить ID");

        //таски будут конфликтовать если нельзя обновить ID
        //если не получиться обновить ID то тогда выведит ошибку
    }

    @Test
    void checkAddTaskInMemoryTaskManager (){
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task = new Task("Task test1","test1");
        taskManager.add(task, StatusTask.NEW);

        Assertions.assertEquals("Task test1",taskManager.getTask(1).getTITLE(),"Поле TITLE не выводит что должно");
        Assertions.assertEquals("test1",taskManager.getTask(1).getDescription(),"Поле Description не выводит что должно");
        Assertions.assertEquals(StatusTask.NEW,taskManager.getTask(1).getSTATUS(),"Поле STATUS не выводит что должно");
        Assertions.assertEquals(1,taskManager.getTask(1).getID(),"Поле ID не выводит что должно");

        //сравниваю то что отправил и что вытащил
    }

    @Test
    void checkHistoryManager(){
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task = new Task("Task test1","test1");
        taskManager.add(task, StatusTask.NEW);

         task = new Task("Task test2","test2");
        taskManager.add(task, StatusTask.NEW);

         task = new Task("Task test3","test3");
        taskManager.add(task, StatusTask.NEW);

         task = new Task("Task test4","test4");
        taskManager.add(task, StatusTask.NEW);

         task = new Task("Task test5","test5");
        taskManager.add(task, StatusTask.NEW);

        ArrayList <Integer> checkID = new ArrayList<>();

        task = new Task();
        task = taskManager.getTask(1);
        checkID.add(1);
        task = taskManager.getTask(5);
        checkID.add(5);
        task = taskManager.getTask(3);
        checkID.add(3);
        task = taskManager.getTask(4);
        checkID.add(4);
        task = taskManager.getTask(2);
        checkID.add(2);


        for (int i = 0; i < taskManager.historyManager.getHistory().size(); i++) {
            Assertions.assertEquals(checkID.get(i),taskManager.historyManager.getHistory().get(i).getID(),"История работает неправильно");
        }

        //При обращение к getTask я сам сохранил историю посещения и сравнил её с той что храниться в InMemoryTaskManager
    }
}
