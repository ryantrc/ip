import java.util.Scanner;

public class Merci {
    public static void main(String[] args) {
        System.out.println("Hello! I am Merci!!");
        System.out.println("What can I do for you?\n");

        Scanner sc = new Scanner(System.in);

        String input;
        String[] tasks = new String[100];
        int taskCount = 0;
        while (true) {
            input = sc.nextLine();

            if (input.equalsIgnoreCase("list")){
                for (int i = 0; i < taskCount; i++){
                    System.out.println((i + 1) + ".  " + tasks[i]);
                }
            }
            if (input.equalsIgnoreCase("bye")){
                System.out.println("bye! see you again soon!");
                break;
            }
            tasks[taskCount] = input;
            taskCount++;
            System.out.println("added: " + tasks[taskCount - 1]);
        }
    }
}
