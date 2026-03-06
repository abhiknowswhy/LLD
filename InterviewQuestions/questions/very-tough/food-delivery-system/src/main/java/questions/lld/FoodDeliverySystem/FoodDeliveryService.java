package questions.lld.FoodDeliverySystem;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton orchestrator for the food delivery system.
 * Manages restaurants, customers, delivery agents, and orders.
 * Provides operations to place orders, assign agents, update statuses,
 * track orders, and rate restaurants.
 */
public class FoodDeliveryService {

    private static FoodDeliveryService instance;

    private final Map<String, Restaurant> restaurants;
    private final Map<String, Customer> customers;
    private final Map<String, DeliveryAgent> agents;
    private final Map<String, Order> orders;
    private final AtomicInteger orderCounter;

    private FoodDeliveryService() {
        this.restaurants = new HashMap<>();
        this.customers = new HashMap<>();
        this.agents = new HashMap<>();
        this.orders = new HashMap<>();
        this.orderCounter = new AtomicInteger(0);
    }

    /**
     * Returns the singleton instance, creating it on first access.
     */
    public static synchronized FoodDeliveryService getInstance() {
        if (instance == null) {
            instance = new FoodDeliveryService();
        }
        return instance;
    }

    /**
     * Resets the singleton instance — intended for testing / demo resets only.
     */
    public static synchronized void resetInstance() {
        instance = null;
    }

    // ── Registration ─────────────────────────────────────────────────

    public void registerRestaurant(Restaurant restaurant) {
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant must not be null");
        }
        restaurants.put(restaurant.getId(), restaurant);
    }

    public void registerCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer must not be null");
        }
        customers.put(customer.getId(), customer);
    }

    public void registerAgent(DeliveryAgent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("DeliveryAgent must not be null");
        }
        agents.put(agent.getId(), agent);
    }

    // ── Queries ──────────────────────────────────────────────────────

    public Restaurant getRestaurant(String id) { return restaurants.get(id); }
    public Customer getCustomer(String id) { return customers.get(id); }
    public DeliveryAgent getAgent(String id) { return agents.get(id); }
    public Order getOrder(String id) { return orders.get(id); }

    public List<Restaurant> getAllRestaurants() { return Collections.unmodifiableList(new ArrayList<>(restaurants.values())); }
    public List<Customer> getAllCustomers() { return Collections.unmodifiableList(new ArrayList<>(customers.values())); }
    public List<DeliveryAgent> getAllAgents() { return Collections.unmodifiableList(new ArrayList<>(agents.values())); }
    public List<Order> getAllOrders() { return Collections.unmodifiableList(new ArrayList<>(orders.values())); }

    // ── Place Order ──────────────────────────────────────────────────

    /**
     * Places a new order for the given customer at the given restaurant.
     * Validates that every ordered item belongs to the restaurant's menu.
     *
     * @return the created {@link Order}
     * @throws IllegalArgumentException if customer/restaurant not found or items invalid
     */
    public Order placeOrder(String customerId, String restaurantId, List<OrderItem> items) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found: " + customerId);
        }
        Restaurant restaurant = restaurants.get(restaurantId);
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant not found: " + restaurantId);
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        // Validate all items are on the restaurant's menu
        List<MenuItem> menu = restaurant.getMenu();
        for (OrderItem item : items) {
            if (!menu.contains(item.menuItem())) {
                throw new IllegalArgumentException(
                        "MenuItem '" + item.menuItem().name()
                                + "' is not on the menu of " + restaurant.getName());
            }
        }

        String orderId = "ORD-" + orderCounter.incrementAndGet();
        Order order = new Order(orderId, customer, restaurant, items);
        orders.put(orderId, order);
        customer.addOrder(order);
        return order;
    }

    // ── Assign Delivery Agent ────────────────────────────────────────

    /**
     * Assigns the nearest available delivery agent to the given order.
     * Distance is measured from the agent's location to the restaurant's
     * hash-code-derived coordinates (simplified for demo purposes).
     *
     * @throws IllegalStateException if no available agents exist
     */
    public void assignAgent(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }

        // Use restaurant address hash as a crude location proxy
        double restX = Math.abs(order.getRestaurant().getAddress().hashCode() % 100);
        double restY = Math.abs(order.getRestaurant().getAddress().hashCode() / 100 % 100);

        DeliveryAgent nearest = agents.values().stream()
                .filter(a -> a.getStatus() == AgentStatus.AVAILABLE)
                .min(Comparator.comparingDouble(a -> a.distanceTo(restX, restY)))
                .orElseThrow(() -> new IllegalStateException(
                        "No available delivery agents"));

        order.assignAgent(nearest);
        order.setStatus(OrderStatus.CONFIRMED);
    }

    // ── Status Updates ───────────────────────────────────────────────

    /**
     * Advances the order to the given status.
     * If the order is delivered or cancelled, the assigned agent is freed.
     */
    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        order.setStatus(newStatus);

        // Free the agent when order reaches a terminal state
        if ((newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.CANCELLED)
                && order.getDeliveryAgent() != null) {
            order.getDeliveryAgent().setStatus(AgentStatus.AVAILABLE);
        }
    }

    // ── Track Order ──────────────────────────────────────────────────

    /**
     * Returns a human-readable tracking summary for the given order.
     */
    public String trackOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== Order Tracking: ").append(orderId).append(" ===\n");
        sb.append("  Customer   : ").append(order.getCustomer().getName()).append("\n");
        sb.append("  Restaurant : ").append(order.getRestaurant().getName()).append("\n");
        sb.append("  Status     : ").append(order.getStatus()).append("\n");
        sb.append("  Items      : ").append(order.getItems()).append("\n");
        sb.append("  Total      : $").append(String.format("%.2f", order.getTotalPrice())).append("\n");
        sb.append("  Agent      : ").append(
                order.getDeliveryAgent() == null ? "Not yet assigned"
                        : order.getDeliveryAgent().getName()).append("\n");
        return sb.toString();
    }

    // ── Rate Restaurant ──────────────────────────────────────────────

    /**
     * Records a rating (1.0–5.0) for the restaurant associated with the given order.
     * The order must be in {@code DELIVERED} status.
     */
    public void rateRestaurant(String orderId, double rating) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        if (order.getStatus() != OrderStatus.DELIVERED) {
            throw new IllegalStateException(
                    "Can only rate after delivery; current status: " + order.getStatus());
        }
        order.getRestaurant().addRating(rating);
    }
}
