package kh.edu.rupp.to_dolistapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kh.edu.rupp.to_dolistapp.models.TaskList
import kh.edu.rupp.to_dolistapp.repositories.TaskRepository

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository = TaskRepository(application)

    // Fixed: LiveData<List<TaskList>> — matches repository and Room, no nullable wrapper
    val taskList: LiveData<List<TaskList>> = repository.allTaskLists

    // Two-way bindable fields
    @JvmField
    var title: MutableLiveData<String?> = MutableLiveData("")
    @JvmField
    var description: MutableLiveData<String?> = MutableLiveData("")
    @JvmField
    var date: MutableLiveData<String?> = MutableLiveData("")
    @JvmField
    var priority: MutableLiveData<String?> = MutableLiveData("Medium")
    @JvmField
    var group: MutableLiveData<String?> = MutableLiveData("General")

    fun insert() {
        val t = title.value
        if (t.isNullOrEmpty()) return

        repository.insert(
            t,
            description.value ?: "",
            date.value ?: "",
            priority.value ?: "Medium",
            group.value ?: "General"
        )
        clearFields()
    }

    private fun clearFields() {
        title.value = ""
        description.value = ""
        date.value = ""
    }
}
