import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;


public class Merci {
    private static void printError (String errorMessage){
        System.out.println("------------------------------------------------");
        System.out.println(errorMessage);
        System.out.println("------------------------------------------------");
    }
    public static void main(String[] args) {
        System.out.println("Hello! I am Merci!!");
        System.out.println("What can I do for you?\n");

        Scanner sc = new Scanner(System.in);
        //Task[] tasks = new Task[100];
        Path savePath = Paths.get("data", "tasks.txt");
        Storage storage = new Storage(savePath);
        ArrayList<Task> tasks = storage.load();
        while (true){
            String input = sc.nextLine();
            try {
                if (input.equalsIgnoreCase("list")) {
                    System.out.println("Here are the tasks in your list: ");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i).toString());
                    }
                } else if (input.equalsIgnoreCase("bye")) {
                    System.out.println("bye! see you again soon!");
                    break;
                } else if (input.toLowerCase().startsWith("mark ")) {
                    int index = Integer.parseInt(input.substring(5).trim()) - 1;

                    if (index < 0 || index > tasks.size()) {
                        throw new MerciException("Invalid task number!!");
                    }
                    tasks.get(index).markAsDone();
                    try {
                        storage.save(tasks);
                    } catch (IOException e){
                        printError("Failed to save: " + e.getMessage());
                    }
                    System.out.println("Nice! I've marked this as done: ");
                    System.out.println(tasks.get(index).toString());
                } else if (input.toLowerCase().startsWith("unmark ")) {
                    int index = Integer.parseInt(input.substring(7).trim()) - 1;

                    if (index < 0 || index >= tasks.size()){
                        throw new MerciException("Invalid task number!!");
                    }
                    tasks.get(index).markAsNotDone();
                    try {
                        storage.save(tasks);
                    } catch (IOException e){
                        printError("Failed to save: " + e.getMessage());
                    }
                    System.out.println("OK, I've marked this task as not done yet: ");
                    System.out.println(tasks.get(index).toString());
                } else if (input.toLowerCase().startsWith("delete ")){
                    int index = Integer.parseInt(input.substring(7).trim()) -1;

                    if (index < 0 || index >= tasks.size()){
                        throw new MerciException("Invalid task number!!");
                    }

                    System.out.println("------------------------------------------------");
                    System.out.println("Noted. Ive removed this task: ");
                    System.out.println(tasks.get(index).toString());
                    tasks.remove(index);
                    try {
                        storage.save(tasks);
                    } catch (IOException e){
                        printError("Failed to save: " + e.getMessage());
                    }
                    System.out.println("You have " + tasks.size() + " tasks in your list now");
                    System.out.println("------------------------------------------------");

                } else {

                    Task newTask;

                    if (input.toLowerCase().startsWith("deadline")) {
                        String rest = input.substring(9).trim();
                        String[] parts = rest.split("/by", 2);

                        if (parts.length == 2) {
                            String desc = parts[0];
                            String by = parts[1];
                            newTask = new Deadline(desc, by);
                        } else {
                            // malformed -> treat as todo
                            throw new MerciException("You can only have one deadline!!");
                        }
                    } else if (input.toLowerCase().startsWith("event")) {
                        String rest = input.substring(6).trim();
                        String[] parts = rest.split("/from", 2);

                        if (parts.length == 2) {
                            String desc = parts[0].trim();
                            String[] parts2 = parts[1].split("/to", 2);
                            if (parts2.length == 2) {
                                String from = parts2[0];
                                String to = parts2[1];
                                newTask = new Event(desc, from, to);
                            } else {
                                // malformed -> treat as todo
                                throw new MerciException("You can only have one end date!!");
                            }
                        } else {
                            // malformed -> treat as todo
                            throw new MerciException("You can only have one start date!!");
                        }
                    } else if (input.toLowerCase().startsWith("todo")) {
                        String desc = input.substring(5).trim();
                        newTask = new toDo(desc);
                    } else {
                        throw new MerciException("I dont know what to do T_T");
                    }

                    tasks.add(newTask);
                    try {
                        storage.save(tasks);
                    } catch (IOException e){
                        printError("Failed to save: " + e.getMessage());
                    }

                    System.out.println("Got it! I've added this task: ");
                    System.out.println(newTask.toString());
                    System.out.println("You now have " + tasks.size() + " tasks in the list");
                }
            } catch (MerciException e) {
                printError(e.getMessage());
            } catch (NumberFormatException e) {
                //mark/unmark with nonsense (e.g abc)
                printError("Enter a number");
            } catch (Exception e){
                printError("Something went wrong: " + e.getMessage());
            }
        }
    }
}

