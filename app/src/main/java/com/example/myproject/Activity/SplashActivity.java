package com.example.myproject.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {

            SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);

            // Check if user email exists
            if (prefs.getString("user_email", null) != null) {

                // User already logged in
                startActivity(new Intent(SplashActivity.this, MainActivity.class));

            } else {

                // User not logged in
                startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
            }

            finish();

        }, 3000); // 2 seconds splash
    }
}