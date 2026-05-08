package kh.edu.rupp.to_dolistapp.views;

import static kh.edu.rupp.to_dolistapp.R.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kh.edu.rupp.to_dolistapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        // Switching between fragments in navigation bar
        BottomNavigationView navView = findViewById(id.nav_view);
        navView.setOnItemSelectedListener(item -> {

            Fragment fragment;

            if (item.getItemId() == id.navigation_home){
                fragment = new HomeFragment();
            } else if (item.getItemId() == id.add_task){
                fragment = new TaskListFragment();
            } else if (item.getItemId() == id.navigation_user_list){
                fragment = new UserListFragment();
            } else {
            return false;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        });
    }
}
