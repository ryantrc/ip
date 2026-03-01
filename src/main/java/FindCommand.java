public class FindCommand extends Command{

    private final String keyword;

    public FindCommand (String keyword){
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception{

        boolean found = false;
        for (int i = 0; i < tasks.size(); i++){
            if (tasks.get(i).contains(keyword)){
                System.out.println("Here are the matching tasks in your list!!");
                System.out.println((i + 1) + ". " + tasks.get(i));
                found = true;
            }
        }

        if (!found){
            System.out.println("No tasks with that keyword was found :'( ");
        }
    }

}