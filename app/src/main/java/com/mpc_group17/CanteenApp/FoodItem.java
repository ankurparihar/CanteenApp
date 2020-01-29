package com.mpc_group17.CanteenApp;

class FoodItem {

    private final String name;
    private final int price;
    private final String imageURL;
    private final String description;
    private int quantity;

    FoodItem(String name, String description, String imageURL, int price) {
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.description = description;
        this.quantity = 0;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return price;
    }
}
