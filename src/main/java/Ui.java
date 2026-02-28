import java.util.Scanner;

public class Ui {
    private static final String LINE = "------------------------------------------------";

    public void showWelcome() {
        System.out.println("Hello! I am Merci!!");
        System.out.println("What can I do for you?\n");
    }

    public String readCommand(Scanner sc) {
        return sc.nextLine();
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showError(String msg) {
        showLine();
        System.out.println(msg);
        showLine();
    }

    public void showLoadingError() {
        showError("Couldn’t load saved tasks, starting with an empty list.");
    }

    public void showList(TaskList tasks) {
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void showAdded(Task task, int size) {
        System.out.println("Got it! I've added this task: ");
        System.out.println(task);
        System.out.println("You now have " + size + " tasks in the list");
    }

    public void showDeleted(Task task, int size) {
        showLine();
        System.out.println("Noted. Ive removed this task: ");
        System.out.println(task);
        System.out.println("You have " + size + " tasks in your list now");
        showLine();
    }

    public void showMarked(Task task, boolean done) {
        if (done) {
            System.out.println("Nice! I've marked this as done: ");
        } else {
            System.out.println("OK, I've marked this task as not done yet: ");
        }
        System.out.println(task);
    }

    public void showBye() {
        System.out.println("bye! see you again soon!");
    }
}