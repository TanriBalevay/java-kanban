package tracker.file;
import tracker.controllers.InMemoryTaskManager;
import tracker.exception.ManagerSaveException;
import tracker.model.Epic;
import tracker.model.Subtask;
import tracker.model.Task;
import tracker.util.StatusTask;
import tracker.util.TypeTask;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File filename;

    public FileBackedTaskManager(File filename) {
        this.filename = filename;
    }

    public static FileBackedTaskManager loadFromFile1(File filename) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(filename);
        fileBackedTaskManager.loadFromFile();
        return fileBackedTaskManager;
    }

    private void loadFromFile() {
        int maxtId = 0;
        try (final FileReader in = new FileReader(filename, StandardCharsets.UTF_8); final BufferedReader reader = new BufferedReader(in)) {
            reader.readLine();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                final Task task = fromString(line);
                if (task.getName() == TypeTask.TASK) {
                    uppdateMapTask(task);
                } else if (task.getName() == TypeTask.EPIC) {
                    uppdateMapEpic((Epic) task);
                } else if (task.getName() == TypeTask.SUBTASK) {
                    uppdateMapSubtask((Subtask) task);
                }
                if (maxtId < task.getID()) {
                    maxtId = task.getID();
                    uppdatenextId(maxtId + 1);
                }
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка в файле: " + filename.getName(), e);
        }

    }

    private Task fromString(String value) {
        final String[] splits = value.split(",");
        Integer id = Integer.valueOf(splits[0]);
        String name = splits[2];
        String description = splits[4];
        StatusTask status = StatusTask.valueOf(splits[3]);
        Integer epicID = null;
        if (splits.length > 5) {
            epicID = Integer.valueOf(splits[splits.length - 1]);
        }

        TypeTask typeTask = TypeTask.valueOf(splits[1]);
        Task task = null;
        switch (typeTask) {
            case TASK:
                task = new Task(name, description, status);
                task.updateID(id);
                break;
            case SUBTASK:
                task = new Subtask(name, description, status, epicID);
                task.updateID(id);
                break;
            case EPIC:
                task = new Epic(name, description, status);
                task.updateID(id);
                break;
        }
        return task;
    }

    public void safeall() {
        save();
    }

    @Override
    public void add(Task simpletask) {
        super.add(simpletask);
        save();
    }

    @Override
    public int add(Epic collection) {
        super.add(collection);
        save();
        return collection.getID();
    }

    @Override
    public void add(Subtask item) {
        super.add(item);
        save();
    }

    public void save() {
       try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {
           writer.write("id,type,name,status,description,epic");
           writer.newLine();
           for (Task task : getTasks()) {
               writer.write(toStringich(task));
               writer.newLine();
           }

           for (Task task : getEpics()) {
               writer.write(toStringich(task));
               writer.newLine();
           }

           for (Task task : getSubtasks()) {
               writer.write(toStringich(task));
               writer.newLine();
           }
        } catch (IOException e) {
           throw new ManagerSaveException("Ошибка в файле: " + filename.getName(), e);
        }
    }

    private String toStringich(Task task) {
        if (task.getCOLID() == null) {
            return String.valueOf(task.getID()) + "," + task.getName() + "," + task.getTITLE() + "," + task.getSTATUS().toString() + "," + task.getDescription();
        } else return String.valueOf(task.getID()) + "," + task.getName() + "," + task.getTITLE() + "," + task.getSTATUS().toString() + "," + task.getDescription() + "," + task.getCOLID();
    }
}
