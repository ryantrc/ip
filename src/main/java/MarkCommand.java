/**
 * Class that defines behaviour for marking and unmarking tasks in the current task list.
 */
public class MarkCommand extends Command {
    private final int index;
    private final boolean done;

    /**
     * Creates a MarkCommand for the task at the given index.
     *
     * @param index
     * @param done
     */
    public MarkCommand(int index, boolean done) {
        this.index = index;
        this.done = done;
    }

    /**
     * Updates the completion status of the task at the given index. Marked if done and unmarked if it is not done yet.
     * Saves to the text file.
     *
     * @param tasks Current task list.
     * @param ui Ui handler for printing messages to the screen.
     * @param storage Storage handler to save changes made by command to the text file.
     * @throws Exception Exception If the index is invalid or saving fails.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        if (index < 0 || index >= tasks.size()) {
            throw new MerciException("Invalid task number!!");
        }
        Task t = tasks.get(index);
        if (done) t.markAsDone();
        else t.markAsNotDone();

        storage.save(tasks.asList());
        ui.showMarked(t, done);
    }
}