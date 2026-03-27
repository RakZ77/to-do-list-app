package kh.edu.rupp.to_dolistapp.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import kh.edu.rupp.to_dolistapp.R;
import kh.edu.rupp.to_dolistapp.adapters.MyViewPagerAdapter;

public class TaskListActivity extends AppCompatActivity {
    MaterialButton btnAdd;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        myViewPagerAdapter = new MyViewPagerAdapter(this);
        viewPager2.setAdapter(myViewPagerAdapter);

        // Set up TabLayout with ViewPager2 by showing selected tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Set up ViewPager2 to show selected tab
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        // Add Task Button to show AddTaskActivity
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

    }
}
