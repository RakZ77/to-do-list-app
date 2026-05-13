package kh.edu.rupp.to_dolistapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kh.edu.rupp.to_dolistapp.views.CompletedFragment
import kh.edu.rupp.to_dolistapp.views.TodayTaskFragment
import kh.edu.rupp.to_dolistapp.views.UpcomingFragment

class MyViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    // Create fragment based on position
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return TodayTaskFragment()
            1 -> return UpcomingFragment()
            2 -> return CompletedFragment()
            else -> return TodayTaskFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}
