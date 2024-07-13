package tracker.controllers;

import tracker.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private ArrayList <Task> history = new ArrayList<>();


    @Override
    public void add(Task task)
    {
        if(task != null) {
            if (history.size() >= 10) {
                history.remove(0);
            }
            history.add(task);
        }
    }


    @Override
    public ArrayList<Task> getHistory() {

        ArrayList<Task> historyCopy = new ArrayList<Task>(history.size());
        for(Task task: history){
            historyCopy.add(new Task(task));
        }

        return historyCopy;
    }
}
