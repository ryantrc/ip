import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

/**
 * Displays tasks grouped by their date (for deadlines abd events) in ascending order.
 * Optionally filtered by inputting a date in yyyy-mm-dd format wherein only the tasks occurring on
 * the given date will be printed to the UI.
 *
 * Tasks that do no have a date (todo) are ignored
 */

public class ScheduleCommand extends Command{

    /** Date to filter by. If null, shows all dated tasks grouped by their sort date in ascending order. */
    public final LocalDate filterDate;

    /**
     * Creates a schedule command with an optional filter date.
     *
     * @param filterDate Date to filter on. Null will show all dated tasks in ascending order.
     */
    public ScheduleCommand(LocalDate filterDate){
        this.filterDate = filterDate;
    }

    /**
     * Group tasks by date and prints them to the UI in ascending order.
     *
     * @param tasks Current task list.
     * @param ui Ui handler for printing messages to the screen.
     * @param storage Storage handler to save changes made by command to the text file.
     * @throws Exception Not thrown intentionally. Kept for signature consistency.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws Exception {
        DateTimeFormatter outFmt = DateTimeFormatter.ofPattern("MMM dd yyyy");

        Map<LocalDate, ArrayList<Task>> grouped = new TreeMap<>();

        for (int i = 0; i < tasks.size(); i++){
            Task t = tasks.get(i);

            if (t.getSortDate() == null){
                continue;
            }
            if (filterDate != null && !t.occursOn(filterDate)){
                continue;
            }
            //from here t is deadline/event or has a filterdate

            LocalDate groupKey = (filterDate != null) ? filterDate : t.getSortDate();

            grouped.putIfAbsent(groupKey, new ArrayList<>());
            grouped.get(groupKey).add(t); // TreeMap where key = date and value = ArrayList
        }

        for (Map.Entry<LocalDate, ArrayList<Task>> entry : grouped.entrySet()){
            LocalDate date = entry.getKey();
            ArrayList<Task> list = entry.getValue();

            System.out.println(date.format(outFmt) + " : ");
            for (int j = 0; j < list.size(); j++){
                System.out.println(" " + (j + 1) + ". " + list.get(j));
            }
            System.out.println();
        }
    }
}