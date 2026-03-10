package kh.edu.rupp.to_dolistapp;

import static kh.edu.rupp.to_dolistapp.R.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        BottomNavigationView navView = findViewById(id.nav_view);
        navView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.add_task){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AddTaskFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            }
            return false;
        });

    }
}
