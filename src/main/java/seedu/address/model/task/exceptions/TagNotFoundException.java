package seedu.address.model.task.exceptions;

/**
 * Signals that the operation is unable to find the specified task.
 */
public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException() {
        super("Tag doesn't exist in task");
    }
}