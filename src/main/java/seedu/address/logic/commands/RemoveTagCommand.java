package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;


public class RemoveTagCommand extends Command {

    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": remove the tag of all the people in the addressBook"
            + "Parameters: "
            + PREFIX_TAG + "TAG(It must be an existing tag)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "friend";

    //public static final String MESSAGE_NOT_IMPLEMENTED_YET = "RemoveTagCommand not implemented yet";
    public static final String MESSAGE_ARGUMENTS = "Tag: ";

    private final Tag tag;

    public RemoveTagCommand(Tag tagName) {
        requireAllNonNull(tagName);
        this.tag = tagName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        //throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, tag));
    }

    @Override
    public boolean equals(Object other) {
        /*
        // short circuit if same object
        if (other == this) {
            return true;
        }
        */

        // instanceof handles nulls
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        return true;
        /*
        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
        */
        //
    }
}