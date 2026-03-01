import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Task {
    protected String description;
    protected boolean isDone;

    public boolean occursOn(LocalDate date){
        return false;
    }

    public LocalDate getSortDate(){
        return null;
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone(){
        this.isDone = true;
    }

    public void markAsNotDone(){
        this.isDone = false;
    }

    public boolean contains(String keyword){
        return this.description.toLowerCase().contains(keyword.toLowerCase());
    }

    public String getDescription(){
        return this.description;
    }

    public String toString(){
        return "[" + this.getStatusIcon() + "]" + " " + this.getDescription();
    }
}


class Deadline extends Task {

    protected LocalDateTime by;

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

class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

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