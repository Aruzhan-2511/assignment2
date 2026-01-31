package com.example.shop.model;

import java.util.List;
import java.util.ArrayList;
import com.example.shop.model.Order;

public class Shopper {
    private String name;
    private List<Order> orders;

    public Shopper(String name) {
        this.name = name;
        this.orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public String toString() {
        return "Shopper: " + name + ", Orders count: " + orders.size();
    }
}
