package tracker.controllers;
import java.util.*;
import tracker.model.Task;

public class InMemoryHistoryManager implements HistoryManager {

    private static class Node {
        Task item;
        Node next;
        Node prev;

        Node(Node prev, Task element, Node next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private HashMap<Integer, Node> history = new HashMap<>();
    Node first;
    Node last;

    private Integer size;

    @Override
    public void add(Task task) {
        Node node = history.get(task.getID());
        size = history.size();
        history.put(task.getID(), linkLast(task));
        if (size == history.size()) {
            removeNode(node);
        }
    }

    @Override
    public void remove(int id) {
        Node node = history.get(id);
        removeNode(node);
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> historyCopy = new ArrayList<>();
        Node current = first;
        while (current != null) {
            historyCopy.add(current.item);
            current = current.next;
        }

        return historyCopy;
    }

    private Node linkLast(Task task) {
        final Node l = last;
        final Node newNode = new Node(l, task, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        return newNode;
    }

    private void removeNode(Node node) {

        if (node != null) {
            final Node next = node.next;
            final Node prev = node.prev;
            if (prev == null) {
                first = next;
            } else {
                prev.next = next;
                node.prev = null;
            }
            if (next == null) {
                last = prev;
            } else {
                next.prev = prev;
                node.next = null;
            }
            node.item = null;
        }
    }

    @Override
    public void clearID(int id) {
        Node node = history.get(id);
        removeNode(node);
        history.remove(id);
    }
}
