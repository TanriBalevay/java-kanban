package tracker.model;
import tracker.util.StatusTask;


public class Task {


    private int id;  //так как к полям я не могу обращаться из за private я обращаюсь к ним через методы
    private StatusTask status;
    private String title;
    private String description;
    public Task()
    {

    }
    public Task(Task task)
    {
        this.id = task.getID();
        this.status = task.getSTATUS();
        this.title = task.getTITLE();
        this.description = task.getDescription();
    }
    public Task(String title, String description)
    {
        this.title = title;
        this.description = description;
    }
    public void updateID(int ids)
    {
        id = ids;
    }



    public void updateSTATUS(StatusTask status1)
    {
        status = status1;
    }

    public void updateTITLE(String titlee)
    {
        title = titlee;
    }

    public void updateDescription(String descriptionn)
    {
        description = descriptionn;
    }

    public int getID()
    {
        return id;
    }

    public StatusTask getSTATUS()
    {
        return status;
    }

    public String getTITLE()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

}
