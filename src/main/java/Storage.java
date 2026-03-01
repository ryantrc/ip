import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    // ---------- Helpers ----------

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
