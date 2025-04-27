package com.bookshop.models;

import java.sql.Timestamp;

public class Order {
    private int id;
    private int userId;
    private Timestamp orderDate;
    private double total;

    // Constructor without ID (for creating new order)
    public Order(int userId, Timestamp orderDate, double total) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.total = total;
    }

    // Constructor with ID (for loading existing orders)
    public Order(int id, int userId, Timestamp orderDate, double total) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.total = total;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

