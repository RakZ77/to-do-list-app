package kh.edu.rupp.to_dolistapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_list")
public class TaskList {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public String date;
    public String priority;
    public String group;

    public TaskList(String title, String description, String date, String priority, String group){
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.group = group;
    }

}
