package tracker.controllers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ManagersTest {
    @Test
    void checkClassManagers() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        //Если в метод getDefault класса Managers внесли изменения то уже будет возвращаться не то что требовало тз
        //И можно считать что утилитарный класс возвращает не готовый экземпляр
        Assertions.assertTrue(inMemoryTaskManager instanceof InMemoryTaskManager, "В методе getDefault класса Managers внесли изменения");
    }
}