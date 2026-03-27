package kh.edu.rupp.to_dolistapp.controllers;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kh.edu.rupp.to_dolistapp.database.TaskListDao;
import kh.edu.rupp.to_dolistapp.database.TaskListDb;
import kh.edu.rupp.to_dolistapp.models.TaskList;

public class TaskController {
    private TaskListDao taskListDao;
    private CompositeDisposable disposable = new CompositeDisposable();

    public interface TaskCallBack{
        void onTaskLoaded(List<TaskList> taskList);
        void onError(String message);
    }

    public interface SaveCallBack{
        void onSaved();
        void onError(String message);
    }

    public TaskController(Context context) {
        taskListDao = TaskListDb.getInstance(context).taskListDao();
    }


    public void loadTask(TaskCallBack callback) {
        disposable.add(
                taskListDao.getAllTaskLists()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                callback::onTaskLoaded,
                                error-> callback.onError(error.getMessage())
                        )
        );
    }
    public void saveTask(String title, String description, String date, String priority, String group, SaveCallBack callback){

        // Validation
        if (title.isEmpty()) {
            callback.onError("Title required");
            return;
        }

        TaskList task = new TaskList(title, description, date, priority, group);
        disposable.add(
                taskListDao.insert(task)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                callback::onSaved,
                                error -> callback.onError(error.getMessage())
                        )
        );
    }

    public void dispose() {
        disposable.clear();
    }
}
