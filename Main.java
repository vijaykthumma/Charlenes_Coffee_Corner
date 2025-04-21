package com.coffee;

public class Main {

    public static void main(String[] args) {
        Order order = new Order();
        order.addItem(new OrderItem(Product.COFFEE_LARGE, 1));
        order.addItem(new OrderItem(Product.EXTRA_MILK, 1));
        order.addItem(new OrderItem(Product.BACON_ROLL, 1));
        order.addItem(new OrderItem(Product.ORANGE_JUICE, 1));
        order.addItem(new OrderItem(Product.COFFEE_SMALL, 1));
        order.addItem(new OrderItem(Product.SPECIAL_ROAST, 1));

        ReceiptGenerator generator = new ReceiptGenerator();
        String receipt = generator.generateReceipt(order);
        System.out.println(receipt);
    }
}
