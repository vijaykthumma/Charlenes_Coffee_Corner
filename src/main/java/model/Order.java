package com.charlenescoffee.model;

import java.util.*;


public class Order {


    private final List<OrderItem> orderItems;
    private int beverageCount; // Keep track of beverages for the bonus program.

    public Order() {
        this.orderItems = new ArrayList<>();
        this.beverageCount = 0;
    }

    public void addItem(OrderItem item) {
        this.orderItems.add(item);
        if (item.getProduct().name().startsWith("COFFEE") || item.getProduct() == Product.ORANGE_JUICE) {
            this.beverageCount++;
        }
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public int getBeverageCount() {
        return beverageCount;
    }
}

