package kh.edu.rupp.to_dolistapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kh.edu.rupp.to_dolistapp.R
import kh.edu.rupp.to_dolistapp.models.TaskGroup

class TaskGroupAdapter(
    private var taskGroups: MutableList<TaskGroup>
) : RecyclerView.Adapter<TaskGroupAdapter.TaskGroupViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskGroupViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_group_view, parent, false)

        return TaskGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskGroupViewHolder, position: Int) {
        holder.bind(taskGroups[position])
    }

    override fun getItemCount(): Int = taskGroups.size

    class TaskGroupViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val taskName: TextView =
            itemView.findViewById(R.id.taskName)

        private val taskNumber: TextView =
            itemView.findViewById(R.id.taskNumber)

        private val taskProgress: TextView =
            itemView.findViewById(R.id.taskProgress)

        private val taskIcon: ImageView =
            itemView.findViewById(R.id.taskIcon)

        private val iconBackground: CardView =
            itemView.findViewById(R.id.iconBackground)

        fun bind(taskGroup: TaskGroup) {

            taskName.text = taskGroup.name
            taskNumber.text = "${taskGroup.tasks} Tasks"
            taskProgress.text = "${taskGroup.progress}%"

            Picasso.get()
                .load(taskGroup.icon)
                .fit()
                .centerCrop()
                .into(taskIcon)

            val color = taskGroup.color

            if (!color.isNullOrEmpty()) {

                try {

                    iconBackground.setCardBackgroundColor(
                        Color.parseColor(color)
                    )

                } catch (e: IllegalArgumentException) {

                    iconBackground.setCardBackgroundColor(Color.GRAY)
                }
            }
        }
    }
}