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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kh.edu.rupp.to_dolistapp.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {

    // Fixed: RxDataStore<Preferences> — not nullable Preferences
    private var dataStore: RxDataStore<Preferences>? = null
    private var binding: FragmentAddTaskBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataStore = RxPreferenceDataStoreBuilder(requireContext(), "tasks").build()
        loadTasks()

        binding!!.addButton.setOnClickListener {
            val title = binding!!.editTextTitle.text.toString()
            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter task details", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            saveTask(title)
        }
    }

    private fun saveTask(title: String) {
        dataStore!!.updateDataAsync { prefs ->
            // Fixed: prefs is Preferences (non-null), use [] operator for key access
            val mutablePreferences = prefs.toMutablePreferences()
            val json = mutablePreferences[taskKey]
            var tasks: MutableList<String> = ArrayList()
            if (json != null) {
                tasks = Gson().fromJson(
                    json,
                    object : TypeToken<MutableList<String>>() {}.type
                )
            }
            tasks.add(title)
            // Fixed: set via [] operator, cast result as Preferences for Single
            mutablePreferences[taskKey] = Gson().toJson(tasks)
            Single.just(mutablePreferences as Preferences)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { prefs ->
                    Log.i("MY PREFS", prefs.toString())
                    binding!!.editTextTitle.setText("")
                    binding!!.editTextDescription.setText("")
                },
                { error -> Log.e("MY ERROR", error.message ?: "Unknown error") }
            )
    }

    private fun loadTasks() {
        dataStore!!.data()
            // Fixed: prefs is Preferences (non-null), [] operator replaces .get()
            .map { prefs -> prefs[taskKey] ?: "No Task" }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { task -> Log.d("MY TASK", task) },
                { error -> Log.e("MY ERROR", error.message ?: "Unknown error") }
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        // Fixed: Preferences.Key<String> — not nullable
        private val taskKey: Preferences.Key<String> = stringPreferencesKey("task_list")
    }
}
