package kh.edu.rupp.to_dolistapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kh.edu.rupp.to_dolistapp.adapters.UserAdapter
import kh.edu.rupp.to_dolistapp.databinding.FragmentAllBinding
import kh.edu.rupp.to_dolistapp.viewmodels.UserViewModel

@AndroidEntryPoint
class AllFragment : Fragment() {

    private var binding: FragmentAllBinding? = null
    private var adapter: UserAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserAdapter()
        binding!!.allRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding!!.allRecyclerView.adapter = adapter

        // Fixed: ViewModelProvider.get() with non-nullable type, no <UserViewModel?>
        val viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        // Fixed: users LiveData is non-nullable, observer receives MutableList<User>
        viewModel.users.observe(viewLifecycleOwner) { users ->
            adapter!!.setUsers(users)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
