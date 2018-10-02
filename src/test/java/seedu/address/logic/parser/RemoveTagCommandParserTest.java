package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;

public class RemoveTagCommandParserTest {
    private RemoveTagCommandParser parser = new RemoveTagCommandParser();
    private final Tag tag = new Tag("friend");
    @Test
    public void parse_tagSpecified_success() {
        //what to put for this?
        String userInput = PREFIX_TAG + "friend";
        RemoveTagCommand expectedCommand = new RemoveTagCommand(tag);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, RemoveTagCommand.COMMAND_WORD, expectedMessage);
    }
}
