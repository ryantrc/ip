import jdk.jfr.Description;
import java.time.LocalDate;
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

    public String getDescription(){
        return this.description;
    }

    public String toString(){
        return "[" + this.getStatusIcon() + "]" + " " + this.getDescription();
    }
}


class Deadline extends Task {

    protected LocalDate by;

    public Deadline (String description, String by) throws MerciException {
        super(description);
        this.by = LocalDate.parse(by);

        if (description == null || description.trim().isEmpty()){
            throw new MerciException("Your deadline cannot be empty!!");
        }

        if (by == null || by.trim().isEmpty()){
            throw new MerciException("Include a date to be done by!!");
        }
    }

    @Override
    public boolean occursOn(LocalDate date){
        return by.equals(date);
    }

    @Override
    public LocalDate getSortDate(){
        return by;
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
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
    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, String from, String to) throws MerciException {
        super(description);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);

        if (description == null || description.trim().isEmpty()){
            throw new MerciException("event cannot be empty!!");
        }

        if (from == null || to == null || from.trim().isEmpty() || to.trim().isEmpty()){
            throw new MerciException("Your event needs a to and from date!!");
        }
    }

    @Override
    public boolean occursOn(LocalDate date){
        return (!date.isBefore(from) && !date.isAfter(to));
    }

    @Override
    public LocalDate getSortDate(){
        return from;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[E]" + super.toString() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + " )";
    }
}

class MerciException extends Exception{
    public MerciException (String errorMessage){
        super(errorMessage);
    }
}