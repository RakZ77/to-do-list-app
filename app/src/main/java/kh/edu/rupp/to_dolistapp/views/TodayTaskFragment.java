package kh.edu.rupp.to_dolistapp.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import kh.edu.rupp.to_dolistapp.R;
import kh.edu.rupp.to_dolistapp.adapters.TaskListAdapter;
import kh.edu.rupp.to_dolistapp.databinding.FragmentTodayTaskBinding;
import kh.edu.rupp.to_dolistapp.models.TaskList;
import kh.edu.rupp.to_dolistapp.viewmodels.TaskViewModel;

public class TodayTaskFragment extends Fragment {

    private FragmentTodayTaskBinding binding;  // use fragment binding, not activity
    private TaskViewModel viewModel;
    private TaskListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate via binding instead of R.layout directly
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today_task, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init controller and adapter
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        adapter = new TaskListAdapter();

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());


        // Setup RecyclerView
        binding.todayTaskRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.todayTaskRecycler.setAdapter(adapter);

        //  Load tasks
        viewModel.taskList.observe(getViewLifecycleOwner(), taskList -> {
            adapter.setTasks(taskList);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;  // avoid memory leak
        // no presenter to dispose — ViewModel handles lifecycle
    }
}
