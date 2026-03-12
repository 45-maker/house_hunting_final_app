package com.example.myproject.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myproject.Adapter.PropertyAdapter;
import com.example.myproject.Database.DatabaseHelper;
import com.example.myproject.Domain.PropertyDomain;
import com.example.myproject.R;
import com.example.myproject.model.Property;

import java.util.List;

public class FavouriteFragment extends Fragment {

    RecyclerView recyclerView;
    PropertyAdapter adapter;
    DatabaseHelper db;

    public FavouriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerView = view.findViewById(R.id.favouriteRecycler);

        db = new DatabaseHelper(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadFavourites();

        return view;
    }

    private void loadFavourites() {

        List<PropertyDomain> favouriteList = db.getFavouriteProperties();

        adapter = new PropertyAdapter(favouriteList, getContext());

        recyclerView.setAdapter(adapter);
    }
}