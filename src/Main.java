import tracker.controllers.InMemoryTaskManager;
import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;
import tracker.util.StatusTask;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        int idEpic;

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

        taskManager.getEpic(1);  //1 элемент
        taskManager.getSubTask(3);
        taskManager.getSubTask(2);
        taskManager.getSubTask(3);//2 элемент
        taskManager.getSubTask(4);//3 элемент
        taskManager.getSubTask(2);//4 элемент
        taskManager.getEpic(5);//5 элемент

        System.out.print("Вся история: ");
        for (int i = 0; i < taskManager.getHistory().getHistory().size(); i++) {
            System.out.print(taskManager.getHistory().getHistory().get(i).getID() + ", ");
        }
        System.out.println();
        System.out.print("Удалил одну подзадачу: ");
        taskManager.removeItemTask(3);

        for (int i = 0; i < taskManager.getHistory().getHistory().size(); i++) {
            System.out.print(taskManager.getHistory().getHistory().get(i).getID() + ", ");
        }
        System.out.println();
        System.out.print("Удалил эпик с тремя подзадачами: ");
        taskManager.removeCollectionTask(1);

        for (int i = 0; i < taskManager.getHistory().getHistory().size(); i++) {
            System.out.print(taskManager.getHistory().getHistory().get(i).getID());
        }
    }
}