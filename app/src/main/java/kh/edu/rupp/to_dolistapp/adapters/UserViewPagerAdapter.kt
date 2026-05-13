package kh.edu.rupp.to_dolistapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kh.edu.rupp.to_dolistapp.views.AllFragment
import kh.edu.rupp.to_dolistapp.views.CachedFragment
import kh.edu.rupp.to_dolistapp.views.LocalFragment

class UserViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AllFragment()
            1 -> return CachedFragment()
            2 -> return LocalFragment()
            else -> return AllFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}
