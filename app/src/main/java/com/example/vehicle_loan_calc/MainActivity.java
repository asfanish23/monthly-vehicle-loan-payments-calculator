package com.example.vehicle_loan_calc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);

        // Load Home sebagai default
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new HomeFragment())
                .commit();

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selected = null;

            if (item.getItemId() == R.id.menu_home) {
                selected = new HomeFragment();
            } else if (item.getItemId() == R.id.menu_about) {
                selected = new AboutFragment();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, selected)
                    .commit();

            return true;
        });
    }
}
