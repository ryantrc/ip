/**
 * Abstract class representing executable user commands.
 * <p>
 *     Each command can implement the execute method, and may use
 *     isExit to mark itself as an exit command.
 * </p>
 */
public abstract class Command {
    protected boolean isExit = false;

    /**
     * Method that each command can use to execute its own command.
     * Uses the current task list, Ui and storage.
     * @param tasks Current task list.
     * @param ui Ui handler for printing messages to the screen.
     * @param storage Storage handler to save changes made by command to the text file.
     * @throws Exception To be used if  command execution fails. (e.g. IO errors or storage failures.)
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws Exception;

    public boolean isExit() {
        return isExit;
    }
}