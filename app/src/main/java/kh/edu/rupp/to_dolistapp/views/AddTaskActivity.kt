package kh.edu.rupp.to_dolistapp.views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Calendar;
import java.util.List;

import kh.edu.rupp.to_dolistapp.R;
import kh.edu.rupp.to_dolistapp.databinding.ActivityAddTaskBinding;
import kh.edu.rupp.to_dolistapp.models.TaskList;
import kh.edu.rupp.to_dolistapp.viewmodels.TaskViewModel;

public class AddTaskActivity extends AppCompatActivity {

    ActivityAddTaskBinding binding;
    TaskViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DataBinding
        binding = (ActivityAddTaskBinding) DataBindingUtil.setContentView(this, R.layout.activity_add_task);
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        setupChips();
        setupDateTimePicker();

        binding.backHomeBtn.setOnClickListener(v -> finish());
        binding.btnSaveTask.setOnClickListener(v -> {
            viewModel.insert();
            finish();
        });
    }

    private void setupChips() {
        binding.chipGroupPriority.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                Chip chip = findViewById(checkedIds.get(0));
                viewModel.priority.setValue(chip.getText().toString());
            }
        });

        binding.chipGroupTaskGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                Chip chip = findViewById(checkedIds.get(0));
                viewModel.group.setValue(chip.getText().toString());
            }
        });
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
                    viewModel.date.setValue(
                            months[month] + " " + day + ", " + year +
                                    " at " + displayHour + ":" + String.format("%02d", minute) + " " + amPm
                    );
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }
}
