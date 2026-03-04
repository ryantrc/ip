import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task item in Merci.
 *
 * Each task has a description and a completion status. Subclasses can attach date/time information
 * and override date/time information depending on what type of task they are.
 */

public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Checks whether this task occurs on the given date.
     * @param date Date to check.
     * @return true if the task occurs on the given date, false otherwise.
     */
    public boolean occursOn(LocalDate date){
        return false;
    }

    /**
     * Returns the date used for sorting this task in schedule views.
     * Default implementation returns null for tasks without dates (todo).
     * @return Sort date, or null if not applicable.
     */
    public LocalDate getSortDate(){
        return null;
    }

    /**
     * Creates a task with the given description, initially marked as not done.
     *
     * @param description Task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of this task. Used in string representations.
     * @return "X" if done, empty otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone(){
        this.isDone = true;
    }

    public void markAsNotDone(){
        this.isDone = false;
    }

    /**
     * Checks if this task contains a given keyword. Used in FindCommand.
     * @param keyword Keyword to search for.
     * @return true if the keyword appears in this task's descrition.
     */
    public boolean contains(String keyword){
        return this.description.toLowerCase().contains(keyword.toLowerCase());
    }

    public String getDescription(){
        return this.description;
    }

    /**
     * Returns a string representation of this task to be printed to the UI.
     * @return String representation inbcluding completion status.
     */
    public String toString(){
        return "[" + this.getStatusIcon() + "]" + " " + this.getDescription();
    }
}

/**
 * Represents a deadline task with a due date/time.
 */
class Deadline extends Task {

    protected LocalDateTime by;

    /**
     * Creats deadline task with the given description and a due date/time string.
     * @param description Task description (cannot be blank).
     * @param by Date task must be done by.
     * @throws MerciException If description is blank or date parsing fails.
     */
    public Deadline (String description, String by) throws MerciException {
        super(description);

        if (description == null || description.trim().isEmpty()){
            throw new MerciException("Your deadline cannot be empty!!");
        }

        if (by == null || by.trim().isEmpty()){
            throw new MerciException("Include a date to be done by!!");
        }

        this.by = DateTimeUtil.parseDateTime(by);
    }

    @Override
    public boolean occursOn(LocalDate date){
        return by.toLocalDate().equals(date);
    }

    @Override
    public LocalDate getSortDate(){
        return by.toLocalDate();
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.formatDateTime(by) + ")";
    }
}

/**
 * Represents a todo task without any date/time attached.
 */
class toDo extends Task {

    public toDo (String description) throws MerciException {
        super(description);

        if (description == null || description.trim().isEmpty()){
            throw new MerciException("Your todo cant be empty!!");
        }
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

/**
 * Represents an event task with a given from and to date.
 */
class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Creates an event task with the given description, to and from date.
     * @param description Event description (must no tbe blank).
     * @param from Date that the event starts.
     * @param to Date that the event ends.
     * @throws MerciException If inputs are blank, parsing fails or to date is before from date.
     */
    public Event(String description, String from, String to) throws MerciException {
        super(description);

        if (description == null || description.trim().isEmpty()) {
            throw new MerciException("event cannot be empty!!");
        }
        if (from == null || to == null || from.trim().isEmpty() || to.trim().isEmpty()) {
            throw new MerciException("Your event needs a to and from date/time!!");
        }

        this.from = DateTimeUtil.parseDateTime(from);
        this.to = DateTimeUtil.parseDateTime(to);

        if (this.to.isBefore(this.from)) {
            throw new MerciException("Event end must be after start!");
        }
    }

    @Override
    public boolean occursOn(LocalDate date) {
        // schedule is by date (not by time), so check date range inclusive
        LocalDate dFrom = from.toLocalDate();
        LocalDate dTo = to.toLocalDate();
        return (!date.isBefore(dFrom)) && (!date.isAfter(dTo));
    }

    @Override
    public LocalDate getSortDate() {
        return from.toLocalDate();
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + DateTimeUtil.formatDateTime(from)
                + " to: " + DateTimeUtil.formatDateTime(to) + ")";
    }
}

class MerciException extends Exception{
    public MerciException (String errorMessage){
        super(errorMessage);
    }
}