package kh.edu.rupp.to_dolistapp.views

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
import kh.edu.rupp.to_dolistapp.adapters.UserViewPagerAdapter

class UserListFragment : Fragment() {
    private var tabLayout: TabLayout? = null
    private var viewPager2: ViewPager2? = null
    private var userViewPagerAdapter: UserViewPagerAdapter? = null
    private val btnAdd: MaterialButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)

        tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        viewPager2 = view.findViewById<ViewPager2>(R.id.viewPager)

        userViewPagerAdapter = UserViewPagerAdapter(requireActivity())
        viewPager2!!.setAdapter(userViewPagerAdapter)

        tabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2!!.setCurrentItem(tab.getPosition())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewPager2!!.registerOnPageChangeCallback(
            object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    val tab = tabLayout!!.getTabAt(position)

                    if (tab != null) {
                        tab.select()
                    }
                }
            })

        return view
    }
}