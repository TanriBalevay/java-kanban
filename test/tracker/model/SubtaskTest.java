package tracker.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tracker.controllers.InMemoryTaskManager;
import tracker.util.StatusTask;

class SubtaskTest {
    @Test
    void checkToSubtaskWithTheSameID() {
        Epic epic = new Epic("Test addNewTask","Test addNewTask description", StatusTask.NEW);
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        int add = taskManager.add(epic);
        Subtask subtask = new Subtask("Test addNewTask","Test addNewTask description", StatusTask.NEW,add);
        taskManager.add(subtask);
        subtask = new Subtask("Test addNewTask","Test addNewTask description", StatusTask.NEW,add);
        subtask.updateID(2);
        subtask.updateCOLID(add);
        subtask.updateSTATUS(StatusTask.NEW);
        //проверяю что сохранёный таск в InMemoryTaskManager не равен новому таску с таким же айди так как по логике это два разных экземпляра класса
        Assertions.assertNotEquals(subtask, taskManager.getSubTask(2), "Задачи совпадают.");
    }

    @Test
    void checkSubtaskInEpic() {
        int add = 1;
        Subtask subtask = new Subtask("Test addNewTask","Test addNewTask description", StatusTask.NEW,add);
        //проверяю что подкласс не наследует эпик, только таким образом можно добавить в мой перегруженый метод
        Assertions.assertFalse(Epic.class.isAssignableFrom(subtask.getClass()),"Подклас можно добавить в эпик");
    }
}