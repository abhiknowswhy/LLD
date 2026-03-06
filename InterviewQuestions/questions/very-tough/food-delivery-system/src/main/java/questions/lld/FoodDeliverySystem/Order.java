package questions.lld.FoodDeliverySystem;

import java.util.*;

/**
 * Represents an order placed by a {@link Customer} at a {@link Restaurant}.
 * Contains line items, tracks lifecycle status, and optionally holds
 * the assigned {@link DeliveryAgent}.
 */
public class Order {
    private final String id;
    private final Customer customer;
    private final Restaurant restaurant;
    private final List<OrderItem> items;
    private OrderStatus status;
    private DeliveryAgent deliveryAgent;

    public Order(String id, Customer customer, Restaurant restaurant, List<OrderItem> items) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Order id must not be null or blank");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Order customer must not be null");
        }
        if (restaurant == null) {
            throw new IllegalArgumentException("Order restaurant must not be null");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.PLACED;
        this.deliveryAgent = null;
    }

    public String getId() { return id; }
    public Customer getCustomer() { return customer; }
    public Restaurant getRestaurant() { return restaurant; }
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
    public OrderStatus getStatus() { return status; }
    public DeliveryAgent getDeliveryAgent() { return deliveryAgent; }

    /**
     * Updates the order status. Prevents transitions from terminal states
     * ({@code DELIVERED} or {@code CANCELLED}).
     *
     * @throws IllegalStateException if the order is already in a terminal state
     */
    public void setStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("OrderStatus must not be null");
        }
        if (this.status == OrderStatus.DELIVERED || this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Cannot change status of a " + this.status + " order");
        }
        this.status = status;
    }

    /**
     * Assigns a delivery agent to this order and marks the agent as {@code BUSY}.
     *
     * @throws IllegalArgumentException if agent is null
     * @throws IllegalStateException    if an agent is already assigned
     */
    public void assignAgent(DeliveryAgent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("DeliveryAgent must not be null");
        }
        if (this.deliveryAgent != null) {
            throw new IllegalStateException("Order already has an assigned agent: "
                    + this.deliveryAgent.getName());
        }
        this.deliveryAgent = agent;
        agent.setStatus(AgentStatus.BUSY);
    }

    /**
     * Calculates the total price by summing all item subtotals.
     */
    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(OrderItem::subtotal)
                .sum();
    }

    @Override
    public String toString() {
        return String.format("Order{%s, customer='%s', restaurant='%s', items=%d, total=$%.2f, status=%s, agent=%s}",
                id, customer.getName(), restaurant.getName(),
                items.size(), getTotalPrice(), status,
                deliveryAgent == null ? "unassigned" : deliveryAgent.getName());
    }
}
