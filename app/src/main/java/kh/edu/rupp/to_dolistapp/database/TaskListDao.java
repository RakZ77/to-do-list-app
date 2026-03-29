package kh.edu.rupp.to_dolistapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import kh.edu.rupp.to_dolistapp.models.TaskList;

@Dao
public interface TaskListDao {
    @Insert
    Completable insert(TaskList taskList);

    @Query("SELECT * FROM task_list")
    LiveData<List<TaskList>> getAllTaskLists();

    @Query("SELECT * FROM task_list WHERE id = :id")
    LiveData<TaskList> getTaskListById(int id);

    @Query("DELETE FROM task_list WHERE id = :id")
    Completable deleteTaskListById(int id);

    @Query("DELETE FROM task_list")
    Completable deleteAllTaskLists();
}
