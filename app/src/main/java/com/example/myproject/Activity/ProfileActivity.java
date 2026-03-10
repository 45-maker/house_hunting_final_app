package com.example.myproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myproject.R;
import com.example.myproject.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Back button
        binding.backBtn.setOnClickListener(v -> finish());

        // Logout button
        binding.logoutBtn.setOnClickListener(v -> logoutUser());

        // Settings menu clicks
        ConstraintLayout accountLayout = findViewById(R.id.accountLayout);
        accountLayout.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, AccountInfoActivity.class));
        });
        binding.securityLayout.setOnClickListener(v ->
                startActivity(new Intent(this, SecurityActivity.class)));

        binding.notificationsLayout.setOnClickListener(v ->
                startActivity(new Intent(this, Notifications.class)));

        binding.languageLayout.setOnClickListener(v ->
                startActivity(new Intent(this, LanguageActivity.class)));
    }

    private void logoutUser() {
        // IMPORTANT: Use "UserPrefs" (matching SignupActivity)
        SharedPreferences prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // This removes the saved email and password
        prefs.edit().clear().apply();

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to Login and clear the Activity stack so the user can't press 'Back' to return to Profile
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}