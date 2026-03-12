package com.example.myproject.Activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.Adapter.PropertyAdapter;
import com.example.myproject.R;

import com.example.myproject.model.Property;
import com.example.myproject.Database.DatabaseHelper; // your existing DB helper

import com.example.myproject.model.User;

import java.util.ArrayList;
import java.util.List;

public class AccountInfoActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvPhone;
    RecyclerView rvBookedProperties;
    PropertyAdapter propertyAdapter;
    List<Property> bookedProperties = new ArrayList<>();
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        rvBookedProperties = findViewById(R.id.rvBookedHouses); // keep same id as in layout

        db = new DatabaseHelper(this);

        // Get current user ID from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        // Load user info
        User user = db.getUserById(userId);
        if (user != null) {
            tvName.setText(user.getName());
            tvEmail.setText(user.getEmail());
            tvPhone.setText(user.getPhone());
        }

        // Load booked properties


        // Setup RecyclerView

    }

}