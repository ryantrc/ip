import java.util.Scanner;

/**
 * Handles ALL user interactions such as printing messages, printing task lists and goodbye message.
 */
public class Ui {
    private static final String LINE = "------------------------------------------------";

    public void showWelcome() {
        showLine();

        System.out.println("""
                   __  __                 _
                  |  \\/  | ___ _ __ ___ (_)
                  | |\\/| |/ _ \\ '__/ __|| |
                  | |  | |  __/ | | (__ | |
                  |_|  |_|\\___|_|  \\___||_|

                         /\\_/\\\\
                        ( o.o )
                         > ^ <
""");

        System.out.println("Hello! I am Merci!!");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * reads a full command line from the user.
     *
     * @param sc Scanner used to read from standard input.
     * @return The raw input line.
     */
    public String readCommand(Scanner sc) {
        return sc.nextLine();
    }

    /** Prints a horizontal separator line. */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays error message with separators.
     *
     * @param msg Error message.
     */
    public void showError(String msg) {
        showLine();
        System.out.println(msg);
        showLine();
    }

    /** Shows a laoding error message used when loading saved tasks failed. */
    public void showLoadingError() {
        showError("Couldn’t load saved tasks, starting with an empty list.");
    }

    /**
     * Displays all tasks in the given task list.
     *
     * @param tasks Task list to be displayed.
     */
    public void showList(TaskList tasks) {
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /** Message shown after adding a task to the task list. */
    public void showAdded(Task task, int size) {
        System.out.println("Got it! I've added this task: ");
        System.out.println(task);
        System.out.println("You now have " + size + " tasks in the list");
    }

    /** Message shown after deleting a task from the task list. */
    public void showDeleted(Task task, int size) {
        showLine();
        System.out.println("Noted. Ive removed this task: ");
        System.out.println(task);
        System.out.println("You have " + size + " tasks in your list now");
        showLine();
    }

    /** Message shown after marking a task as done/not done. */
    public void showMarked(Task task, boolean done) {
        if (done) {
            System.out.println("Nice! I've marked this as done: ");
        } else {
            System.out.println("OK, I've marked this task as not done yet: ");
        }
        System.out.println(task);
    }

    /** Goodbye message to be shown to the user. */
    public void showBye() {
        System.out.println("bye! see you again soon!");
    }
}