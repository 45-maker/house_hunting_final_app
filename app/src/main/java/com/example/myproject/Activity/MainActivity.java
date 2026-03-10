package com.example.myproject.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myproject.Fragment.FavouriteFragment;
import com.example.myproject.Fragment.HomeFragment;
import com.example.myproject.R;
import com.example.myproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. SESSION CHECK
        // Using "UserData" to match your RegisterActivity
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String savedEmail = prefs.getString("email", null);

        // If no email exists, redirect to LoginActivity immediately
        if (savedEmail == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish(); // Close MainActivity so they can't go back
            return;
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 2. LOAD DEFAULT FRAGMENT (HOME)
        loadFragment(new HomeFragment());

        // 3. BOTTOM MENU CLICK LOGIC
        binding.bottomMenu.setOnItemSelectedListener(item -> {
            Fragment fragment = null;

            int id = item.getItemId();

            if (id == R.id.home) { // Check these IDs match your menu/bottom_menu.xml
                fragment = new HomeFragment();
            } else if (id == R.id.favorite) {
                fragment = new FavouriteFragment();
            } else if (id == R.id.profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return false;
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .commit();
                return true;
            }
            return false;
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}