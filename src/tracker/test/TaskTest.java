import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tracker.util.StatusTask;
import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;
import tracker.controllers.InMemoryTaskManager;


public class TaskTest {

    @Test
    void checkToTaskWithTheSameID() {
        Task task = new Task("Test addNewTask","Test addNewTask description");
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.add(task, StatusTask.NEW);

        task = new Task("Test addNewTask","Test addNewTask description");
        task.updateID(1);
        task.updateSTATUS(StatusTask.NEW);

        //проверяю что сохранёный таск в InMemoryTaskManager не равен новому таску с таким же айди

        //так как по логике это два разных экземпляра класса
        //Экземпляр класса это то же что и объект
        //У объекта класса есть своё обозначение через @ для того чтобы их различать и вот по тз мне надо сравнить объекты Task что они равны
        //Но они изначальны разные даже если у них все поля будут одинаковые

        Assertions.assertNotEquals(task, taskManager.getTask(1), "Задачи совпадают.");
    }

    @Test
    void checkToEpicWithTheSameID (){
        Epic epic = new Epic("Test addNewTask","Test addNewTask description");
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.add(epic);

        epic = new Epic("Test addNewTask","Test addNewTask description");
        epic.updateID(2);
        epic.updateSTATUS(StatusTask.DONE);

        //проверяю что сохранёный таск в InMemoryTaskManager не равен новому таску с таким же айди так как по логике это два разных экземпляра класса

        Assertions.assertNotEquals(epic, taskManager.getEpic(1), "Задачи совпадают.");
    }

    @Test
    void checkToSubtaskWithTheSameID (){
        Epic epic = new Epic("Test addNewTask","Test addNewTask description");
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        int add = taskManager.add(epic);

        Subtask subtask = new Subtask("Test addNewTask","Test addNewTask description");
        taskManager.add(subtask,add,StatusTask.NEW);

        subtask = new Subtask("Test addNewTask","Test addNewTask description");
        subtask.updateID(2);
        subtask.updateCOLID(add);
        subtask.updateSTATUS(StatusTask.NEW);

        //проверяю что сохранёный таск в InMemoryTaskManager не равен новому таску с таким же айди так как по логике это два разных экземпляра класса

        Assertions.assertNotEquals(subtask, taskManager.getSubTask(2), "Задачи совпадают.");
    }

    @Test
    void checkEpicInSubtask (){
        Epic epic = new Epic("Test addNewTask","Test addNewTask description");

        //проверяю что эпик не наследует подкласс, только таким образом можно добавить в мой перегруженый метод

        Assertions.assertFalse(Subtask.class.isAssignableFrom(epic.getClass()),"Эпик можно добавить в подклас");
    }

    @Test
    void checkSubtaskInEpic (){
        Subtask subtask = new Subtask("Test addNewTask","Test addNewTask description");

        //проверяю что подкласс не наследует эпик, только таким образом можно добавить в мой перегруженый метод

        Assertions.assertFalse(Epic.class.isAssignableFrom(subtask.getClass()),"Подклас можно добавить в эпик");
    }
}
