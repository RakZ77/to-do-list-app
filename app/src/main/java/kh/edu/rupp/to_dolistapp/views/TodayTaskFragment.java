package kh.edu.rupp.to_dolistapp.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import kh.edu.rupp.to_dolistapp.adapters.TaskListAdapter;
import kh.edu.rupp.to_dolistapp.presenter.TaskPresenter;
import kh.edu.rupp.to_dolistapp.databinding.FragmentTodayTaskBinding;
import kh.edu.rupp.to_dolistapp.models.TaskList;

public class TodayTaskFragment extends Fragment implements TaskListView {

    private FragmentTodayTaskBinding binding;  // use fragment binding, not activity
    private TaskPresenter taskPresenter;
    private TaskListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // ✅Inflate via binding instead of R.layout directly
        binding = FragmentTodayTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init controller and adapter
        taskPresenter = new TaskPresenter(requireContext(), this);
        adapter = new TaskListAdapter();

        // Setup RecyclerView
        binding.todayTaskRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.todayTaskRecycler.setAdapter(adapter);

        //  Load tasks
        taskPresenter.loadTask();
    }

    @Override
    public void loadTask(List<TaskList> taskList){
        adapter.setTasks(taskList);
    }

    @Override
    public void onSaved(){

    }

    @Override
    public void onError(String message){
        Log.e("TodayTaskFragment", message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        taskPresenter.dispose();
        binding = null;  // ✅ prevent memory leak in fragments
    }
}
