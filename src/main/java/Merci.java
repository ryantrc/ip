import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Merci {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Merci(String filePath) {
        ui = new Ui();
        storage = new Storage(Paths.get(filePath));

        ArrayList<Task> loaded = storage.load();
        tasks = new TaskList(loaded);
    }

    public void run() {
        ui.showWelcome();

        Scanner sc = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand(sc);
                ui.showLine();

                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();

            } catch (MerciException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Something went wrong: " + e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
        new Merci("data/tasks.txt").run();
    }
}