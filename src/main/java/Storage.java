import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving of the current task list to a text file located at a given file path.
 *
 * Each file is stored as a single line in this format:
 * <ul>
 *     <li>T|doneFlag|description</li>
 *     <li>D|doneFlag|description|by</li>
 *     <li>E|doneFlag|description|from|to</li>
 * </ul>
 */
public class Storage {
    private final Path path;

    public Storage(Path path) {
        this.path = path;
    }

    /** Loads tasks from disk. If file/folder doesn't exist yet, returns empty list. */
    public ArrayList<Task> load() {
        try {
            if (!Files.exists(path)) {
                return new ArrayList<>();
            }

            List<String> lines = Files.readAllLines(path);
            ArrayList<Task> tasks = new ArrayList<>();

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                try {
                    tasks.add(parseLine(line));
                } catch (Exception corrupted) {
                    // skip corrupted line (stretch goal friendly)
                }
            }
            return tasks;

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /** Saves tasks to disk. Creates data folder if missing. */
    /**
     * Saves all tasks to a text file at the given file location, overwriting the file every time.
     * Creates parent directories of they do not exist.
     *
     * @param tasks Task list to be saved.
     * @throws IOException If writing fails.
     */
    public void save(List<Task> tasks) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(encode(t));
        }
        Files.write(path, lines);
    }

    /**
     * Parses a single line into a Task instance
     * @param line Encoded task line.
     * @return parsed Task.
     * @throws Exception If the line is corrupted or has an unknown format.
     */
    private Task parseLine(String line) throws Exception {
        // T|1|read book
        // D|0|return book|June 6th
        // E|0|project meeting|Aug 6th 2pm|4pm

        String[] parts = line.split("\\|", -1);
        if (parts.length < 3) throw new Exception("Corrupted");

        String type = parts[0].trim();
        String done = parts[1].trim();
        String desc = parts[2].trim();

        Task task;
        switch (type) {
            case "T":
                task = new toDo(desc);
                break;
            case "D":
                if (parts.length < 4) throw new Exception("Corrupted deadline");
                task = new Deadline(desc, parts[3].trim());
                break;
            case "E":
                if (parts.length < 5) throw new Exception("Corrupted event");
                task = new Event(desc, parts[3].trim(), parts[4].trim());
                break;
            default:
                throw new Exception("Unknown type");
        }

        if (done.equals("1")) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }

        return task;
    }

    /**
     * Encodes a task into a single line string suitable for saving.
     *
     * @param t Task to encode.
     * @return Encoded string representation.
     */
    private String encode(Task t) {
        String done = t.isDone ? "1" : "0";

        if (t instanceof toDo) {
            return "T|" + done + "|" + t.description;
        }
        if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return "D|" + done + "|" + d.description + "|" + d.by.toString();
        }
        if (t instanceof Event) {
            Event e = (Event) t;
            return "E|" + done + "|" + e.description + "|" + e.from.toString() + "|" + e.to.toString();
        }

        return "T|" + done + "|" + t.description;
    }
}
