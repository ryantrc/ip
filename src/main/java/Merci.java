import java.util.Scanner;

public class Merci {
    public static void main(String[] args) {
        System.out.println("Hello! I am Merci!!");
        System.out.println("What can I do for you?\n");

        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;
        while (true){
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("list")) {
                System.out.println("Here are the tasks in your list: ");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i].toString());
                }
            }
            else if (input.equalsIgnoreCase("bye")){
                System.out.println("bye! see you again soon!");
                break;
            } else if (input.toLowerCase().startsWith("mark ")){
                int index = Integer.parseInt(input.substring(5).trim()) - 1;
                tasks[index].markAsDone();
                System.out.println("Nice! I've marked this as done: ");
                System.out.println(tasks[index].toString());
            } else if (input.toLowerCase().startsWith("unmark ")){
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
                tasks[index].markAsNotDone();
                System.out.println("OK, I've marked this task as not done yet: ");
                System.out.println(tasks[index].toString());
            }
            else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("Added: " + tasks[taskCount - 1].getDescription());
            }
        }
    }
}

