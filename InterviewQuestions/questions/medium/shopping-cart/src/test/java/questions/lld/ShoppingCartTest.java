package questions.lld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.lld.ShoppingCart.*;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private Cart cart;
    private Product laptop, mouse, book;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        laptop = new Product("P1", "Laptop", "Electronics", 1000.00);
        mouse = new Product("P2", "Mouse", "Electronics", 25.00);
        book = new Product("P3", "Book", "Books", 50.00);
    }

    @Test
    void testAddItem() {
        cart.addItem(laptop, 1);
        assertEquals(1, cart.getItemCount());
        assertEquals(1000.00, cart.getSubtotal(), 0.01);
    }

    @Test
    void testAddSameItemTwice() {
        cart.addItem(mouse, 2);
        cart.addItem(mouse, 3);
        assertEquals(5, cart.getItemCount());
        assertEquals(125.00, cart.getSubtotal(), 0.01);
    }

    @Test
    void testRemoveItem() {
        cart.addItem(laptop, 1);
        cart.addItem(mouse, 2);
        cart.removeItem("P1");
        assertEquals(2, cart.getItemCount());
        assertNull(cart.getItem("P1"));
    }

    @Test
    void testUpdateQuantity() {
        cart.addItem(mouse, 2);
        cart.updateQuantity("P2", 5);
        assertEquals(5, cart.getItem("P2").getQuantity());
    }

    @Test
    void testUpdateQuantityToZeroRemoves() {
        cart.addItem(mouse, 2);
        cart.updateQuantity("P2", 0);
        assertTrue(cart.isEmpty());
    }

    @Test
    void testSubtotal() {
        cart.addItem(laptop, 1);  // 1000
        cart.addItem(mouse, 2);   // 50
        cart.addItem(book, 1);    // 50
        assertEquals(1100.00, cart.getSubtotal(), 0.01);
    }

    @Test
    void testPercentageDiscount() {
        cart.addItem(laptop, 1); // 1000
        cart.setDiscount(new PercentageDiscount(10));
        assertEquals(900.00, cart.getTotal(), 0.01);
        assertEquals(100.00, cart.getDiscountAmount(), 0.01);
    }

    @Test
    void testFlatDiscount() {
        cart.addItem(laptop, 1); // 1000
        cart.setDiscount(new FlatDiscount(150));
        assertEquals(850.00, cart.getTotal(), 0.01);
    }

    @Test
    void testFlatDiscountExceedsTotal() {
        cart.addItem(mouse, 1); // 25
        cart.setDiscount(new FlatDiscount(50));
        assertEquals(0.0, cart.getTotal(), 0.01);
    }

    @Test
    void testNoDiscount() {
        cart.addItem(laptop, 1);
        assertEquals(1000.00, cart.getTotal(), 0.01);
    }

    @Test
    void testClearCart() {
        cart.addItem(laptop, 1);
        cart.addItem(mouse, 2);
        cart.setDiscount(new PercentageDiscount(10));
        cart.clear();
        assertTrue(cart.isEmpty());
        assertEquals(0.0, cart.getSubtotal(), 0.01);
    }

    @Test
    void testCartSummary() {
        cart.addItem(laptop, 1);
        String summary = cart.getSummary();
        assertTrue(summary.contains("Laptop"));
        assertTrue(summary.contains("Total"));
    }

    @Test
    void testInvalidQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new CartItem(laptop, -1));
    }

    @Test
    void testInvalidPercentage() {
        assertThrows(IllegalArgumentException.class, () -> new PercentageDiscount(101));
    }

    @Test
    void testProductEquality() {
        Product dup = new Product("P1", "Different", "Cat", 0);
        assertEquals(laptop, dup);
    }
}
