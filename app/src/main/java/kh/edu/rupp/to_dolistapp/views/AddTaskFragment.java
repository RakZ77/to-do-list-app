package kh.edu.rupp.to_dolistapp.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kh.edu.rupp.to_dolistapp.databinding.FragmentAddTaskBinding;

public class AddTaskFragment extends Fragment {

    private RxDataStore<Preferences> dataStore;
    private static final Preferences.Key<String> taskKey = PreferencesKeys.stringKey("task_list");
    private FragmentAddTaskBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataStore = new RxPreferenceDataStoreBuilder(requireContext(),"tasks").build();
        loadTasks();


        binding.addButton.setOnClickListener(v -> {

            String title = binding.editTextTitle.getText().toString();
            String description = binding.editTextDescription.getText().toString();

            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter task details", Toast.LENGTH_SHORT).show();
                return;
            }

            saveTask(title);
        });

    }

    private void saveTask(String title) {
        dataStore.updateDataAsync(prefs -> {
                    MutablePreferences mutablePreferences = prefs.toMutablePreferences();
                    String json = mutablePreferences.get(taskKey);
                    List<String> tasks = new ArrayList<>();
                    if (json != null){
                        tasks = new Gson().fromJson(json, new TypeToken<List<String>>(){}.getType());
                    }
                    tasks.add(title);
                    String newJson = new Gson().toJson(tasks);
                    mutablePreferences.set(taskKey, newJson);

                    return Single.just(mutablePreferences);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        prefs -> {
                            Log.i("MY PREFS", prefs.toString());
                            binding.editTextTitle.setText("");
                            binding.editTextDescription.setText("");
                        },
                        error -> {
                            Log.e("MY ERROR", error.getMessage());
                        }
                );

    }

    private void loadTasks(){
        dataStore.data().map(prefs->{
            String task = prefs.get(taskKey);
            return task != null ? task : "No Task";
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(task -> {
                },error -> {
                    Log.e("MY ERROR", error.getMessage());
                }
        );

    }
}