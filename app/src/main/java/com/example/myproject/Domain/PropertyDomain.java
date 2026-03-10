package com.example.myproject.Domain;

import java.io.Serializable;

public class PropertyDomain implements Serializable {

    private int id;
    private String title;
    private String location;
    private String price;
    private String description;
    private int image;    // The R.drawable integer
    private String type;

    // Optional extra fields
    private int bed;
    private int bath;
    private int size;
    private String pickPath; // Used if you still use String-based loading

    private boolean isGarage;
    private int address;

    private boolean favorite;

    // 1. Constructor used by DatabaseHelper.insertSampleProperties
    public PropertyDomain(String title, String location, String price, String description, int image, String type) {
        this.title = title;
        this.location = location;
        this.price = price;
        this.description = description;
        this.image = image;
        this.pickPath = pickPath;
        this.type = type;
        this.favorite=favorite;

        // Default values for extra fields to avoid nulls
        this.bed = 2;
        this.bath = 1;
        this.size = 80;
    }

    // 2. Empty Constructor (REQUIRED for SQLite loops)
    public PropertyDomain() {
    }

    // --- GETTERS AND SETTERS ---
    // Ensure these match your PropertyAdapter and DatabaseHelper exactly

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public int getBath() {
        return bath;
    }

    public void setBath(int bath) {
        this.bath = bath;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public String getPickPath() {
        return pickPath;
    }

    public void setPickPath(String pickPath) {
        this.pickPath = pickPath;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }


    public boolean getisFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {this.favorite = favorite;
    }
}
