/**
 * Class used for deleting a command from the task list, saved the updated list to the text file
 * and prints a confirmation message to the UI.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Method that takes an index from the user and points the DeleteCommand index to that index
     * which will be deleted.
     * @param index Zero-based index of the task to be deleted.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Removes the task at the given index and saves it to the text file.
     *
     *
     * @param tasks Current task list.
     * @param ui Ui handler for printing messages to the screen.
     * @param storage Storage handler to save changes made by command to the text file.
     * @throws Exception If the index is invalid or saving fails.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        if (index < 0 || index >= tasks.size()) {
            throw new MerciException("Invalid task number!!");
        }
        Task removed = tasks.remove(index);
        storage.save(tasks.asList());
        ui.showDeleted(removed, tasks.size());
    }
}