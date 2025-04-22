package com.charlenescoffee.model;

public enum Product {
    COFFEE_SMALL("Coffee (small)", 2.50),
    COFFEE_MEDIUM("Coffee (medium)", 3.00),
    COFFEE_LARGE("Coffee (large)", 3.50),
    BACON_ROLL("Bacon Roll", 4.50),
    ORANGE_JUICE("Freshly squeezed orange juice (0.25l)", 3.95),
    EXTRA_MILK("Extra milk", 0.30),
    FOAMED_MILK("Foamed milk", 0.50),
    SPECIAL_ROAST("Special roast coffee", 0.90);

    private final String name;
    private final double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

     //Added toString for better representation of products
    @Override
    public String toString() {
        return name + " - " + price + " CHF";
    }

    // Static method to get a product by its name, useful for testing and order processing.
    public static Product getProductByName(String name) {
        for (Product product : Product.values()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null; 
    }
}
