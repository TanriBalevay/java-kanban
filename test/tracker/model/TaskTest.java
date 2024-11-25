package tracker.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tracker.controllers.InMemoryTaskManager;
import tracker.file.FileBackedTaskManager;
import tracker.util.StatusTask;
import java.io.File;

class TaskTest {

    @Test
    void checkToTaskWithTheSameID() {
        Task task = new Task("Test addNewTask","Test addNewTask description",StatusTask.NEW);

        InMemoryTaskManager taskManager1 = new InMemoryTaskManager();
        taskManager1.add(task);
        task = new Task("Test addNewTask","Test addNewTask description",StatusTask.DONE);
        task.updateID(1);
        task.updateSTATUS(StatusTask.NEW);
        //проверяю что сохранёный таск в InMemoryTaskManager не равен новому таску с таким же айди
        //так как по логике это два разных экземпляра класса
        //Экземпляр класса это то же что и объект
        //У объекта класса есть своё обозначение через @ для того чтобы их различать и вот по тз мне надо сравнить объекты Task что они равны
        //Но они изначальны разные даже если у них все поля будут одинаковые
        Assertions.assertNotEquals(task, taskManager1.getTask(1), "Задачи совпадают.");
    }

}