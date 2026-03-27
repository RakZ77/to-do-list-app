package kh.edu.rupp.to_dolistapp.presenter;

import android.content.Context;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kh.edu.rupp.to_dolistapp.database.TaskListDao;
import kh.edu.rupp.to_dolistapp.database.TaskListDb;
import kh.edu.rupp.to_dolistapp.models.TaskList;
import kh.edu.rupp.to_dolistapp.views.TaskListView;

public class TaskPresenter {
    private TaskListDao taskListDao;
    private TaskListView view;
    private CompositeDisposable disposable = new CompositeDisposable();

    public TaskPresenter(Context context, TaskListView view) {
        this.view = view;
        taskListDao = TaskListDb.getInstance(context).taskListDao();
    }



    public void loadTask() {
        disposable.add(
                taskListDao.getAllTaskLists()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                view::loadTask,
                                error-> view.onError(error.getMessage())
                        )
        );
    }
    public void saveTask(String title, String description, String date, String priority, String group){

        // Validation
        if (title.isEmpty()) {
            view.onError("Title required");
            return;
        }

        TaskList task = new TaskList(title, description, date, priority, group);
        disposable.add(
                taskListDao.insert(task)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                view::onSaved,
                                error -> view.onError(error.getMessage())
                        )
        );
    }

    public void dispose() {
        disposable.clear();
    }
}
