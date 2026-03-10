package com.example.myproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button; // or TextView if using text clicks

import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject.R;

public class SettingsActivity extends AppCompatActivity {

    private Button btnAccountInfo, btnSecurity, btnNotifications, btnLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); // Make sure this layout exists

        // Initialize buttons (ensure IDs match your XML)
        btnAccountInfo = findViewById(R.id.btnAccountInfo);
        btnSecurity = findViewById(R.id.btnSecurity);
        btnNotifications = findViewById(R.id.btnNotifications);
        btnLanguage = findViewById(R.id.btnLanguage);

        // Set click listeners
        btnAccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, AccountInfoActivity.class));
            }
        });

        btnSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, SecurityActivity.class));
            }
        });

        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, Notifications.class));
            }
        });

        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, LanguageActivity.class));
            }
        });
    }
}