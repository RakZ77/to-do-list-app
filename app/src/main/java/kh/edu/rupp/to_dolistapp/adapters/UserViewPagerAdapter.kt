package kh.edu.rupp.to_dolistapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import kh.edu.rupp.to_dolistapp.views.AllFragment;
import kh.edu.rupp.to_dolistapp.views.CachedFragment;
import kh.edu.rupp.to_dolistapp.views.LocalFragment;

public class UserViewPagerAdapter extends FragmentStateAdapter {
    public UserViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AllFragment();
            case 1:
                return new CachedFragment();
            case 2:
                return new LocalFragment();
            default:
                return new AllFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
