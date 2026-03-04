import java.time.LocalDate;

/**
 * Parses raw user input into executable command objects (e.g. AddCommand, ByeCommand, FindCommand, ListCommand etc).
 *
 * Throws MerciException for invalid command formats.
 */
public class Parser {

    /**
     * Parses a user input into a corresponding command.
     *
     * @param input Raw user input.
     * @return Parsed {@link Command} instance.
     * @throws MerciException If the command is unknown or badly formatted.
     */
    public static Command parse(String input) throws MerciException {
        String trimmed = input.trim();
        String lower = trimmed.toLowerCase();

        if (lower.equals("list")) return new ListCommand();
        if (lower.equals("bye")) return new ByeCommand();

        if (lower.startsWith("mark ")) {
            int idx = parseIndex(trimmed.substring(5)) - 1;
            return new MarkCommand(idx, true);
        }

        if (lower.startsWith("unmark ")) {
            int idx = parseIndex(trimmed.substring(7)) - 1;
            return new MarkCommand(idx, false);
        }

        if (lower.startsWith("delete ")) {
            int idx = parseIndex(trimmed.substring(7)) - 1;
            return new DeleteCommand(idx);
        }

        if (lower.startsWith("todo ")) {
            String desc = trimmed.substring(5).trim();
            return new AddCommand(new toDo(desc));
        }

        if (lower.startsWith("deadline")) {
            String rest = trimmed.substring(8).trim(); // after "deadline"
            // expecting: deadline <desc> /by <by>
            String[] parts = rest.split("/by", 2);
            if (parts.length != 2) throw new MerciException("You can only have one deadline!!");
            String desc = parts[0].trim();
            String by = parts[1].trim();
            return new AddCommand(new Deadline(desc, by));
        }

        if (lower.startsWith("event")) {
            String rest = trimmed.substring(5).trim(); // after "event"
            // expecting: event <desc> /from <from> /to <to>
            String[] parts = rest.split("/from", 2);
            if (parts.length != 2) throw new MerciException("You can only have one start date!!");

            String desc = parts[0].trim();
            String[] parts2 = parts[1].split("/to", 2);
            if (parts2.length != 2) throw new MerciException("You can only have one end date!!");

            String from = parts2[0].trim();
            String to = parts2[1].trim();
            return new AddCommand(new Event(desc, from, to));
        }

        if (lower.equals("schedule")){
            return new ScheduleCommand(null);
        }

        if (lower.startsWith("schedule ")) {
            String rest = trimmed.substring(8).trim();
            try {
                return new ScheduleCommand(LocalDate.parse(rest));
            } catch (Exception e){
                throw new MerciException("Enter date in yyyy-mm-dd format!!");
            }
        }

        if (lower.startsWith("find ")){
            String rest = trimmed.substring(4).trim();
            try {
                return new FindCommand(rest);
            } catch (Exception e){
                throw new MerciException("Enter a valid keyword!");
            }
        }

        throw new MerciException("I dont know what to do T_T");
    }

    /**
     * Parses an integer index from a string
     *
     * @param s
     * @return Parsed Integer.
     * @throws MerciException
     */
    private static int parseIndex(String s) throws MerciException {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw new MerciException("Enter a number");
        }
    }
}