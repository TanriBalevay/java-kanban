package tracker.controllers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tracker.model.Task;
import tracker.util.StatusTask;
import java.util.ArrayList;


class InMemoryHistoryManagerTest {
    @Test
    void checkHistoryManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.add(new Task("Task test1","test1",StatusTask.NEW));
        taskManager.add(new Task("Task test2","test2",StatusTask.NEW));
        taskManager.add(new Task("Task test3","test3",StatusTask.NEW));
        taskManager.add(new Task("Task test4","test4",StatusTask.NEW));
        taskManager.add(new Task("Task test5","test5",StatusTask.NEW));
        ArrayList<Integer> checkID = new ArrayList<>();
        taskManager.getTask(1);
        taskManager.getTask(5);// 1 элемент
        taskManager.getTask(4);
        taskManager.getTask(1);
        taskManager.getTask(4);// 2 элемент
        taskManager.getTask(1);// 3 элемент
        checkID.add(5);
        checkID.add(4);
        checkID.add(1);
        for (int i = 0; i < taskManager.getHistory().getHistory().size(); i++) {
            //System.out.println(taskManager.getHistory().getHistory().get(i).getID());
            Assertions.assertEquals(checkID.get(i),taskManager.getHistory().getHistory().get(i).getID(),"История работает неправильно");
        }
        //При обращение к getTask я сам сохранил историю посещения и сравнил её с той что храниться в InMemoryTaskManager
    }
}