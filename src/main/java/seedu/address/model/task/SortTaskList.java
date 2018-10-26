package seedu.address.model.task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

//@@author ChanChunCheong
public class SortTaskList {
    public ObservableList<Task> sortTask(ObservableList<Task> internalList, String method) {

        FXCollections.sort(internalList, new Comparator<Task>() {
            @Override
            public int compare(Task self, Task other) {
                switch(method) {
                    case ("modules"): {
                        return self.getModuleCode().compareTo(other.getModuleCode());
                    }
                    case ("deadlines"): {
                        return self.getDeadline().compareTo(other.getDeadline());
                    }
                    case ("priority"): {
                        return self.getPriorityLevel().toString().compareTo(other.getPriorityLevel().toString());
                    }
                    case ("title"): {
                        return self.getTitle().compareTo(other.getTitle());
                    }
                    default:
                        return 0;
                }
            }

        });
        return internalList;
    }
}
