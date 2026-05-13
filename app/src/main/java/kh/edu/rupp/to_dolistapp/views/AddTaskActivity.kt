package kh.edu.rupp.to_dolistapp.views

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kh.edu.rupp.to_dolistapp.R
import kh.edu.rupp.to_dolistapp.databinding.ActivityAddTaskBinding
import kh.edu.rupp.to_dolistapp.viewmodels.TaskViewModel
import java.util.Calendar

class AddTaskActivity : AppCompatActivity() {
    var binding: ActivityAddTaskBinding? = null
    var viewModel: TaskViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DataBinding
        binding = DataBindingUtil.setContentView<ViewDataBinding?>(
            this,
            R.layout.activity_add_task
        ) as ActivityAddTaskBinding
        viewModel = ViewModelProvider(this).get<TaskViewModel>(TaskViewModel::class.java)
        binding!!.setViewModel(viewModel)
        binding!!.setLifecycleOwner(this)

        setupChips()
        setupDateTimePicker()

        binding!!.backHomeBtn.setOnClickListener(View.OnClickListener { v: View? -> finish() })
        binding!!.btnSaveTask.setOnClickListener(View.OnClickListener { v: View? ->
            viewModel!!.insert()
            finish()
        })
    }

    private fun setupChips() {
        binding!!.chipGroupPriority.setOnCheckedStateChangeListener(ChipGroup.OnCheckedStateChangeListener { group: ChipGroup?, checkedIds: MutableList<Int?>? ->
            if (!checkedIds!!.isEmpty()) {
                val chip = findViewById<Chip>(checkedIds.get(0)!!)
                viewModel!!.priority.setValue(chip.getText().toString())
            }
        })

        binding!!.chipGroupTaskGroup.setOnCheckedStateChangeListener(ChipGroup.OnCheckedStateChangeListener { group: ChipGroup?, checkedIds: MutableList<Int?>? ->
            if (!checkedIds!!.isEmpty()) {
                val chip = findViewById<Chip>(checkedIds.get(0)!!)
                viewModel!!.group.setValue(chip.getText().toString())
            }
        })
    }

    private fun setupDateTimePicker() {
        binding!!.etDueDate.setOnClickListener(View.OnClickListener { v: View? ->
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this, OnDateSetListener { view: DatePicker?, year: Int, month: Int, day: Int ->
                    TimePickerDialog(
                        this,
                        OnTimeSetListener { timeView: TimePicker?, hour: Int, minute: Int ->
                            val months = arrayOf<String?>(
                                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                            )
                            val amPm = if (hour < 12) "AM" else "PM"
                            val displayHour = if (hour % 12 == 0) 12 else hour % 12
                            viewModel!!.date.setValue(
                                months[month] + " " + day + ", " + year +
                                        " at " + displayHour + ":" + String.format(
                                    "%02d",
                                    minute
                                ) + " " + amPm
                            )
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false
                    ).show()
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        })
    }
}
