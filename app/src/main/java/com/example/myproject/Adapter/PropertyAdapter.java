package com.example.myproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.Activity.AccountInfoActivity;
import com.example.myproject.Database.DatabaseHelper;
import com.example.myproject.Domain.PropertyDomain;
import com.example.myproject.R;
import com.example.myproject.model.Property;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {

    private List<PropertyDomain> properties;
    private Context context;

    public PropertyAdapter(List<PropertyDomain> properties, Context context) {
        this.properties = properties;
        this.context = context;
    }




    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_item, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        PropertyDomain property = properties.get(position);

        holder.title.setText(property.getTitle());
        holder.address.setText(property.getLocation());

        // Price: convert to string safely
        holder.price.setText(String.valueOf(property.getPrice()));

        // Beds, Baths, Garage: convert int to string
        holder.bed.setText(String.valueOf(property.getBed()));
        holder.bath.setText(String.valueOf(property.getBath()));
        holder.garage.setText(String.valueOf(property.getGarage()));

        // Size: convert to string if not already
        holder.size.setText(String.valueOf(property.getSize()));

        // Image
        holder.pic.setImageResource(property.getImage());

        // Favorite button
        holder.favBtn.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(context);
            db.addToFavourite(property.getId());
            holder.favBtn.setImageResource(R.drawable.ic_favorite);
            Toast.makeText(context,"Added to favourites",Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public static class PropertyViewHolder extends RecyclerView.ViewHolder {
        ImageView pic,favBtn;
        TextView price, title, address, bed, bath, garage, size;

        public PropertyViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
            favBtn = itemView.findViewById(R.id.favBtn);
            price = itemView.findViewById(R.id.priceTxt);
            title = itemView.findViewById(R.id.titleTxt);
            address = itemView.findViewById(R.id.addressTxt);
            bed = itemView.findViewById(R.id.bedTxt);
            bath = itemView.findViewById(R.id.bathTxt);
            garage = itemView.findViewById(R.id.garageTxt);
            size = itemView.findViewById(R.id.sizeTxt);
        }
    }
}