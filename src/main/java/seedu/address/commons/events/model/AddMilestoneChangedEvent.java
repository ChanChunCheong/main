package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskBook;

//@@author JeremyInElysium
/** Indicates the AddressBook in the model has changed*/
public class AddMilestoneChangedEvent extends BaseEvent {

    public final ReadOnlyTaskBook data;

    public AddMilestoneChangedEvent(ReadOnlyTaskBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size();
    }
}
