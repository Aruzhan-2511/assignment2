package com.example.shop.dao;

import com.example.shop.db.DatabaseConnection;

import java.sql.*;
import java.util.Arrays;

public class OrderDAO {

    public void addOrder(int[] productIds) {
        String sql = "INSERT INTO orders(product_ids) VALUES (?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Array array = conn.createArrayOf(
                    "INTEGER",
                    Arrays.stream(productIds).boxed().toArray()
            );
            ps.setArray(1, array);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
