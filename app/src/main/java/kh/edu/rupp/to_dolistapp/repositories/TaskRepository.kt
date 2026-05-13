package kh.edu.rupp.to_dolistapp.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.schedulers.Schedulers
import kh.edu.rupp.to_dolistapp.database.TaskListDao
import kh.edu.rupp.to_dolistapp.database.TaskListDb
import kh.edu.rupp.to_dolistapp.models.TaskList

class TaskRepository(app: Application) {

    private val taskListDao: TaskListDao =
        TaskListDb.getInstance(app).taskListDao()

    // Fixed: LiveData<List<TaskList>> matches what Room's @Query returns — no nullability
    val allTaskLists: LiveData<List<TaskList>>
        get() = taskListDao.getAllTaskLists()

    fun insert(
        title: String,
        description: String,
        date: String,
        priority: String,
        group: String
    ) {
        val task = TaskList(
            title = title,
            description = description,
            date = date,
            priority = priority,
            group = group
        )

        // Fixed: insert() returns Completable (not nullable), no ?. needed
        taskListDao.insert(task)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Log.i("db", "Saved") },
                { error -> Log.e("db", "Error: ${error.message}") }
            )
    }
}
