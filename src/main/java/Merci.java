import java.util.Scanner;

public class Merci {
    public static void main(String[] args) {
        System.out.println("Hello! I am Merci!!");
        System.out.println("What can I do for you?");
        System.out.println();

        Scanner sc = new Scanner(System.in);
        String echo;

        while (true){
            echo = sc.nextLine();
            if (echo.equalsIgnoreCase("bye")) {
                System.out.println("bye! hope to see you again soon!!");
                break;
            }
            System.out.println(echo);
        }
    }
}
