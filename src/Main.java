import tracker.util.StatusTask;
import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;
import tracker.controllers.TaskManager;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();
        Epic epic = new Epic();
        int idEpic;
        Subtask subtask = new Subtask();
        Task task = new Task();

        task.updateTITLE("погулять");
        taskManager.add(task, StatusTask.NEW);

        task = new Task();
        task.updateTITLE("поспать");
        taskManager.add(task, StatusTask.NEW);

        epic.updateTITLE("ураться дома");
        idEpic = taskManager.add(epic);
        subtask.updateTITLE("убарть комнату");
        taskManager.add(subtask, idEpic, StatusTask.NEW);
        subtask = new Subtask();
        subtask.updateTITLE("убарть кухню");
        taskManager.add(subtask, idEpic, StatusTask.IN_PROGRESS);

        epic = new Epic();
        epic.updateTITLE("сходить в магазин");
        idEpic = taskManager.add(epic);
        subtask = new Subtask();
        subtask.updateTITLE("купить молоко");
        taskManager.add(subtask, idEpic, StatusTask.DONE);


        printTask(taskManager);
        // не стал замарачиваться с красотой вывода :)

        task = taskManager.getTask(1);
        taskManager.update(task,StatusTask.DONE);
        task = taskManager.getTask(2);
        taskManager.update(task,StatusTask.IN_PROGRESS);

        subtask = taskManager.getSubTask(5);
        taskManager.update(subtask,StatusTask.NEW);


        System.out.println();
        System.out.println();
        System.out.println();


        printTask(taskManager);

        taskManager.removeSimpleTask(1);
        taskManager.removeCollectionTask(3);

        System.out.println();
        System.out.println();
        System.out.println();

        printTask(taskManager);
    }

    public static void printTask(TaskManager taskManager) {
        int i, j;

        for (i = 0; i < taskManager.getTasks().size(); i++) { //вывод простых задач и их статусов
            System.out.print(taskManager.getTasks().get(i).getTITLE());
            System.out.println(" " + taskManager.getTasks().get(i).getSTATUS());
        }


        for (i = 0; i < taskManager.getEpics().size(); i++) {  //вывод эпик задач и их под задач со статусами
            System.out.print(taskManager.getEpics().get(i).getTITLE());
            System.out.println(" " + taskManager.getEpics().get(i).getSTATUS());

            for (j = 0; j < taskManager.getItemTask(taskManager.getEpics().get(i)).size(); j++) {
                System.out.print(taskManager.getItemTask(taskManager.getEpics().get(i)).get(j).getTITLE());
                System.out.println("     " + taskManager.getItemTask(taskManager.getEpics().get(i)).get(j).getSTATUS());
            }
        }
    }
}