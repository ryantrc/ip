public class MarkCommand extends Command {
    private final int index;
    private final boolean done;

    public MarkCommand(int index, boolean done) {
        this.index = index;
        this.done = done;
    }

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