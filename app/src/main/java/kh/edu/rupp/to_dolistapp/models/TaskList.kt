package kh.edu.rupp.to_dolistapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_list")
data class TaskList(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val description: String,
    val date: String,
    val priority: String,
    val group: String
)