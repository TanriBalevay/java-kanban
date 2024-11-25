package tracker.model;
import tracker.util.StatusTask;
import tracker.util.TypeTask;


public class Task {
    private int id;
    private StatusTask status;
    private String title;
    private String description;

    public Task() {

    }

    public Task(Task task) {
        this.id = task.getID();
        this.status = task.getSTATUS();
        this.title = task.getTITLE();
        this.description = task.getDescription();
    }

    public Task(String title, String description,StatusTask status) {
        this.status = status;
        this.title = title;
        this.description = description;
    }

    public void updateID(int ids) {
        id = ids;
    }

    public void updateSTATUS(StatusTask status1) {
        status = status1;
    }

    public void updateTITLE(String titlee) {
        title = titlee;
    }

    public void updateDescription(String descriptionn) {
        description = descriptionn;
    }

    public int getID() {
        return id;
    }

    public StatusTask getSTATUS() {
        return status;
    }

    public String getTITLE() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCOLID() {
        return null;
    }

    public TypeTask getName() {
        return TypeTask.TASK;
    }

}
