package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TaskBuilder.DEFAULT_DEADLINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddTagCommandTest {
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
    public void execute_validIndexUnFilteredList_success() throws Exception {
        Tag tag = new Tag("module");
        Task task = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task taskTagged = new TaskBuilder(task).withTags("english", "module").build();
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_TASK, tag);

        String expectedMessage = String.format(addTagCommand.MESSAGE_SUCCESS, taskTagged.getTitle(), tag.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateTask(task , taskTagged);
        expectedModel.commitTaskBook();

        assertCommandSuccess(addTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Tag tag = new Tag("friends");
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, tag);

        assertCommandFailure(addTagCommand, model, commandHistory,
                AddTagCommand.MESSAGE_TASK_NOT_FOUND);
    }


    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Tag tag = new Tag("module");
        Task task = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task taskTagged = new TaskBuilder(task).withTags("english", "module").build();
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_TASK, tag);

        String expectedMessage = String.format(addTagCommand.MESSAGE_SUCCESS, taskTagged.getTitle(), tag.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateTask(task , taskTagged);
        expectedModel.commitTaskBook();

        assertCommandSuccess(addTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Tag tag = new Tag("friends");
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, tag);

        assertCommandFailure(addTagCommand, model, commandHistory,
                AddTagCommand.MESSAGE_TASK_NOT_FOUND);
    }

    @Test
    public void execute_setsWillPreventDuplicateTags_success() {
        Tag tag = new Tag("english");
        Task task = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_TASK, tag);

        String expectedMessage = String.format(addTagCommand.MESSAGE_SUCCESS, task.getTitle(), tag.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.commitTaskBook();

        assertCommandSuccess(addTagCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Tag tag = new Tag("friend");
        AddTagCommand addTagToFirstTaskCommand = new AddTagCommand(INDEX_FIRST_TASK, tag);
        AddTagCommand addTagToSecondTaskCommand = new AddTagCommand(INDEX_SECOND_TASK, tag);

        // same object -> returns true
        assertTrue(addTagToFirstTaskCommand.equals(addTagToFirstTaskCommand));

        // same values -> returns true
        AddTagCommand addTagToFirstTaskCommandCopy = new AddTagCommand(INDEX_FIRST_TASK, tag);
        assertTrue(addTagToFirstTaskCommand.equals(addTagToFirstTaskCommandCopy));

        // different types -> returns false
        assertFalse(addTagToFirstTaskCommand.equals(1));

        // null -> returns false
        assertFalse(addTagToFirstTaskCommand.equals(null));

        // different task -> returns false
        assertFalse(addTagToFirstTaskCommand.equals(addTagToSecondTaskCommand));
    }
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
        public void sortTask(String method) {
            throw new AssertionError("This method should not be called.");
        }
        //author

        @Override
        public Deadline getDeadline() {
            return new Deadline(DEFAULT_DEADLINE);
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
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single task.
     */
    private class ModelStubWithTask extends ModelStub {
        private final Task task;

        ModelStubWithTask(Task task) {
            requireNonNull(task);
            this.task = task;
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return this.task.isSameTask(task);
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();
        private Deadline deadline = new Deadline("1/1/2018");

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return tasksAdded.stream().anyMatch(task::isSameTask);
        }

        @Override
        public void selectDeadline(Deadline deadline) {
            this.deadline = deadline;
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
        public void commitTaskBook() {
            // called by {@code AddTaskCommand#execute()}
        }

        @Override
        public ReadOnlyTaskBook getAddressBook() {
            return new AddressBook();
        }
    }

}
