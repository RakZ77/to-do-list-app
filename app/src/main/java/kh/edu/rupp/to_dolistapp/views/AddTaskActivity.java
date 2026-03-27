package kh.edu.rupp.to_dolistapp.views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Calendar;
import java.util.List;

import kh.edu.rupp.to_dolistapp.presenter.TaskPresenter;
import kh.edu.rupp.to_dolistapp.databinding.ActivityAddTaskBinding;
import kh.edu.rupp.to_dolistapp.models.TaskList;

public class AddTaskActivity extends AppCompatActivity implements TaskListView{

    ActivityAddTaskBinding binding;
    TaskPresenter taskPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskPresenter = new TaskPresenter(this, this);
        taskPresenter.loadTask();
        setupDateTimePicker();

        // When the save button is clicked -> save task
        binding.btnSaveTask.setOnClickListener(v -> {
            // View's job: read UI values and pass to controller
            String title    = binding.etTaskName.getText().toString().trim();
            String desc     = binding.etDescription.getText().toString().trim();
            String date     = binding.etDueDate.getText().toString().trim();
            String priority = getCheckedChipText(binding.chipGroupPriority, "Medium");
            String group    = getCheckedChipText(binding.chipGroupTaskGroup, "General");

            taskPresenter.saveTask(title, desc, date, priority, group);
        });

        // Arrow back button to return to TaskListActivity
        binding.backHomeBtn.setOnClickListener(view -> {
                    Intent intent = new Intent(this, TaskListActivity.class);
                    startActivity(intent);
        });
    }

    // MVP contract: presenter tells view to display task
    @Override
    public void loadTask(List<TaskList> taskList) {
        if (taskList.isEmpty()){
            binding.etTaskDemo.setText("No Task");
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (TaskList task : taskList) {
            builder.append("📌 ").append(task.title).append("\n")
                    .append("📝 ").append(task.description).append("\n")
                    .append("📅 ").append(task.date).append("\n")
                    .append("⚡ ").append(task.priority).append("\n")
                    .append("🏷️ ").append(task.group).append("\n\n");
        }
        binding.etTaskDemo.setText(builder.toString());
    }

    //  MVP contract: presenter tells view save succeeded → reset form + reload
    @Override
    public void onSaved() {
        binding.etTaskName.setText("");
        binding.etDescription.setText("");
        binding.etDueDate.setText("");
        taskPresenter.loadTask();
    }

    //  MVP contract: presenter tells view something went wrong
    @Override
    public void onError(String message) {
        if ("Title required".equals(message)) {
            binding.etTaskName.setError(message);
        }
    }

    // Helper: read selected chip text from a ChipGroup
    private String getCheckedChipText(ChipGroup chipGroup, String defaultValue) {
        int checkedId = chipGroup.getCheckedChipId();
        if (checkedId != View.NO_ID) {
            return ((Chip) binding.getRoot().findViewById(checkedId)).getText().toString();
        }
        return defaultValue;
    }

    private void setupDateTimePicker() {
        binding.etDueDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> {
                new TimePickerDialog(this, (timeView, hour, minute) -> {
                    String[] months = {"Jan","Feb","Mar","Apr","May","Jun",
                            "Jul","Aug","Sep","Oct","Nov","Dec"};
                    String amPm = hour < 12 ? "AM" : "PM";
                    int displayHour = hour % 12 == 0 ? 12 : hour % 12;
                    binding.etDueDate.setText(
                            months[month] + " " + day + ", " + year +
                                    " at " + displayHour + ":" + String.format("%02d", minute) + " " + amPm
                    );
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskPresenter.dispose();  // ✅ clean up disposables
    }
}
