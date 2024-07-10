package tracker.model;

public class Subtask extends Task {
    private int colId;

    public Subtask(String title, String description) {
        super(title, description);
    }

    public void updateCOLID(int id)
    {
        colId = id;
    }

    public int getCOLID()
    {
       return colId;
    }
}
