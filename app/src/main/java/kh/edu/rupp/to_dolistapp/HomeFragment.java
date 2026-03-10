package kh.edu.rupp.to_dolistapp;

import static kh.edu.rupp.to_dolistapp.NetworkUtil.isNetworkAvailable;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kh.edu.rupp.to_dolistapp.adapters.TaskAdapter;
import kh.edu.rupp.to_dolistapp.adapters.TaskGroupAdapter;
import kh.edu.rupp.to_dolistapp.databinding.FragmentHomeBinding;
import kh.edu.rupp.to_dolistapp.models.Task;
import kh.edu.rupp.to_dolistapp.models.TaskGroup;
import kh.edu.rupp.to_dolistapp.models.TaskResponse;
import kh.edu.rupp.to_dolistapp.services.TaskService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private TaskAdapter taskAdapter;
    private TaskGroupAdapter taskGroupAdapter;
    private List<Task> inProgressTasks = new ArrayList<>();
    private List<TaskGroup> taskGroups = new ArrayList<>();
    private FragmentHomeBinding binding;
    private static final String TAG = "HomeFragment";
    private RxDataStore<Preferences> dataStore;
    private Preferences.Key<String> taskKey = PreferencesKeys.stringKey("task_title");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataStore = new RxPreferenceDataStoreBuilder(
                requireContext(),
                "tasks"
        ).build();

        // Initialize In Progress RecyclerView (Horizontal)
        taskAdapter = new TaskAdapter(inProgressTasks);
        binding.recyclerViewProgress.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewProgress.setAdapter(taskAdapter);

        // Initialize Task Groups RecyclerView (Vertical)
        taskGroupAdapter = new TaskGroupAdapter(taskGroups);
        binding.recyclerViewTaskGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewTaskGroups.setAdapter(taskGroupAdapter);

        // Fetch data from API if online, otherwise from local storage
        if (isNetworkAvailable(requireContext())) {
            getTasks();
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            loadTasksFromLocal();
        }
    }

    private void getTasks() {
        Retrofit httpsClient = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TaskService service = httpsClient.create(TaskService.class);
        Call<TaskResponse> call = service.getTasks();

        call.enqueue(new retrofit2.Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, retrofit2.Response<TaskResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TaskResponse taskResponse = response.body();

                    // Update In Progress tasks
                    if (taskResponse.getInProgress() != null) {
                        inProgressTasks.clear();
                        inProgressTasks.addAll(taskResponse.getInProgress());
                        taskAdapter.notifyDataSetChanged();
                        Log.d(TAG, "In Progress tasks loaded: " + inProgressTasks.size());
                    }

                    // Update Task Groups
                    if (taskResponse.getTaskGroups() != null) {
                        taskGroups.clear();
                        taskGroups.addAll(taskResponse.getTaskGroups());
                        taskGroupAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Task Groups loaded: " + taskGroups.size());
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to load tasks", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "API Error: " + t.getMessage());
            }
        });
    }

    private void loadTasksFromLocal(){
        dataStore.data()
                .map(prefs -> prefs.get(taskKey))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(title -> {

                    if (title != null) {

                        Task task = new Task(title);

                        inProgressTasks.clear();
                        inProgressTasks.add(task);

                        taskAdapter.notifyDataSetChanged();
                    }

                }, error -> {
                    Log.e(TAG, error.getMessage());
                });
    }
}