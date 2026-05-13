package kh.edu.rupp.to_dolistapp.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kh.edu.rupp.to_dolistapp.R;
import kh.edu.rupp.to_dolistapp.models.TaskList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private List<TaskList> taskList = new ArrayList<>();

    public void setTasks(List<TaskList> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.today_task_view, parent, false); // your XML layout
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskList task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() { return taskList.size(); }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, dueDate, taskGroup;
        View taskPriority;
        CheckBox checkbox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle    = itemView.findViewById(R.id.taskTitle);
            dueDate      = itemView.findViewById(R.id.dueDate);
            taskGroup    = itemView.findViewById(R.id.taskGroup);
            taskPriority = itemView.findViewById(R.id.taskPriority);
            checkbox     = itemView.findViewById(R.id.checkbox);
        }

        public void bind(TaskList task) {
            taskTitle.setText(task.title);
            dueDate.setText(task.date);
            taskGroup.setText(task.group);

            // Set priority dot color
            int color;
            switch (task.priority) {
                case "High":   color = Color.parseColor("#FF5252"); break;
                case "Low":    color = Color.parseColor("#69F0AE"); break;
                default:       color = Color.parseColor("#FFD740"); break; // Medium
            }
            taskPriority.setBackgroundTintList(
                    ColorStateList.valueOf(color)
            );
        }
    }
}