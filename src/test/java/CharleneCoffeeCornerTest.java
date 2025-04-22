package test.java.com.charlenescoffee.service;

import org.junit.jupiter.api.Test;

import com.charlenescoffee.model.Order;
import com.charlenescoffee.model.OrderItem;
import com.charlenescoffee.model.Product;

import main.java.service.ReceiptGenerator;

import static org.junit.jupiter.api.Assertions.*;

// JUnit tests for Charlene's Coffee Corner.
class CharleneCoffeeCornerTest {

    @Test
    void testProductPrices() {
        assertEquals(2.50, Product.COFFEE_SMALL.getPrice(), 0.001);
        assertEquals(4.50, Product.BACON_ROLL.getPrice(), 0.001);
        assertEquals(3.95, Product.ORANGE_JUICE.getPrice(), 0.001);
        assertEquals(0.30, Product.EXTRA_MILK.getPrice(), 0.001);
        assertEquals(0.50, Product.FOAMED_MILK.getPrice(), 0.001);
        assertEquals(0.90, Product.SPECIAL_ROAST.getPrice(), 0.001);
    }

    @Test
    void testOrderTotal() {
        Order order = new Order();
        order.addItem(new OrderItem(Product.COFFEE_SMALL, 2));
        order.addItem(new OrderItem(Product.BACON_ROLL, 1));
        double total = 0;
        for (OrderItem item : order.getOrderItems()) {
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

    // New Test Cases
    @Test
    void testBonusCalculation_5thBeverageFree_MultipleOrders() {
        Order order1 = new Order();
        for (int i = 0; i < 5; i++) {
            order1.addItem(new OrderItem(Product.COFFEE_MEDIUM, 1));
        }
        ReceiptGenerator generator = new ReceiptGenerator();
        String receipt1 = generator.generateReceipt(order1);
        assertTrue(receipt1.contains(String.format("%.2f", -Product.COFFEE_MEDIUM.getPrice())));

        Order order2 = new Order();
        for (int i = 0; i < 6; i++) {
            order2.addItem(new OrderItem(Product.COFFEE_LARGE, 1));
        }
        String receipt2 = generator.generateReceipt(order2);
        assertTrue(receipt2.contains(String.format("%.2f", -Product.COFFEE_LARGE.getPrice())));
    }

    @Test
    void testBonusCalculation_BeverageAndSnack_MultipleExtras() {
        Order order = new Order();
        order.addItem(new OrderItem(Product.COFFEE_SMALL, 1));
        order.addItem(new OrderItem(Product.BACON_ROLL, 1));
        order.addItem(new OrderItem(Product.EXTRA_MILK, 1));
        order.addItem(new OrderItem(Product.FOAMED_MILK, 1));
        ReceiptGenerator generator = new ReceiptGenerator();
        String receipt = generator.generateReceipt(order);
        // Should only apply the discount for the cheapest extra
        assertTrue(receipt.contains(String.format("%.2f", -Product.EXTRA_MILK.getPrice())));
    }

    @Test
    void testBonusCalculation_BeverageAndSnack_NoExtras() {
        Order order = new Order();
        order.addItem(new OrderItem(Product.COFFEE_SMALL, 1));
        order.addItem(new OrderItem(Product.BACON_ROLL, 1));
        ReceiptGenerator generator = new ReceiptGenerator();
        String receipt = generator.generateReceipt(order);
        assertFalse(receipt.contains("Discount"));
    }

    @Test
    void testBonusCalculation_OnlyBeverages() {
        Order order = new Order();
        order.addItem(new OrderItem(Product.COFFEE_SMALL, 1));
        order.addItem(new OrderItem(Product.COFFEE_LARGE, 1));
        ReceiptGenerator generator = new ReceiptGenerator();
        String receipt = generator.generateReceipt(order);
        assertFalse(receipt.contains("Discount"));
    }

    @Test
    void testBonusCalculation_OnlySnacks() {
        Order order = new Order();
        order.addItem(new OrderItem(Product.BACON_ROLL, 2));
        ReceiptGenerator generator = new ReceiptGenerator();
        String receipt = generator.generateReceipt(order);
        assertFalse(receipt.contains("Discount"));
    }

     @Test
    void testBonusCalculation_FreeExtra_SpecialRoast() {
        Order order = new Order();
        order.addItem(new OrderItem(Product.COFFEE_SMALL, 1));
        order.addItem(new OrderItem(Product.BACON_ROLL, 1));
        order.addItem(new OrderItem(Product.SPECIAL_ROAST, 1));
        ReceiptGenerator generator = new ReceiptGenerator();
        String receipt = generator.generateReceipt(order);
        assertTrue(receipt.contains(String.format("%.2f", -Product.SPECIAL_ROAST.getPrice())));
    }
}

