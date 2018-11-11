package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook_sortedByDeadlines;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook_sortedByModuleCode;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook_sortedByPriority;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook_sortedByTitle;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

import java.util.Collections;
import java.util.function.Predicate;

public class SortTaskCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    @Test
    public void constructor_nullDeadline_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeferDeadlineCommand(null, 0);
    }

    @Test
    public void execute_sortedByModuleCode_success() {
        String method = "modules";
        SortTaskCommand sortTaskCommand = new SortTaskCommand(method);
        String expectedMessage = String.format(sortTaskCommand.MESSAGE_SUCCESS, method);

        ModelManager expectedModel = new ModelManager(getTypicalTaskBook_sortedByModuleCode(), new UserPrefs());
        expectedModel.commitTaskBook();
        assertCommandSuccess(sortTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /*
    @Test
    public void execute_sortedByTitle_success() {
       String method = "title";
        SortTaskCommand sortTaskCommand = new SortTaskCommand(method);
        String expectedMessage = String.format(sortTaskCommand.MESSAGE_SUCCESS, method);

        ModelManager expectedModel = new ModelManager(getTypicalTaskBook_sortedByTitle(), new UserPrefs());
        expectedModel.commitTaskBook();
        assertCommandSuccess(sortTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    */
    @Test
    public void execute_sortedByPriority_success() {
        String method = "priority";
        SortTaskCommand sortTaskCommand = new SortTaskCommand(method);
        String expectedMessage = String.format(sortTaskCommand.MESSAGE_SUCCESS, method);

        ModelManager expectedModel = new ModelManager(getTypicalTaskBook_sortedByPriority(), new UserPrefs());
        expectedModel.commitTaskBook();
        assertCommandSuccess(sortTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /*
    @Test
    public void execute_sortedByDeadlines_success() {
        String method = "deadlines";
        SortTaskCommand sortTaskCommand = new SortTaskCommand(method);
        String expectedMessage = String.format(sortTaskCommand.MESSAGE_SUCCESS, method);

        ModelManager expectedModel = new ModelManager(getTypicalTaskBook_sortedByDeadlines(), new UserPrefs());
        Task task = expectedModel.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        System.out.println(String.format("%1$s", task.getModuleCode()));
        Task task1 = expectedModel.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        System.out.println(String.format("%1$s", task1.getModuleCode()));
        Task task2 = expectedModel.getFilteredTaskList().get(INDEX_THIRD_TASK.getZeroBased());
        System.out.println(String.format("%1$s", task2.getModuleCode()));
        expectedModel.commitTaskBook();
        assertCommandSuccess(sortTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    */

    @Test
    public void equals() {
        String title = "title";
        String module = "module";
        SortTaskCommand sortByTitleCommand = new SortTaskCommand(title);
        SortTaskCommand sortByModuleCommand = new SortTaskCommand(module);

        // same object -> returns true
        assertTrue(sortByTitleCommand.equals(sortByTitleCommand));

        // same values -> returns true
        SortTaskCommand sortByTitleCommandCopy = new SortTaskCommand(title);
        assertTrue(sortByTitleCommand.equals(sortByTitleCommandCopy));

        // different types -> returns false
        assertFalse(sortByTitleCommand.equals(1));

        // null -> returns false
        assertFalse(sortByTitleCommand.equals(null));

        // different tag selected -> returns false
        assertFalse(sortByTitleCommand.equals(sortByModuleCommand));
    }
}
