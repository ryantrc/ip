public abstract class Command {
    protected boolean isExit = false;

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws Exception;

    public boolean isExit() {
        return isExit;
    }
}