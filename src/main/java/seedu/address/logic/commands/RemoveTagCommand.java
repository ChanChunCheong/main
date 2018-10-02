package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


public class RemoveTagCommand extends Command {

    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": remove the tag of all the people in the addressBook"
            + "Parameters: "
            + PREFIX_TAG + "TAG(It must be an existing tag)\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_TAG + "friend";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "RemoveTagCommand not implemented yet";

    //private final Tag tag;

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }

}