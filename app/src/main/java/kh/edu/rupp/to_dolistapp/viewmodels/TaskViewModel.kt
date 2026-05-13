package kh.edu.rupp.to_dolistapp.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import kh.edu.rupp.to_dolistapp.models.TaskList;
import kh.edu.rupp.to_dolistapp.repositories.TaskRepository;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    public LiveData<List<TaskList>> taskList;

    //  Two-way bindable fields
    public MutableLiveData<String> title       = new MutableLiveData<>("");
    public MutableLiveData<String> description = new MutableLiveData<>("");
    public MutableLiveData<String> date        = new MutableLiveData<>("");
    public MutableLiveData<String> priority    = new MutableLiveData<>("Medium");
    public MutableLiveData<String> group       = new MutableLiveData<>("General");

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        taskList = repository.getAllTaskLists(); //auto-updates via LiveData
    }

    public void insert(){
        String t = title.getValue();
        if(t == null || t.isEmpty()) return;

        repository.insert(t, description.getValue(), date.getValue(), priority.getValue(), group.getValue());
        clearFields();
    }
    private void clearFields() {
        title.setValue("");
        description.setValue("");
        date.setValue("");
    }


}
