import java.util.ArrayList;
import java.util.List;

/**
 * Creates an arrayList of Task objects and provides basic operations used by the commands.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list initialised with the given tasks.
     *
     * @param tasks Initial tasks; if null then empty list is used.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = (tasks == null) ? new ArrayList<>() : tasks;
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task list as a list.
     * Used for saving tasks through storage.
     *
     * @return List view of tasks.
     */
    public List<Task> asList() {
        return tasks;
    }
}