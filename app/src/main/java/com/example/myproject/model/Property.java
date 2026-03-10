package com.example.myproject.model;

public class Property {

    private int id;
    private String title;
    private String location;
    private String price;
    private String description;

    // New fields for viewholder
    private int beds;
    private int baths;
    private int garage;
    private String size;
    private int image; // drawable resource

    public Property(int id, String title, String location, String price, String description,
                    int beds, int baths, int garage, String size, int image) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.price = price;
        this.description = description;
        this.beds = beds;
        this.baths = baths;
        this.garage = garage;
        this.size = size;
        this.image = image;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public String getPrice() { return price; }
    public String getDescription() { return description; }
    public int getBeds() { return beds; }
    public int getBaths() { return baths; }
    public int getGarage() { return garage; }
    public String getSize() { return size; }
    public int getImage() { return image; }

    // Setters (optional if you need to update fields later)
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setLocation(String location) { this.location = location; }
    public void setPrice(String price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setBeds(int beds) { this.beds = beds; }
    public void setBaths(int baths) { this.baths = baths; }
    public void setGarage(int garage) { this.garage = garage; }
    public void setSize(String size) { this.size = size; }
    public void setImage(int image) { this.image = image; }
}