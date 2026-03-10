package com.example.myproject.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject.Database.DatabaseHelper;
import com.example.myproject.R;


public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginBtn;

    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);

        DB = new DatabaseHelper(this);

        loginBtn.setOnClickListener(v ->
                loginUser());
    }

    private void loginUser() {
        String emailTxt = email.getText().toString().trim();
        String passTxt = password.getText().toString().trim();

        if (emailTxt.isEmpty() || passTxt.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (DB.checkUser(emailTxt, passTxt)) {
                // Save session correctly
                SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email", emailTxt);
                editor.apply();

                // Navigate to Main
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // This will tell you if the Database is the problem
            android.util.Log.e("LOGIN_ERROR", "DB Error: " + e.getMessage());
            Toast.makeText(this, "Database Error!", Toast.LENGTH_SHORT).show();
        }
    }
    }
