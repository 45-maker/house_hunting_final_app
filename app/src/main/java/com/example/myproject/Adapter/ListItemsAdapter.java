package com.example.myproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject.Activity.DetailActivity;
import com.example.myproject.Domain.PropertyDomain;
import com.example.myproject.R;
import com.example.myproject.databinding.ViewholderItemBinding;

import java.util.List;

public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsAdapter.Viewholder> {

    // Use List<PropertyDomain> for everything
    private List<PropertyDomain> items;
    private Context context;

    // REMOVED: The second constructor that was causing the "Same Erasure" error
    public ListItemsAdapter(List<PropertyDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ListItemsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        // Use a local binding variable here
        ViewholderItemBinding binding = ViewholderItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new Viewholder(binding);
    }

    @Override

    public void onBindViewHolder(@NonNull ListItemsAdapter.Viewholder holder, int position) {
        PropertyDomain currentItem = items.get(position);

        // Safe text assignment
        holder.binding.titleTxt.setText(currentItem.getTitle() != null ? currentItem.getTitle() : "No Title");
        holder.binding.priceTxt.setText("Ksh " + currentItem.getPrice());
        holder.binding.sizeTxt.setText(currentItem.getSize() + " m2");
        holder.binding.addressTxt.setText(String.valueOf(currentItem.getAddress()));
        holder.binding.bedTxt.setText(currentItem.getBed() + " Bed");
        holder.binding.bathTxt.setText(String.valueOf(currentItem.getBath()) + " Bath");

        // --- Safe Image Loading ---
        // Inside onBindViewHolder in ListItemsAdapter.java
        int imageResourceId = currentItem.getImage(); // This should be an int from your Domain class

        if (imageResourceId != 0) {
            Glide.with(context)
                    .load(imageResourceId) // Glide handles Int resource IDs perfectly
                    .into(holder.binding.pic);
        } else {
            holder.binding.pic.setImageResource(R.drawable.ic_home);
        }

        // Click listener
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", currentItem);
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        // Keep binding inside the ViewHolder
        private final ViewholderItemBinding binding;

        public Viewholder(ViewholderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
