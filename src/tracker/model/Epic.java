package tracker.model;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> itemIds = new ArrayList<>();

    public void updateITEMIDS(ArrayList<Integer> itemIds1)
    {
        itemIds = itemIds1;
    }

    public ArrayList<Integer> getITEMIDS()
    {
        return itemIds;
    }
}
