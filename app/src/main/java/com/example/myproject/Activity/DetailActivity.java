package com.example.myproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myproject.Domain.PropertyDomain;
import com.example.myproject.R;
import com.example.myproject.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private PropertyDomain object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        getBundles();
        setVariable();
    }

    private void getBundles() {
        object = (PropertyDomain) getIntent().getSerializableExtra("object");
    }

    private void setVariable() {
        if (object == null) return;

        // Back button
        binding.bacBtn.setOnClickListener(v -> finish());

        // FIX: Load the image directly using the Integer resource ID from our database
        int imageResource = object.getImage();

        if (imageResource != 0) {
            Glide.with(this)
                    .load(imageResource)
                    .placeholder(R.drawable.house_1) // Fallback while loading
                    .into(binding.picDetails);
        } else {
            binding.picDetails.setImageResource(R.drawable.house_4);
        }

        // Property details - Using 'location' instead of 'address' to match DatabaseHelper
        binding.TitleAddressTxt.setText(object.getTitle() + " in " + object.getLocation());
        binding.typeTxt.setText(object.getType());
        binding.descriptionTxt.setText(object.getDescription());
        binding.pricTxt.setText("Ksh " + object.getPrice());

        // Bed, Bath, Size
        binding.beTxt.setText(object.getBed() + " Bedroom");
        binding.bathTxt.setText(object.getBath() + " Bathroom");
        binding.sizeTxt.setText(object.getSize() + " m²");

        // Garage logic


        // Request Appointment Button
        binding.RequestBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, AppointmentActivity.class);
            intent.putExtra("property_name", object.getTitle());
            intent.putExtra("property_address", object.getLocation());
            startActivity(intent);
        });
    }
}