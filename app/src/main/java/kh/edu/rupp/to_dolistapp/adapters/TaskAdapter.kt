package kh.edu.rupp.to_dolistapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.to_dolistapp.R
import kh.edu.rupp.to_dolistapp.models.Task

class TaskAdapter(tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder?>() {
    private var tasks: MutableList<Task> = ArrayList<Task>()

    init {
        this.tasks = tasks
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.progress_task_view, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks.get(position)
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskName: TextView
        private val taskTitle: TextView
        private val progressBar: ProgressBar
        private val cardBackground: CardView

        init {
            taskName = itemView.findViewById<TextView>(R.id.taskName)
            taskTitle = itemView.findViewById<TextView>(R.id.taskTitle)
            progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
            cardBackground = itemView.findViewById<CardView>(R.id.cardBackground)
        }

        fun bind(task: Task) {
            taskTitle.setText(task.title)
            taskName.setText(task.name)
            progressBar.setProgress(task.progress)

            val color = task.color

            if (!color.isNullOrEmpty()) {

                try {
                    cardBackground.setCardBackgroundColor(
                        Color.parseColor(color)
                    )

                } catch (e: IllegalArgumentException) {

                    cardBackground.setCardBackgroundColor(Color.GRAY)
                }
            }
        }
    }
}