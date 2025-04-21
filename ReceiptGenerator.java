package com.coffee;

import java.util.*;


public class ReceiptGenerator {



    // Method to generate the receipt string
    public String generateReceipt(Order order) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("\nCharlene's Coffee Corner\n");
        receipt.append("--------------------------------\n");

        List<OrderItem> orderItems = order.getOrderItems();
        double total = 0;
        // Map to store item counts for display
        Map<Product, Integer> itemCounts = new HashMap<>();

        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            int quantity = orderItem.getQuantity();
            double itemTotal = orderItem.getTotalPrice();

            // Update item counts
            itemCounts.put(product, itemCounts.getOrDefault(product, 0) + quantity);
            total += itemTotal;
        }

        // Iterate through unique items and their counts
        for (Map.Entry<Product, Integer> entry : itemCounts.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = product.getPrice() * quantity;
            receipt.append(String.format("%-40s %d x %.2f CHF = %.2f CHF\n", product.getName(), quantity, product.getPrice(), itemTotal));
        }

        // Apply bonus
        double discount = calculateBonus(order);
        if (discount > 0) {
            receipt.append(String.format("%-40s %.2f CHF\n", "Discount", -discount));
            total -= discount;
        }

        receipt.append("--------------------------------\n");
        receipt.append(String.format("%-40s %.2f CHF\n", "Total", total));

        return receipt.toString();
    }

    // 4. Bonus Calculation
    // Calculates the bonus amount for an order.
    private double calculateBonus(Order order) {
        double discount = 0;
        int beverageCount = order.getBeverageCount();
        List<OrderItem> orderItems = order.getOrderItems();
        boolean hasSnack = false;
        boolean hasBeverage = false;
        List<Product> freeExtras = new ArrayList<>();

       for (OrderItem item : orderItems) {
            if (item.getProduct().getName().contains("Coffee") || item.getProduct() == Product.ORANGE_JUICE) {
                hasBeverage = true;
            } else if (item.getProduct() == Product.BACON_ROLL) {
                hasSnack = true;
            }
        }
        // Every 5th beverage is free
        if (beverageCount >= 5) {
            discount += getCheapestBeveragePrice(order);
        }

       if (hasBeverage && hasSnack)
       {
           discount += getCheapestExtra(order);
       }
        return discount;
    }

    // Helper method to find the price of the cheapest beverage in an order
    private double getCheapestBeveragePrice(Order order) {
        double minPrice = Double.MAX_VALUE;
        for (OrderItem item : order.getOrderItems()) {
             if (item.getProduct().getName().contains("Coffee") || item.getProduct() == Product.ORANGE_JUICE) {
                if (item.getProduct().getPrice() < minPrice) {
                    minPrice = item.getProduct().getPrice();
                }
             }
        }
        return (minPrice == Double.MAX_VALUE) ? 0 : minPrice;
     }

    private double getCheapestExtra(Order order)
    {
        double minPrice = Double.MAX_VALUE;
         for (OrderItem item : order.getOrderItems()) {
             if (item.getProduct() == Product.EXTRA_MILK || item.getProduct() == Product.FOAMED_MILK || item.getProduct() == Product.SPECIAL_ROAST) {
                if (item.getProduct().getPrice() < minPrice) {
                    minPrice = item.getProduct().getPrice();
                }
             }
        }
        return (minPrice == Double.MAX_VALUE) ? 0 : minPrice;
   }
}
