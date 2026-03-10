package com.example.myproject.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject.Database.DatabaseHelper;
import com.example.myproject.R;

public class RegisterActivity extends AppCompatActivity {

    EditText firstName, lastName, phone, email, password;
    Button registerBtn;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);

        // Initialize Database Helper
        DB = new DatabaseHelper(this);

        registerBtn.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String phoneNo = phone.getText().toString().trim();
        String emailTxt = email.getText().toString().trim();
        String passTxt = password.getText().toString().trim();

        // Basic Validation
        if (TextUtils.isEmpty(fName) || TextUtils.isEmpty(emailTxt) || TextUtils.isEmpty(passTxt)) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Added Phone Length Validation (Common for Kenyan mobile apps)
        if (phoneNo.length() < 10) {
            phone.setError("Enter a valid phone number");
            return;
        }

        // --- SAVE TO SQLITE DATABASE ---
        // This makes the user "exist" for the LoginActivity to find
        boolean isInserted = DB.insertUser(emailTxt, passTxt);

        if (isInserted) {
            // Save additional details to SharedPreferences for profile display
            SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("firstName", fName);
            editor.putString("lastName", lName);
            editor.putString("phone", phoneNo);
            editor.putString("email", emailTxt);
            editor.apply();

            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_LONG).show();

            // Navigate to LoginActivity
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Registration Failed. Email might already exist.", Toast.LENGTH_SHORT).show();
        }
    }
}