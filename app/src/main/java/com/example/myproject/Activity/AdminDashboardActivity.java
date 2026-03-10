 package com.example.myproject.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject.Database.DatabaseHelper;
import com.example.myproject.R;

import java.util.ArrayList;

 public class AdminDashboardActivity extends AppCompatActivity {

     ListView listView;
     DatabaseHelper db;
     ArrayList<String> applicantList;
     ArrayAdapter<String> adapter;
     String selectedApplicantName = ""; // Store selection

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_admin_dashboard);

         db = new DatabaseHelper(this);
         listView = findViewById(R.id.applicantList);
         Button btnApprove = findViewById(R.id.btnApprove); // The button from your XML
         applicantList = new ArrayList<>();

         displayApplicants();

         // 1. Capture which item is clicked in the list
         listView.setOnItemClickListener((parent, view, position, id) -> {
             // Get the full string (e.g., "John - Business Trip")
             String fullText = applicantList.get(position);
             // Extract just the name (everything before the " - ")
             selectedApplicantName = fullText.split(" - ")[0];

             Toast.makeText(this, "Selected: " + selectedApplicantName, Toast.LENGTH_SHORT).show();
         });

         // 2. Handle the Approve Button
         btnApprove.setOnClickListener(v -> {
             if (selectedApplicantName.isEmpty()) {
                 Toast.makeText(this, "Please select an applicant first!", Toast.LENGTH_SHORT).show();
             } else {
                 boolean isUpdated = db.updateStatus(selectedApplicantName);
                 if (isUpdated) {
                     Toast.makeText(this, "Booking Approved!", Toast.LENGTH_SHORT).show();
                     // Refresh the list to show updated data
                     applicantList.clear();
                     displayApplicants();
                     selectedApplicantName = ""; // Reset selection
                 } else {
                     Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
                 }
             }
         });
     }

     private void displayApplicants() {
         applicantList.clear();
         SQLiteDatabase database = db.getReadableDatabase();

         // Filter: Only show those who are NOT approved yet
         Cursor cursor = database.rawQuery("SELECT * FROM applications WHERE status = 'Pending'", null);

         if (cursor.moveToFirst()) {
             do {
                 String name = cursor.getString(1);
                 String reason = cursor.getString(2);
                 applicantList.add(name + " - " + reason);
             } while (cursor.moveToNext());
         }
         cursor.close();

         adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, applicantList);
         listView.setAdapter(adapter);
     }
 }