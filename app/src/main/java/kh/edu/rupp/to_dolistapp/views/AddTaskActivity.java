package kh.edu.rupp.to_dolistapp.views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kh.edu.rupp.to_dolistapp.R;
import kh.edu.rupp.to_dolistapp.controllers.TaskController;
import kh.edu.rupp.to_dolistapp.database.TaskListDao;
import kh.edu.rupp.to_dolistapp.database.TaskListDb;
import kh.edu.rupp.to_dolistapp.databinding.ActivityAddTaskBinding;
import kh.edu.rupp.to_dolistapp.models.TaskList;

public class AddTaskActivity extends AppCompatActivity{

    ActivityAddTaskBinding binding;
    TaskController taskController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskController = new TaskController(this);
        loadAndDisplayTasks();
        setupDateTimePicker();

        // When the save button is clicked -> save task
        binding.btnSaveTask.setOnClickListener(v -> {
            // View's job: read UI values and pass to controller
            String title    = binding.etTaskName.getText().toString().trim();
            String desc     = binding.etDescription.getText().toString().trim();
            String date     = binding.etDueDate.getText().toString().trim();
            String priority = getCheckedChipText(binding.chipGroupPriority, "Medium");
            String group    = getCheckedChipText(binding.chipGroupTaskGroup, "General");

            taskController.saveTask(title, desc, date, priority, group, new TaskController.SaveCallBack() {
                @Override
                public void onSaved() {
                    // View's job: update UI after save
                    binding.etTaskName.setText("");
                    binding.etDescription.setText("");
                    binding.etDueDate.setText("");
                    loadAndDisplayTasks();
                }

                @Override
                public void onError(String message) {
                    if (message.equals("Title required"))
                        binding.etTaskName.setError(message);
                }
            });
        });

        // Arrow back button to return to TaskListActivity
        binding.backHomeBtn.setOnClickListener(view -> {
                    Intent intent = new Intent(this, TaskListActivity.class);
                    startActivity(intent);
        });
    }
    private void loadAndDisplayTasks() {
        taskController.loadTask(new TaskController.TaskCallBack() {
            @Override
            public void onTaskLoaded(List<TaskList> taskList) {
                // View's job: format and display data
                if (!taskList.isEmpty()) {
                    StringBuilder builder = new StringBuilder();
                    for (TaskList task : taskList) {
                        builder.append("📌 ").append(task.title).append("\n")
                                .append("📝 ").append(task.description).append("\n")
                                .append("📅 ").append(task.date).append("\n")
                                .append("⚡ ").append(task.priority).append("\n")
                                .append("🏷️ ").append(task.group).append("\n\n");
                    }
                    binding.etTaskDemo.setText(builder.toString());
                } else {
                    binding.etTaskDemo.setText("No Task");
                }
            }

            @Override
            public void onError(String message) {
                Log.e("LOAD ERROR", message);
            }
        });
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
        taskController.dispose();  // ✅ clean up disposables
    }
}
