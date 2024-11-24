package tracker.model;

import tracker.util.TypeTask;

public class Subtask extends Task {
    private int colId;

    public Subtask(String title, String description) {
        super(title, description);
    }

    public void updateCOLID(int id) {
        colId = id;
    }

    @Override
    public Integer getCOLID() {
       return colId;
    }

    @Override
    public TypeTask getName() {
        return TypeTask.SUBTASK;
    }
}
