/**
 * Class that defines the behaviour for adding tasks to the task list.
 * Adds the task to the task list and then saves it to the storge file.
 */

public class AddCommand extends Command {
    private final Task taskToAdd;

    /**
     * method that adds an individual task to the tasklist.
     *
     * @param taskToAdd a task of type Task that will be added to the arrayList of tasks.
     */
    public AddCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

    /**
     * method that adds the task, stores it in the text file, and prints the
     * added taks to the screen to show that it has been added.
     * @param tasks Current task list
     * @param ui User interface handler
     * @param storage Storage handler
     * @throws Exception Exception is thrown when saving fails.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        tasks.add(taskToAdd);
        storage.save(tasks.asList());
        ui.showAdded(taskToAdd, tasks.size());
    }
}