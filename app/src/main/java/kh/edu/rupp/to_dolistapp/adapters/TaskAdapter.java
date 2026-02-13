package kh.edu.rupp.to_dolistapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kh.edu.rupp.to_dolistapp.R;
import kh.edu.rupp.to_dolistapp.models.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks = new ArrayList<>();

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progress_task_view, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView taskName;
        private TextView taskTitle;
        private ProgressBar progressBar;
        private View cardBackground;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            progressBar = itemView.findViewById(R.id.progressBar);
//            cardBackground = itemView.findViewById(R.id.cardBackground);
        }

        public void bind(Task task) {
            taskTitle.setText(task.getTitle());
            taskName.setText(task.getName());
            progressBar.setProgress(task.getProgress());

//            // Set background color
//            if (task.getColor() != null && !task.getColor().isEmpty()) {
//                try {
//                    cardBackground.setBackgroundColor(Color.parseColor(task.getColor()));
//                } catch (IllegalArgumentException e) {
//                    // Handle invalid color
//                }
//            }
        }
    }
}