package kh.edu.rupp.to_dolistapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kh.edu.rupp.to_dolistapp.models.TaskList;

@Database(entities = {TaskList.class}, version = 2, exportSchema = false)
public abstract class TaskListDb extends RoomDatabase {

    public abstract TaskListDao taskListDao();
    public static TaskListDb INSTANCE;
    public static TaskListDb getInstance(Context context){
        if (INSTANCE == null){
            synchronized (TaskListDb.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskListDb.class, "task_list_db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
