/**
 * Class that handles the terminating of chatbot.
 */
public class ByeCommand extends Command {

    /**
     * Creates bye command that signals programme termination.
     */
    public ByeCommand() {
        isExit = true;
    }

    /**
     * Displays the goodbye ui message.
     *
     * @param tasks Current task list.
     * @param ui User interface handler.
     * @param storage Storage handler.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }
}