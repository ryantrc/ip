public class AddCommand extends Command {
    private final Task taskToAdd;

    public AddCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        tasks.add(taskToAdd);
        storage.save(tasks.asList());
        ui.showAdded(taskToAdd, tasks.size());
    }
}