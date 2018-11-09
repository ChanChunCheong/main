package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeadlines.INVALID_32ND_JAN_2018;
import static seedu.address.testutil.TypicalDeadlines.INVALID_32ND_JAN_WITHOUT_YEAR;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_APR_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_APR_WITHOUT_YEAR;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_WITHOUT_YEAR;
import static seedu.address.testutil.TypicalDeadlines.VALID_YEAR_2018;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

public class DeferDeadlineCommandTest {
    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
        private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

        @Rule
        public ExpectedException thrown = ExpectedException.none();

        //private CommandHistory commandHistory = new CommandHistory();

        @Test
        public void constructor_nullDeadline_throwsNullPointerException() {
            thrown.expect(NullPointerException.class);
            new DeferDeadlineCommand(null, 0);
        }

        @Test
        public void execute_validIndexFilteredList_success() throws Exception {
            int DeferredDay = 1;

            Task task = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
            Task editedTask = new TaskBuilder(task).withDeadline("10/10/2018").build();
            DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(INDEX_FIRST_TASK, 1);

            String expectedMessage = String.format(DeferDeadlineCommand.MESSAGE_SUCCESS, task);

            ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
            expectedModel.updateTask(task , editedTask);
            expectedModel.commitTaskBook();

            assertCommandSuccess(deferDeadlineCommand, model, commandHistory, expectedMessage, expectedModel);
        }


        @Test
        public void execute_invalidIndexUnfilteredList_throwsCommandException() {
            int Deferredday = 1;
            Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
            DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(outOfBoundIndex, 1);

            assertCommandFailure(deferDeadlineCommand, model, commandHistory,
                    deferDeadlineCommand.MESSAGE_NONEXISTENT_TASK);
        }

        /*
        @Test
            public void execute_invalidDeadlineWithYear_throwCommandException() throws Exception {
                Deadline invalidDeadline = INVALID_32ND_JAN_2018;
                SelectDeadlineCommand selectCommand = new SelectDeadlineCommand(invalidDeadline);
                ModelStub modelStub = new ModelStubWithDeadline(invalidDeadline);

                thrown.expect(CommandException.class);
                thrown.expectMessage(Messages.MESSAGE_INVALID_DEADLINE);
                selectCommand.execute(modelStub, commandHistory);
            }

        @Test
        public void execute_invalidDeadlineWithoutYear_throwsCommandException() throws Exception {
            Deadline invalidDeadline = INVALID_32ND_JAN_WITHOUT_YEAR;
            SelectDeadlineCommand selectCommand = new SelectDeadlineCommand(invalidDeadline);
            ModelStub modelStub = new ModelStubWithDeadline(invalidDeadline);

            thrown.expect(CommandException.class);
            thrown.expectMessage(Messages.MESSAGE_INVALID_DEADLINE);
            selectCommand.execute(modelStub, commandHistory);
        }

        @Test
        public void equals() {
            SelectDeadlineCommand selectJan1st2018Command = new SelectDeadlineCommand(VALID_1ST_JAN_2018);
            SelectDeadlineCommand selectApr1st2018Command = new SelectDeadlineCommand(VALID_1ST_APR_2018);
            SelectDeadlineCommand selectJan1st2018CommandCopy = new SelectDeadlineCommand(VALID_1ST_JAN_2018);
            SelectDeadlineCommand selectJan1stCommand = new SelectDeadlineCommand(VALID_1ST_JAN_WITHOUT_YEAR);
            SelectDeadlineCommand selectApr1stCommand = new SelectDeadlineCommand(VALID_1ST_APR_WITHOUT_YEAR);

            // Same object -> returns true
            assertEquals(selectJan1st2018Command, selectJan1st2018Command);

            // Same deadline -> returns true
            assertEquals(selectJan1st2018Command, selectJan1st2018CommandCopy);

            // Same deadline since default year is 2018 -> returns true
            assertEquals(selectJan1st2018Command, selectJan1stCommand);

            // Null -> returns false
            assertNotEquals(selectJan1st2018Command, (null));
            assertNotEquals(selectJan1stCommand, (null));

            // Different types -> returns false
            assertNotEquals(selectJan1st2018Command, 1);
            assertNotEquals(selectJan1stCommand, 1);

            // Different deadline -> returns false
            assertNotEquals(selectJan1st2018Command, selectApr1st2018Command);
            assertNotEquals(selectJan1stCommand, selectApr1st2018Command);
            assertNotEquals(selectJan1stCommand, selectApr1stCommand);

        }
        */
/**
 * A default model stub that have all of the methods failing.
 */
private class ModelStub implements Model {
    @Override
    public void addTask(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void selectDeadline(Deadline deadline) {
        throw new AssertionError("This method should not be called.");
    }

    //author ChanChunCheong
    @Override
    public void addTag(Task task, Tag tag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeTag(Task task, Tag tag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void selectTag(Tag tag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deferTaskDeadline(Task task, int deferredDay) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortTask(String method) {
        throw new AssertionError("This method should not be called.");
    }
    //author


    @Override
    public Deadline getDeadline() {
        return new Deadline(VALID_1ST_JAN_2018.toString());
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyTaskBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTask(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isTheExactSameTaskAs(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTask(Task target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void completeTask(Task target, int hours) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateTask(Task target, Task editedTask) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoTaskBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoTaskBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoTaskBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoTaskBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commitTaskBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void trackProductivity() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getYear() {
        return VALID_YEAR_2018;
    }
}

/**
 * A default model stub that contains a single task.
 */

private class ModelStubWithDeadline extends ModelStub {
    private final Deadline deadline;

    ModelStubWithDeadline(Deadline deadline) {
        requireNonNull(deadline);
        this.deadline = deadline;
    }
}

/**
 * A model stub that always accepts the deferred deadline being.
 */
private class ModelStubAcceptingDeferredDeadline extends ModelStub {

    final ArrayList<Task> tasksAdded = new ArrayList<>();
    private Deadline deadline = new Deadline("1/1/2018");

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasksAdded.stream().anyMatch(task::isSameTask);
    }
    @Override
    public void selectDeadline(Deadline deadline) {
        requireNonNull(deadline);
    }
    @Override
    public Deadline getDeadline() {
        return this.deadline;
    }
    @Override
    public void addTask(Task task) {
        requireNonNull(task);
        tasksAdded.add(task);
    }

    @Override
    public void deferTaskDeadline(Task targetedTask, int deferredDays) {
        for(Task task : tasksAdded) {
            if(task.equals(targetedTask)) {
                Deadline deferredDeadline = task.getDeadline().deferDeadline(deferredDays);
                task.setDeadline(deferredDeadline);
            }
        }
    }
    @Override
    public void commitTaskBook() {
        // called by {@code SelectDeadlineCommand#execute()}
    }

    @Override
    public ReadOnlyTaskBook getAddressBook() {
        return new AddressBook();
    }
}
}
