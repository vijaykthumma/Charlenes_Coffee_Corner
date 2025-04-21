package com.coffee;

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

// Represents an item in an order, including the product and quantity.
class OrderItem {
    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
