package kh.edu.rupp.to_dolistapp.database;

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
    Flowable<List<TaskList>> getAllTaskLists();

    @Query("SELECT * FROM task_list WHERE id = :id")
    Flowable <TaskList> getTaskListById(int id);
}
