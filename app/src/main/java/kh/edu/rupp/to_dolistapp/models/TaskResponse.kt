package kh.edu.rupp.to_dolistapp.models

import com.google.gson.annotations.SerializedName

class TaskResponse {

    @SerializedName("in_progress")
    var inProgress: List<Task> = emptyList()

    @SerializedName("task_groups")
    var taskGroups: List<TaskGroup> = emptyList()
}