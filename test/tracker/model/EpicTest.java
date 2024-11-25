package tracker.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tracker.controllers.InMemoryTaskManager;
import tracker.util.StatusTask;

class EpicTest {
    @Test
    void checkToEpicWithTheSameID() {
        Epic epic = new Epic("Test addNewTask","Test addNewTask description",StatusTask.NEW);
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        taskManager.add(epic);
        epic = new Epic("Test addNewTask","Test addNewTask description",StatusTask.NEW);
        epic.updateID(2);
        epic.updateSTATUS(StatusTask.DONE);
        //проверяю что сохранёный таск в InMemoryTaskManager не равен новому таску с таким же айди так как по логике это два разных экземпляра класса
        Assertions.assertNotEquals(epic, taskManager.getEpic(1), "Задачи совпадают.");
    }

    @Test
    void checkEpicInSubtask() {
        Epic epic = new Epic("Test addNewTask","Test addNewTask description",StatusTask.NEW);
        //проверяю что эпик не наследует подкласс, только таким образом можно добавить в мой перегруженый метод
        Assertions.assertFalse(Subtask.class.isAssignableFrom(epic.getClass()),"Эпик можно добавить в подклас");
    }
}