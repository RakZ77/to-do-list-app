package kh.edu.rupp.to_dolistapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import kh.edu.rupp.to_dolistapp.models.TaskList

@Dao
interface TaskListDao {

    @Insert
    fun insert(taskList: TaskList): Completable

    // Returns LiveData<List<TaskList>> — no nullability, matches what Room generates
    @Query("SELECT * FROM task_list")
    fun getAllTaskLists(): LiveData<List<TaskList>>

    @Query("SELECT * FROM task_list WHERE id = :id")
    fun getTaskListById(id: Int): LiveData<TaskList>

    @Query("DELETE FROM task_list WHERE id = :id")
    fun deleteTaskListById(id: Int): Completable

    @Query("DELETE FROM task_list")
    fun deleteAllTaskLists(): Completable
}
