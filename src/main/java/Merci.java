import java.util.Arrays;
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
            } else {

                Task newTask;

                if (input.toLowerCase().startsWith("deadline")){
                    String rest = input.substring(9).trim();
                    String[] parts = rest.split("/by", 2);

                    if (parts.length == 2){
                        String desc = parts[0];
                        String by = parts[1];
                        newTask = new Deadline(desc, by);
                    } else {
                        // malformed -> treat as todo
                        newTask = new toDo(input);
                    }
                } else if (input.toLowerCase().startsWith("event")){
                    String rest = input.substring(6).trim();
                    String[] parts = rest.split("/from", 2);

                    if (parts.length == 2){
                        String desc = parts[0].trim();
                        String[] parts2 = parts[1].split("/to", 2);
                        if (parts2.length == 2){
                            String from = parts2[0];
                            String to = parts2[1];
                            newTask = new Event(desc, from, to);
                        } else {
                            // malformed -> treat as todo
                            newTask = new toDo(input);
                        }
                    } else {
                        // malformed -> treat as todo
                        newTask = new toDo(input);
                    }
                } else if (input.toLowerCase().startsWith("todo")){
                    String desc = input.substring(5).trim();
                    newTask = new toDo(desc);
                } else {
                    newTask = new toDo(input);
                }

                tasks[taskCount] = newTask;
                taskCount++;

                System.out.println("Got it! I've added this task: ");
                System.out.println(newTask.toString());
                System.out.println("You now have " + taskCount + " tasks in the list");
            }
        }
    }
}

