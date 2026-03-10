package com.example.myproject.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myproject.Adapter.ListItemsAdapter;
import com.example.myproject.Database.DatabaseHelper;
import com.example.myproject.Domain.PropertyDomain;
import com.example.myproject.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ListItemsAdapter adapterRecommended;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // It is safer to initialize UI data in onViewCreated
        initList();
    }

    private void initList() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        // 1. Force a check: If empty, insert and then RE-FETCH
        List<PropertyDomain> allItems = dbHelper.getAllProperties();
        if (allItems.isEmpty()) {
            dbHelper.insertSampleProperties();
            allItems = dbHelper.getAllProperties(); // Fetch again after inserting
        }

        // 2. Filter for Recommended
        List<PropertyDomain> recommendedItems = dbHelper.getPropertiesByType("recommended");
        if (!recommendedItems.isEmpty()) {
            binding.recommendedView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.recommendedView.setAdapter(new ListItemsAdapter(recommendedItems));
        }

        // 3. Filter for Nearby
        List<PropertyDomain> nearbyItems = dbHelper.getPropertiesByType("nearby");
        if (!nearbyItems.isEmpty()) {
            binding.nearbyView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            binding.nearbyView.setAdapter(new ListItemsAdapter(nearbyItems));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}