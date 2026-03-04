import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Entry point class for the Merci chatbot.
 *
 * Wires together the user interface, the task list and the storage, then runs the main input processing
 * loop where user input is read and parsed for the command type. (e.g. AddCommand, DeleteCommand, FindCommand etc)
 * then executed according to the command type.
 */

public class Merci {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a new Merci instance which loads information from the text file at the given file path, into
     * the new task list.
     * @param filePath Path to the saved tasks text file (e.g. "data/tasks.txt")
     */

    public Merci(String filePath) {
        ui = new Ui();
        storage = new Storage(Paths.get(filePath));

        ArrayList<Task> loaded = storage.load();
        tasks = new TaskList(loaded);
    }

    /**
     * Runs the main application loop.
     *
     * First displays a welcome message, then repeatedly loops through to read for user input.
     * User input is parsed for command type (e.g. AddCommand, ByeCommand, DeleteCommand etc) and
     * then executed based on the command type given.
     * Handles MerciException as a user-facing error and all other exceptions as unexpected failures.
     */
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

    /**
     * Launches the Merci application using the default saved file location.
     * @param args
     */
    public static void main(String[] args) {
        new Merci("data/tasks.txt").run();
    }
}