package com.example.myproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.R;
import com.example.myproject.model.Property;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {

    private List<Property> properties;
    private Context context;

    public PropertyAdapter(List<Property> properties, Context context) {
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
        Property property = properties.get(position);

        holder.title.setText(property.getTitle());
        holder.address.setText(property.getLocation());
        holder.price.setText(property.getPrice());
        holder.bed.setText(String.valueOf(property.getBeds()));
        holder.bath.setText(String.valueOf(property.getBaths()));
        holder.garage.setText(String.valueOf(property.getGarage()));
        holder.size.setText(property.getSize());
        holder.pic.setImageResource(property.getImage()); // drawable resource
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public static class PropertyViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView price, title, address, bed, bath, garage, size;

        public PropertyViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
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