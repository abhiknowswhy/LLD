package questions.lld.FoodDeliverySystem;

import java.util.*;

/**
 * Represents a customer who can place orders in the food delivery system.
 * Tracks the customer's name, address, and order history.
 */
public class Customer {
    private final String id;
    private final String name;
    private final String address;
    private final List<Order> orderHistory;

    public Customer(String id, String name, String address) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Customer id must not be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name must not be null or blank");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Customer address must not be null or blank");
        }
        this.id = id;
        this.name = name;
        this.address = address;
        this.orderHistory = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public List<Order> getOrderHistory() { return Collections.unmodifiableList(orderHistory); }

    /**
     * Adds a completed or in-progress order to this customer's history.
     */
    public void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order must not be null");
        }
        orderHistory.add(order);
    }

    @Override
    public String toString() {
        return String.format("Customer{%s, '%s', orders=%d}", id, name, orderHistory.size());
    }
}
