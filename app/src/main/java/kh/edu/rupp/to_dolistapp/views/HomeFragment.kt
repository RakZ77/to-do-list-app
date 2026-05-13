package kh.edu.rupp.to_dolistapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder
import androidx.datastore.rxjava3.RxDataStore
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kh.edu.rupp.to_dolistapp.NetworkUtil.isNetworkAvailable
import kh.edu.rupp.to_dolistapp.adapters.TaskAdapter
import kh.edu.rupp.to_dolistapp.adapters.TaskGroupAdapter
import kh.edu.rupp.to_dolistapp.databinding.FragmentHomeBinding
import kh.edu.rupp.to_dolistapp.models.Task
import kh.edu.rupp.to_dolistapp.models.TaskGroup
import kh.edu.rupp.to_dolistapp.models.TaskResponse
import kh.edu.rupp.to_dolistapp.services.TaskService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private var taskAdapter: TaskAdapter? = null
    private var taskGroupAdapter: TaskGroupAdapter? = null
    private val inProgressTasks: MutableList<Task> = ArrayList()
    private val taskGroups: MutableList<TaskGroup> = ArrayList()
    private var binding: FragmentHomeBinding? = null
    // Fixed: RxDataStore<Preferences> — Preferences must not be nullable
    private var dataStore: RxDataStore<Preferences>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataStore = RxPreferenceDataStoreBuilder(requireContext(), "tasks").build()

        taskAdapter = TaskAdapter(inProgressTasks)
        binding!!.recyclerViewProgress.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.recyclerViewProgress.adapter = taskAdapter

        taskGroupAdapter = TaskGroupAdapter(taskGroups)
        binding!!.recyclerViewTaskGroups.layoutManager = LinearLayoutManager(context)
        binding!!.recyclerViewTaskGroups.adapter = taskGroupAdapter

        if (isNetworkAvailable(requireContext())) {
            fetchTasks()
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
            loadTasksFromLocal()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isNetworkAvailable(requireContext())) {
            loadTasksFromLocal()
        }
    }

    private fun fetchTasks() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(TaskService::class.java)
        // Fixed: Call<TaskResponse> — no nullable type argument
        val call: Call<TaskResponse> = service.getTasks()

        call.enqueue(object : Callback<TaskResponse> {
            // Fixed: override signature must match Callback<TaskResponse> exactly
            override fun onResponse(call: Call<TaskResponse>, response: Response<TaskResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val taskResponse = response.body()!!

                    inProgressTasks.clear()
                    inProgressTasks.addAll(taskResponse.inProgress)
                    taskAdapter?.notifyDataSetChanged()
                    Log.d(TAG, "In Progress tasks loaded: ${inProgressTasks.size}")

                    taskGroups.clear()
                    taskGroups.addAll(taskResponse.taskGroups)
                    taskGroupAdapter?.notifyDataSetChanged()
                    Log.d(TAG, "Task Groups loaded: ${taskGroups.size}")
                } else {
                    Toast.makeText(context, "Failed to load tasks", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                Toast.makeText(context, "Network Error!", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "API Error: ${t.message}")
            }
        })
    }

    private fun loadTasksFromLocal() {
        dataStore!!.data()
            .map<String> { prefs -> prefs[taskKey] ?: "" }  // return empty string if key absent
            .filter { it.isNotEmpty() }                      // skip if nothing stored yet
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { json ->
                    Log.d(TAG, "Loaded JSON: $json")
                    val titles = Gson().fromJson<MutableList<String>>(
                        json,
                        object : TypeToken<MutableList<String>>() {}.type
                    )
                    for (title in titles) {
                        inProgressTasks.add(Task(title))
                    }
                    taskAdapter?.notifyDataSetChanged()
                },
                { error -> Log.e(TAG, error.message ?: "Unknown error") }
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val TAG = "HomeFragment"
        // Fixed: Preferences.Key<String> — key type must not be nullable
        private val taskKey: Preferences.Key<String> = stringPreferencesKey("task_list")
    }
}
