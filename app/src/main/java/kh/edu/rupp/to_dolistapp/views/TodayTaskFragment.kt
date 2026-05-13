package kh.edu.rupp.to_dolistapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.to_dolistapp.R
import kh.edu.rupp.to_dolistapp.adapters.TaskListAdapter
import kh.edu.rupp.to_dolistapp.databinding.FragmentTodayTaskBinding
import kh.edu.rupp.to_dolistapp.viewmodels.TaskViewModel

class TodayTaskFragment : Fragment() {

    private var binding: FragmentTodayTaskBinding? = null
    private var viewModel: TaskViewModel? = null
    private var adapter: TaskListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_today_task, container, false
        )
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        adapter = TaskListAdapter()

        binding!!.setViewModel(viewModel)
        binding!!.setLifecycleOwner(viewLifecycleOwner)

        binding!!.todayTaskRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding!!.todayTaskRecycler.adapter = adapter

        // Fixed: taskList is LiveData<List<TaskList>> — observer receives List<TaskList>
        viewModel!!.taskList.observe(viewLifecycleOwner) { taskList ->
            adapter!!.setTasks(taskList ?: emptyList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
