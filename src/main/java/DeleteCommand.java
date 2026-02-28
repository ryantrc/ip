public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

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