package kh.edu.rupp.to_dolistapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kh.edu.rupp.to_dolistapp.models.TaskList

@Database(
    entities = [TaskList::class],
    version = 3,
    exportSchema = false
)
abstract class TaskListDb : RoomDatabase() {

    abstract fun taskListDao(): TaskListDao

    companion object {

        @Volatile
        private var INSTANCE: TaskListDb? = null

        fun getInstance(context: Context): TaskListDb {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskListDb::class.java,
                    "task_list_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
