package kh.edu.rupp.to_dolistapp.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;
import kh.edu.rupp.to_dolistapp.database.TaskListDao;
import kh.edu.rupp.to_dolistapp.database.TaskListDb;
import kh.edu.rupp.to_dolistapp.models.TaskList;

public class TaskRepository {
    private TaskListDao taskListDao;

    public TaskRepository(Application app) {
        taskListDao = TaskListDb.getInstance(app).taskListDao();
    }

    public LiveData<List<TaskList>> getAllTaskLists() {
        return taskListDao.getAllTaskLists(); //  Room auto-delivers on main thread
    }

    public void insert(String title, String description,
                       String date, String priority, String group) {

        TaskList task = new TaskList(title, description, date, priority, group);
        taskListDao.insert(task)
                .subscribeOn(Schedulers.io())  //  runs DB work on IO thread
                .subscribe(
                        () -> Log.i("db", "Saved"),
                        error -> Log.e("db", "Error: " + error.getMessage())
                );
    }
}
