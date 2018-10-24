package seedu.address.model.task;

import java.util.Collections;
import java.util.Comparator;

import javafx.collections.ObservableList;

//@@author ChanChunCheong
public class SortTaskList {
    public ObservableList<Task> sortTask(ObservableList<Task> internalList, String method) {
        Collections.sort(internalList, new Comparator<Task>() {
            @Override
            public int compare(Task self, Task other) {
                //add the different comparisons here
                if (method.equals("modules")) {
                    return self.getModuleCode().compareTo(other.getModuleCode());
                } else if (method.equals("deadlines")) {
                    return self.getDeadline().compareTo(other.getDeadline());
                } else {
                    return 0;
                }
            }
        });
        return internalList;
    }
}
