package kh.edu.rupp.to_dolistapp.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kh.edu.rupp.to_dolistapp.R
import kh.edu.rupp.to_dolistapp.adapters.MyViewPagerAdapter

class TaskListFragment : Fragment() {
    var btnAdd: MaterialButton? = null
    var tabLayout: TabLayout? = null
    var viewPager2: ViewPager2? = null
    var myViewPagerAdapter: MyViewPagerAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        viewPager2 = view.findViewById<ViewPager2>(R.id.viewPager)
        myViewPagerAdapter = MyViewPagerAdapter(requireActivity())
        viewPager2!!.setAdapter(myViewPagerAdapter)

        // Set up TabLayout with ViewPager2 by showing selected tab
        tabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2!!.setCurrentItem(tab.getPosition())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        // Set up ViewPager2 to show selected tab
        viewPager2!!.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout!!.getTabAt(position)!!.select()
            }
        })

        // Add Task Button to show AddTaskActivity
        btnAdd = view.findViewById<MaterialButton>(R.id.btnAdd)
        btnAdd!!.setOnClickListener(View.OnClickListener { v: View? ->
            startActivity(Intent(requireContext(), AddTaskActivity::class.java))
        })
        return view
    }
}
