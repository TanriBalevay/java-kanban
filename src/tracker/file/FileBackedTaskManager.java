package tracker.file;
import tracker.controllers.HistoryManager;
import tracker.controllers.InMemoryTaskManager;
import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;
import tracker.util.StatusTask;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File filename;
    private HistoryManager historyManager;
    private InMemoryTaskManager taskManager;

    public FileBackedTaskManager(File filename) {
        this.filename = filename;
    }

    public FileBackedTaskManager(HistoryManager historyManager, File filename) {
        this.historyManager = historyManager;
        this.filename = filename;
    }

    public FileBackedTaskManager(InMemoryTaskManager taskManager, File filename) {
        this.taskManager = taskManager;
        this.filename = filename;
    }

    public static FileBackedTaskManager loadFromFile(File filename) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(filename);
        fileBackedTaskManager.loadFromFile(filename);
        return fileBackedTaskManager;
    }

    public void safeall() {
        save();
    }

    @Override
    public void add(Task simpletask, StatusTask status) {
        super.add(simpletask,status);
        save();
    }

    @Override
    public int add(Epic collection) {

        save();
        return collection.getID();
    }

    @Override
    public void add(Subtask item, int ids, StatusTask status) {
        super.add(item,ids,status);
        save();
    }

    public void save() {
       try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {
           writer.write("id,type,name,status,description,epic");
           writer.newLine();
           for (Task task : taskManager.getTasks()) {
               writer.write(toStringich(task));
               writer.newLine();
           }

           for (Task task : taskManager.getEpics()) {
               writer.write(toStringich(task));
               writer.newLine();
           }

           for (Task task : taskManager.getSubtasks()) {
               writer.write(toStringich(task));
               writer.newLine();
           }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка в файле: " + filename.getAbsolutePath(), e);
        }
    }

    private String toStringich(Task task) {
        if (task.getCOLID() == null) {
            return String.valueOf(task.getID()) + "," + task.getName() + "," + task.getTITLE() + "," + task.getSTATUS().toString() + "," + task.getDescription();
        } else {
            return String.valueOf(task.getID()) + "," + task.getName() + "," + task.getTITLE() + "," + task.getSTATUS().toString() + "," + task.getDescription() + "," + task.getCOLID();
        }
    }
}
