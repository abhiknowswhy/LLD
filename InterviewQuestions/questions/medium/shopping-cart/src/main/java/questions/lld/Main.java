package questions.lld;

import questions.lld.ShoppingCart.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Shopping Cart Demo ===\n");

        Product laptop = new Product("P1", "Laptop", "Electronics", 999.99);
        Product mouse = new Product("P2", "Mouse", "Electronics", 29.99);
        Product book = new Product("P3", "Clean Code", "Books", 39.99);

        Cart cart = new Cart();
        cart.addItem(laptop, 1);
        cart.addItem(mouse, 2);
        cart.addItem(book, 1);

        System.out.println(cart.getSummary());

        System.out.println("\n--- Applying 10% discount ---\n");
        cart.setDiscount(new PercentageDiscount(10));
        System.out.println(cart.getSummary());

        System.out.println("\n--- Changing to $50 flat discount ---\n");
        cart.setDiscount(new FlatDiscount(50));
        System.out.println(cart.getSummary());
    }
}