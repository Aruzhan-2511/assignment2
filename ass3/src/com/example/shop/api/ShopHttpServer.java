package com.example.shop.api;

import com.example.shop.dao.OrderDAO;
import com.example.shop.dao.ProductDAO;
import com.example.shop.model.Product;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class
ShopHttpServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/products", ShopHttpServer::handleProducts);
        server.createContext("/orders", ShopHttpServer::handleOrders);

        server.setExecutor(null);
        server.start();

        System.out.println("REST API started on http://localhost:8080");
    }

    // ---------- PRODUCTS ----------
    private static void handleProducts(HttpExchange exchange) throws IOException {
        ProductDAO productDAO = new ProductDAO();

        if ("GET".equals(exchange.getRequestMethod())) {
            List<Product> products = productDAO.getAllProducts();

            StringBuilder json = new StringBuilder();
            json.append("[");

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                json.append("{")
                        .append("\"id\":").append(p.getId()).append(",")
                        .append("\"name\":\"").append(p.getName()).append("\",")
                        .append("\"cost\":").append(p.getCost())
                        .append("}");
                if (i < products.size() - 1) json.append(",");
            }

            json.append("]");

            sendResponse(exchange, 200, json.toString());
        } else if ("POST".equals(exchange.getRequestMethod())) {
            String body = readRequestBody(exchange);

            String name = extractJsonValue(body, "name");
            int cost = Integer.parseInt(extractJsonValue(body, "cost"));

            productDAO.addProduct(new Product(name, cost));

            sendResponse(exchange, 201, "{\"status\":\"product added\"}");
        } else {
            sendResponse(exchange, 405, "{\"error\":\"Method Not Allowed\"}");
        }
    }

    // ---------- ORDERS ----------
    private static void handleOrders(HttpExchange exchange) throws IOException {
        OrderDAO orderDAO = new OrderDAO();

        if ("POST".equals(exchange.getRequestMethod())) {
            String body = readRequestBody(exchange);

            String idsPart = body.substring(body.indexOf("[") + 1, body.indexOf("]"));
            String[] parts = idsPart.trim().isEmpty() ? new String[0] : idsPart.split(",");

            int[] ids = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                ids[i] = Integer.parseInt(parts[i].trim());
            }

            orderDAO.addOrder(ids);

            sendResponse(exchange, 201, "{\"status\":\"order added\"}");
        } else {
            sendResponse(exchange, 405, "{\"error\":\"Method Not Allowed\"}");
        }
    }

    // ---------- UTILS ----------
    private static String readRequestBody(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private static void sendResponse(HttpExchange exchange, int code, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(code, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    // Очень примитивный JSON парсер
    private static String extractJsonValue(String json, String key) {
        String search = "\"" + key + "\":";
        int start = json.indexOf(search) + search.length();

        if (json.charAt(start) == '"') {
            start++;
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } else {
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            return json.substring(start, end).trim();
        }
    }
}
