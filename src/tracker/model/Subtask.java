package tracker.model;

public class Subtask extends Task {
    private int colId;

    public void updateCOLID(int id)
    {
        colId = id;
    }

    public int getCOLID()
    {
       return colId;
    }
}
