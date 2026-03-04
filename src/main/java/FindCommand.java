/**
 * Class that defines the command for finding a task whose description contains a keyword and printing it to the
 * screen.
 *
 */
public class FindCommand extends Command{

    private final String keyword;

    /**
     * Creates a FindCommand with the keyword to seach for.
     * @param keyword Keyword that will be used to search for the command.
     */
    public FindCommand (String keyword){
        this.keyword = keyword;
    }

    /**
     * Method that defines the FindCommand execute. Searches through the current task list and prints
     * any tasks it finds that matches the keyword given.
     *
     *
     * @param tasks Current task list.
     * @param ui Ui handler for printing messages to the screen.
     * @param storage Storage handler to save changes made by command to the text file.
     * @throws Exception Exception is not thrown here but kept for signature consistency.
     */

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