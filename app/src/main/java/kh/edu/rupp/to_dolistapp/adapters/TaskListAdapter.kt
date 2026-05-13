package kh.edu.rupp.to_dolistapp.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.to_dolistapp.R
import kh.edu.rupp.to_dolistapp.models.TaskList

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private var taskList: List<TaskList> = emptyList()

    // Fixed: accepts List<TaskList> — matches what Room/LiveData emits
    fun setTasks(tasks: List<TaskList>) {
        this.taskList = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.today_task_view, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int = taskList.size

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTitle: TextView = itemView.findViewById(R.id.taskTitle)
        private val dueDate: TextView = itemView.findViewById(R.id.dueDate)
        private val taskGroup: TextView = itemView.findViewById(R.id.taskGroup)
        private val taskPriority: View = itemView.findViewById(R.id.taskPriority)
        private val checkbox: CheckBox? = itemView.findViewById(R.id.checkbox)

        fun bind(task: TaskList) {
            taskTitle.text = task.title
            dueDate.text = task.date
            taskGroup.text = task.group

            val color = when (task.priority) {
                "High" -> Color.parseColor("#FF5252")
                "Low"  -> Color.parseColor("#69F0AE")
                else   -> Color.parseColor("#FFD740")
            }
            taskPriority.backgroundTintList = ColorStateList.valueOf(color)
        }
    }
}
