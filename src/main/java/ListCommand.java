/**
 * Class used to define the behaviour for listing out current tasks saved in the current task list
 */
public class ListCommand extends Command {

    /**
     * Method that prints all tasks in the currenbt task list to the user's screen.
     *
     * @param tasks Current task list.
     * @param ui Ui handler for printing messages to the screen.
     * @param storage Storage handler to save changes made by command to the text file.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}