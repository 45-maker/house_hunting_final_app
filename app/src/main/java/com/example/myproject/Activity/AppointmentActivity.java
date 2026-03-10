package com.example.myproject.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myproject.R;
import java.util.Calendar;

public class AppointmentActivity extends AppCompatActivity {

    EditText nameTxt, phoneTxt, dateTxt, timeTxt;
    Button sendBtn;
    ImageView backBtn;
    TextView propertyTitleHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Initialize Views
        nameTxt = findViewById(R.id.nameTxt);
        phoneTxt = findViewById(R.id.phoneTxt);
        dateTxt = findViewById(R.id.dateTxt);
        timeTxt = findViewById(R.id.timeTxt);
        sendBtn = findViewById(R.id.sendBtn);
        backBtn = findViewById(R.id.backBtn);
        propertyTitleHeader = findViewById(R.id.propertyTitleHeader);

        // Set Title from Intent
        String houseTitle = getIntent().getStringExtra("propertyTitle");
        if (houseTitle != null) {
            propertyTitleHeader.setText(houseTitle);
        }

        // Back Button logic
        backBtn.setOnClickListener(v -> finish());

        // FRIENDLY FEATURE: Date Picker
        dateTxt.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) ->
                    dateTxt.setText(day + "/" + (month + 1) + "/" + year),
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // FRIENDLY FEATURE: Time Picker
        timeTxt.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, hour, minute) ->
                    timeTxt.setText(String.format("%02d:%02d", hour, minute)),
                    c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
        });

        // Submit Logic
        sendBtn.setOnClickListener(view -> {
            if(validateFields()){
                // Show the fancy success dialog instead of a Toast
                showSuccessDialog(houseTitle);
            }
        });
    }

    private void showSuccessDialog(String houseName) {
        // Create the dialog
        android.app.Dialog dialog = new android.app.Dialog(this);
        dialog.setContentView(R.layout.dialog_success);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Set the message
        TextView msg = dialog.findViewById(R.id.successMsg);
        msg.setText("Your viewing for the house is booked!");

        // Button logic
        Button doneBtn = dialog.findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(v -> {
            dialog.dismiss();
            finish(); // Close the activity and go back
        });

        dialog.show();
    }

    private boolean validateFields() {
        if(nameTxt.getText().toString().isEmpty() ||
                phoneTxt.getText().toString().isEmpty() ||
                dateTxt.getText().toString().isEmpty() ||
                timeTxt.getText().toString().isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}