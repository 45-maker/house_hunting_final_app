package com.example.myproject.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myproject.Adapter.ListItemsAdapter;
import com.example.myproject.Database.DatabaseHelper;
import com.example.myproject.Domain.PropertyDomain;
import com.example.myproject.databinding.FragmentFavouriteBinding;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    FragmentFavouriteBinding binding;
    ListItemsAdapter adapter;

    public FavouriteFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFavouriteBinding.inflate(inflater, container, false);

        loadFavouriteHouses();

        return binding.getRoot();
    }

    private void loadFavouriteHouses(){

        DatabaseHelper db = new DatabaseHelper(getContext());

        ArrayList<PropertyDomain> items = db.getFavoriteProperties();

        binding.favouriteRecycler.setLayoutManager(
                new LinearLayoutManager(getContext())
        );

        adapter = new ListItemsAdapter(items);
        binding.favouriteRecycler.setAdapter(adapter);
    }
}