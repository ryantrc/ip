import jdk.jfr.Description;

public class Task {
    protected String description;
    protected boolean isDone;

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

    protected String by;

    public Deadline (String description, String by) throws MerciException {
        super(description);
        this.by = by;

        if (description == null || description.trim().isEmpty()){
            throw new MerciException("Your deadline cannot be empty!!");
        }

        if (by == null || by.trim().isEmpty()){
            throw new MerciException("Include a date to be done by!!");
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
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
    protected String from;
    protected  String to;

    public Event(String description, String from, String to) throws MerciException {
        super(description);
        this.from = from;
        this.to = to;

        if (description == null || description.trim().isEmpty()){
            throw new MerciException("event cannot be empty!!");
        }

        if (from == null || to == null || from.trim().isEmpty() || to.trim().isEmpty()){
            throw new MerciException("Your event needs a to and from date!!");
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + " )";
    }
}

class MerciException extends Exception{
    public MerciException (String errorMessage){
        super(errorMessage);
    }
}