package com.mia.apa;

public class Food {
    String name;
    String cost;
    String category;

    public Food(String name, String cost, String category) {
        this.name = name;
        this.cost = cost;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCost() {
        return cost;
    }
    public String getCategory() {
        return category;
    }
}