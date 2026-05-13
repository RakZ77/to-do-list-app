package kh.edu.rupp.to_dolistapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import kh.edu.rupp.to_dolistapp.views.CompletedFragment;
import kh.edu.rupp.to_dolistapp.views.TodayTaskFragment;
import kh.edu.rupp.to_dolistapp.views.UpcomingFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    // Create fragment based on position
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TodayTaskFragment();
            case 1:
                return new UpcomingFragment();
            case 2:
                return new CompletedFragment();
            default:
                return new TodayTaskFragment();
        }
    }

    @Override
    public int getItemCount() {

        return 3;
    }
}
