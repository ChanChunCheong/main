package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static junit.framework.TestCase.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalDeadlines.VALID_1ST_JAN_2018;
import static seedu.address.testutil.TypicalDeadlines.VALID_YEAR_2018;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;
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


public class DeferDeadlineCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    //private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDeadline_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeferDeadlineCommand(null, 0);
    }

    @Test
    public void execute_validIndexUnFilteredList_success() throws Exception {

        Task taskToDefer = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task deferredTask = new TaskBuilder(taskToDefer).withDeadline("10/10/2018").build();
        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(INDEX_FIRST_TASK, 1);

        String expectedMessage = String.format(DeferDeadlineCommand.MESSAGE_SUCCESS, taskToDefer);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateTask(taskToDefer , deferredTask);
        expectedModel.commitTaskBook();

        assertCommandSuccess(deferDeadlineCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(outOfBoundIndex, 1);

        assertCommandFailure(deferDeadlineCommand, model, commandHistory,
                deferDeadlineCommand.MESSAGE_NONEXISTENT_TASK);
    }


    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskToDefer = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task deferredTask = new TaskBuilder(taskToDefer).withDeadline("10/10/2018").build();
        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(INDEX_FIRST_TASK, 1);

        String expectedMessage = String.format(DeferDeadlineCommand.MESSAGE_SUCCESS, taskToDefer);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showTaskAtIndex(expectedModel, INDEX_FIRST_TASK);
        expectedModel.updateTask(taskToDefer, deferredTask);
        expectedModel.commitTaskBook();

        assertCommandSuccess(deferDeadlineCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(outOfBoundIndex, 1);
        assertCommandFailure(deferDeadlineCommand, model, commandHistory,
                deferDeadlineCommand.MESSAGE_NONEXISTENT_TASK);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task taskToDefer = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task duplicateTaskWithDiffDeadline = new TaskBuilder(taskToDefer).withDeadline("10/10/2018").build();
        model.addTask(duplicateTaskWithDiffDeadline);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(INDEX_FIRST_TASK, 1);
        assertCommandFailure(deferDeadlineCommand, model, commandHistory,
                deferDeadlineCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_sameTaskButDifferentDeadline() {
        Task taskToDefer = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task duplicateTaskWithDiffDeadline = new TaskBuilder(taskToDefer).withDeadline("10/10/2018").build();
        Task deferredTask = new TaskBuilder(taskToDefer).withDeadline("11/10/2018").build();
        model.addTask(duplicateTaskWithDiffDeadline);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        DeferDeadlineCommand deferDeadlineCommand = new DeferDeadlineCommand(INDEX_FIRST_TASK, 2);


        String expectedMessage = String.format(DeferDeadlineCommand.MESSAGE_SUCCESS, taskToDefer);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        expectedModel.updateTask(taskToDefer , deferredTask);
        Task task = expectedModel.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        System.out.println(String.format("Expectedtask is %1$s", task.getTitle()));
        Task task2 = expectedModel.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        System.out.println(String.format("Expectedtask2 is %1$s", task2.getTitle()));
        Task task3 = expectedModel.getFilteredTaskList().get(INDEX_THIRD_TASK.getZeroBased());
        System.out.println(String.format("Expectedtask3 is %1$s", task3.getTitle()));
        Task task4 = expectedModel.getFilteredTaskList().get(INDEX_FOURTH_TASK.getZeroBased());
        System.out.println(String.format("Expectedtask4 is %1$s", task4.getTitle()));
        expectedModel.commitTaskBook();

        assertCommandSuccess(deferDeadlineCommand, model, commandHistory, expectedMessage, expectedModel);
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
        public void commitTaskBook() {
            // called by {@code SelectDeadlineCommand#execute()}
        }
        @Override
        public ReadOnlyTaskBook getAddressBook() {
            return new AddressBook();
        }
    }
}
