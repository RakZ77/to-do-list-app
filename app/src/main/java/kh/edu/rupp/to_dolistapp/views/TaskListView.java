package kh.edu.rupp.to_dolistapp.views;

import java.util.List;

import kh.edu.rupp.to_dolistapp.models.TaskList;

public interface TaskListView {
    void loadTask(List<TaskList> taskList);
    void onError(String message);
    void onSaved();
}
