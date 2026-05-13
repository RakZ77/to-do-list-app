package kh.edu.rupp.to_dolistapp.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TaskResponse {
    @SerializedName("in_progress")
    private List<Task> inProgress;

    @SerializedName("task_groups")
    private List<TaskGroup> taskGroups;

    public List<Task> getInProgress() {
        return inProgress;
    }

    public void setInProgress(List<Task> inProgress) {
        this.inProgress = inProgress;
    }

    public List<TaskGroup> getTaskGroups() {
        return taskGroups;
    }

    public void setTaskGroups(List<TaskGroup> taskGroups) {
        this.taskGroups = taskGroups;
    }
}