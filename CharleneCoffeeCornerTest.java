package com.coffee;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CharleneCoffeeCornerTest {

	


    @Test
    void testProductPrices() {
        assertEquals(2.50, Product.COFFEE_SMALL.getPrice(), 0.001);
        assertEquals(4.50, Product.BACON_ROLL.getPrice(), 0.001);
        assertEquals(3.95, Product.ORANGE_JUICE.getPrice(), 0.001);
        assertEquals(0.30, Product.EXTRA_MILK.getPrice(), 0.001);
    }

    @Test
    void testOrderTotal() {
        Order order = new Order();
        order.addItem(new OrderItem(Product.COFFEE_SMALL, 2));
        order.addItem(new OrderItem(Product.BACON_ROLL, 1));
        double total = 0;
        for(OrderItem item : order.getOrderItems()){
            total += item.getTotalPrice();
        }
        assertEquals(2 * 2.50 + 4.50, total, 0.001);
    }

    @Test
    void testReceiptGeneration() {
        Order order = new Order();
        order.addItem(new OrderItem(Product.COFFEE_LARGE, 1));
        order.addItem(new OrderItem(Product.EXTRA_MILK, 1));
        order.addItem(new OrderItem(Product.BACON_ROLL, 1));
        ReceiptGenerator generator = new ReceiptGenerator();
        String receipt = generator.generateReceipt(order);
        assertTrue(receipt.contains("Coffee (large)"));
        assertTrue(receipt.contains("Bacon Roll"));
        assertTrue(receipt.contains("Extra milk"));
        assertTrue(receipt.contains("Total"));
        assertTrue(receipt.contains(String.format("%.2f", Product.COFFEE_LARGE.getPrice())));
        assertTrue(receipt.contains(String.format("%.2f", Product.BACON_ROLL.getPrice())));
        assertTrue(receipt.contains(String.format("%.2f", Product.EXTRA_MILK.getPrice())));
    }

    @Test
    void testBonusCalculation_5thBeverageFree() {
        Order order = new Order();
        for (int i = 0; i < 5; i++) {
            order.addItem(new OrderItem(Product.COFFEE_SMALL, 1)); // Add 5 beverages
        }
        ReceiptGenerator generator = new ReceiptGenerator();
        String receipt = generator.generateReceipt(order);
        // Check if the discount is applied.  The discount should be the price of the small coffee
        assertTrue(receipt.contains(String.format("%.2f", -Product.COFFEE_SMALL.getPrice())));
    }

     @Test
    void testBonusCalculation_BeverageAndSnack_FreeExtra() {
        Order order = new Order();
        order.addItem(new OrderItem(Product.COFFEE_SMALL, 1));
        order.addItem(new OrderItem(Product.BACON_ROLL, 1));
        order.addItem(new OrderItem(Product.EXTRA_MILK, 1));  //add an extra
        ReceiptGenerator generator = new ReceiptGenerator();
        String receipt = generator.generateReceipt(order);
        assertTrue(receipt.contains(String.format("%.2f", -Product.EXTRA_MILK.getPrice())));
    }

    @Test
    void testBonusCalculation_NoBonus() {
        Order order = new Order();
        order.addItem(new OrderItem(Product.COFFEE_SMALL, 1));
        order.addItem(new OrderItem(Product.BACON_ROLL, 1));
        ReceiptGenerator generator = new ReceiptGenerator();
        String receipt = generator.generateReceipt(order);
        assertFalse(receipt.contains("Discount"));
    }

    @Test
    void testGetProductByName() {
        assertEquals(Product.COFFEE_SMALL, Product.getProductByName("Coffee (small)"));
        assertEquals(Product.BACON_ROLL, Product.getProductByName("Bacon Roll"));
        assertEquals(Product.ORANGE_JUICE, Product.getProductByName("Freshly squeezed orange juice (0.25l)"));
        assertNull(Product.getProductByName("Nonexistent Product"));
    }
}
