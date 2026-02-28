import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class ScheduleCommand extends Command{

    public final LocalDate filterDate;

    public ScheduleCommand(LocalDate filterDate){
        this.filterDate = filterDate;
    }

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