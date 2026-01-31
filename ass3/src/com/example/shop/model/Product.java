package com.example.shop.model;

public class Product {
    private int id;
    private String name;
    private int cost;

    public Product() {}

    public Product(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
