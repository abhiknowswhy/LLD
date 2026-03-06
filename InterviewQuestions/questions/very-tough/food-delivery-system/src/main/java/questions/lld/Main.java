package questions.lld;

import questions.lld.FoodDeliverySystem.*;

import java.util.List;

/**
 * Demo driver for the Food Delivery System LLD.
 * Exercises restaurant registration, order placement, agent assignment,
 * status lifecycle, order tracking, and restaurant rating.
 */
public class Main {
    public static void main(String[] args) {

        // ╔══════════════════════════════════════════════════════════════╗
        // ║                    FOOD DELIVERY SYSTEM                    ║
        // ╚══════════════════════════════════════════════════════════════╝

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    FOOD DELIVERY SYSTEM                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // ── SETUP PHASE ──────────────────────────────────────────────

        System.out.println("\n========== SETUP PHASE ==========\n");

        FoodDeliveryService.resetInstance();
        FoodDeliveryService service = FoodDeliveryService.getInstance();

        // --- Restaurants ---
        Restaurant italianBistro = new Restaurant("R1", "Italian Bistro", "123 Pasta Lane");
        italianBistro.addMenuItem(new MenuItem("Bruschetta", 8.99, MenuCategory.APPETIZER));
        italianBistro.addMenuItem(new MenuItem("Margherita Pizza", 14.99, MenuCategory.MAIN_COURSE));
        italianBistro.addMenuItem(new MenuItem("Spaghetti Carbonara", 16.49, MenuCategory.MAIN_COURSE));
        italianBistro.addMenuItem(new MenuItem("Tiramisu", 7.99, MenuCategory.DESSERT));
        italianBistro.addMenuItem(new MenuItem("Espresso", 3.49, MenuCategory.BEVERAGE));
        service.registerRestaurant(italianBistro);
        System.out.println("Registered: " + italianBistro);

        Restaurant sushiBar = new Restaurant("R2", "Tokyo Sushi Bar", "456 Ocean Drive");
        sushiBar.addMenuItem(new MenuItem("Edamame", 5.99, MenuCategory.APPETIZER));
        sushiBar.addMenuItem(new MenuItem("Salmon Nigiri", 12.99, MenuCategory.MAIN_COURSE));
        sushiBar.addMenuItem(new MenuItem("Dragon Roll", 18.49, MenuCategory.MAIN_COURSE));
        sushiBar.addMenuItem(new MenuItem("Mochi Ice Cream", 6.49, MenuCategory.DESSERT));
        sushiBar.addMenuItem(new MenuItem("Green Tea", 2.99, MenuCategory.BEVERAGE));
        service.registerRestaurant(sushiBar);
        System.out.println("Registered: " + sushiBar);

        // --- Customers ---
        Customer alice = new Customer("C1", "Alice Johnson", "789 Maple Street");
        Customer bob = new Customer("C2", "Bob Smith", "321 Oak Avenue");
        service.registerCustomer(alice);
        service.registerCustomer(bob);
        System.out.println("Registered: " + alice);
        System.out.println("Registered: " + bob);

        // --- Delivery Agents ---
        DeliveryAgent dave = new DeliveryAgent("A1", "Dave Runner", 10.0, 20.0);
        DeliveryAgent eve = new DeliveryAgent("A2", "Eve Speedster", 50.0, 60.0);
        DeliveryAgent frank = new DeliveryAgent("A3", "Frank Wheels", 30.0, 40.0);
        service.registerAgent(dave);
        service.registerAgent(eve);
        service.registerAgent(frank);
        System.out.println("Registered: " + dave);
        System.out.println("Registered: " + eve);
        System.out.println("Registered: " + frank);

        // ── EXERCISE PHASE ───────────────────────────────────────────

        System.out.println("\n========== EXERCISE PHASE ==========\n");

        // --- Place Order 1: Alice orders from Italian Bistro ---
        System.out.println("--- Placing Order 1 (Alice @ Italian Bistro) ---");
        MenuItem bruschetta = italianBistro.getMenu().get(0);
        MenuItem pizza = italianBistro.getMenu().get(1);
        MenuItem tiramisu = italianBistro.getMenu().get(3);

        Order order1 = service.placeOrder("C1", "R1", List.of(
                new OrderItem(bruschetta, 1),
                new OrderItem(pizza, 2),
                new OrderItem(tiramisu, 1)
        ));
        System.out.println("Placed: " + order1);

        // --- Place Order 2: Bob orders from Tokyo Sushi Bar ---
        System.out.println("\n--- Placing Order 2 (Bob @ Tokyo Sushi Bar) ---");
        MenuItem dragonRoll = sushiBar.getMenu().get(2);
        MenuItem mochi = sushiBar.getMenu().get(3);
        MenuItem greenTea = sushiBar.getMenu().get(4);

        Order order2 = service.placeOrder("C2", "R2", List.of(
                new OrderItem(dragonRoll, 3),
                new OrderItem(mochi, 2),
                new OrderItem(greenTea, 2)
        ));
        System.out.println("Placed: " + order2);

        // --- Assign Agents ---
        System.out.println("\n--- Assigning Delivery Agents ---");
        service.assignAgent(order1.getId());
        System.out.println("After assignment: " + order1);
        System.out.println("  Agent status: " + order1.getDeliveryAgent());

        service.assignAgent(order2.getId());
        System.out.println("After assignment: " + order2);
        System.out.println("  Agent status: " + order2.getDeliveryAgent());

        // --- Order Lifecycle: Order 1 ---
        System.out.println("\n--- Order 1 Lifecycle ---");
        service.updateOrderStatus(order1.getId(), OrderStatus.PREPARING);
        System.out.println("Status -> PREPARING: " + order1.getStatus());

        service.updateOrderStatus(order1.getId(), OrderStatus.OUT_FOR_DELIVERY);
        System.out.println("Status -> OUT_FOR_DELIVERY: " + order1.getStatus());

        service.updateOrderStatus(order1.getId(), OrderStatus.DELIVERED);
        System.out.println("Status -> DELIVERED: " + order1.getStatus());
        System.out.println("  Agent freed: " + order1.getDeliveryAgent());

        // --- Order Lifecycle: Order 2 ---
        System.out.println("\n--- Order 2 Lifecycle ---");
        service.updateOrderStatus(order2.getId(), OrderStatus.PREPARING);
        service.updateOrderStatus(order2.getId(), OrderStatus.CANCELLED);
        System.out.println("Status -> CANCELLED: " + order2.getStatus());
        System.out.println("  Agent freed: " + order2.getDeliveryAgent());

        // --- Track Order ---
        System.out.println("\n--- Tracking Orders ---");
        System.out.println(service.trackOrder(order1.getId()));
        System.out.println(service.trackOrder(order2.getId()));

        // --- Rate Restaurant ---
        System.out.println("--- Rating Restaurants ---");
        service.rateRestaurant(order1.getId(), 4.5);
        System.out.println("Italian Bistro rated 4.5 → avg: " + String.format("%.1f", italianBistro.getRating()));

        // --- Place another order to test re-use of freed agent ---
        System.out.println("\n--- Placing Order 3 (Alice @ Tokyo Sushi Bar, agent re-use) ---");
        MenuItem edamame = sushiBar.getMenu().get(0);
        MenuItem salmonNigiri = sushiBar.getMenu().get(1);

        Order order3 = service.placeOrder("C1", "R2", List.of(
                new OrderItem(edamame, 2),
                new OrderItem(salmonNigiri, 3)
        ));
        System.out.println("Placed: " + order3);

        service.assignAgent(order3.getId());
        System.out.println("Agent assigned: " + order3.getDeliveryAgent());

        service.updateOrderStatus(order3.getId(), OrderStatus.PREPARING);
        service.updateOrderStatus(order3.getId(), OrderStatus.OUT_FOR_DELIVERY);
        service.updateOrderStatus(order3.getId(), OrderStatus.DELIVERED);
        service.rateRestaurant(order3.getId(), 5.0);
        System.out.println("Tokyo Sushi Bar rated 5.0 → avg: " + String.format("%.1f", sushiBar.getRating()));

        // ── VERIFICATION PHASE ───────────────────────────────────────

        System.out.println("\n========== VERIFICATION PHASE ==========\n");

        // Menu modification
        System.out.println("--- Menu Modification ---");
        MenuItem newItem = new MenuItem("Panna Cotta", 8.49, MenuCategory.DESSERT);
        italianBistro.addMenuItem(newItem);
        System.out.println("Added to Italian Bistro: " + newItem);
        System.out.println("Menu size now: " + italianBistro.getMenu().size());

        italianBistro.removeMenuItem(newItem);
        System.out.println("Removed from Italian Bistro: " + newItem);
        System.out.println("Menu size now: " + italianBistro.getMenu().size());

        // Agent status toggle
        System.out.println("\n--- Agent Status Toggle ---");
        frank.setStatus(AgentStatus.OFFLINE);
        System.out.println("Frank set OFFLINE: " + frank);
        frank.setStatus(AgentStatus.AVAILABLE);
        System.out.println("Frank set AVAILABLE: " + frank);

        // Customer order history
        System.out.println("\n--- Customer Order History ---");
        System.out.println("Alice's orders: " + alice.getOrderHistory().size());
        alice.getOrderHistory().forEach(o ->
                System.out.println("  " + o.getId() + " → " + o.getStatus()));
        System.out.println("Bob's orders: " + bob.getOrderHistory().size());
        bob.getOrderHistory().forEach(o ->
                System.out.println("  " + o.getId() + " → " + o.getStatus()));

        // Unmodifiable collection check
        System.out.println("\n--- Immutability Check ---");
        try {
            italianBistro.getMenu().add(new MenuItem("Hack", 0, MenuCategory.BEVERAGE));
            System.out.println("ERROR: should not be mutable!");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: getMenu() returns unmodifiable list");
        }

        try {
            alice.getOrderHistory().clear();
            System.out.println("ERROR: should not be mutable!");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: getOrderHistory() returns unmodifiable list");
        }

        // Invalid state transitions
        System.out.println("\n--- Invalid State Transition Check ---");
        try {
            service.updateOrderStatus(order1.getId(), OrderStatus.PREPARING);
            System.out.println("ERROR: should have thrown!");
        } catch (IllegalStateException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        // Validation checks
        System.out.println("\n--- Validation Checks ---");
        try {
            new MenuItem("", 10, MenuCategory.APPETIZER);
            System.out.println("ERROR: should have thrown!");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }
        try {
            new OrderItem(bruschetta, 0);
            System.out.println("ERROR: should have thrown!");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        // Summary
        System.out.println("\n========== SUMMARY ==========");
        System.out.println("Restaurants : " + service.getAllRestaurants().size());
        System.out.println("Customers   : " + service.getAllCustomers().size());
        System.out.println("Agents      : " + service.getAllAgents().size());
        System.out.println("Orders      : " + service.getAllOrders().size());

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              FOOD DELIVERY SYSTEM — DEMO DONE              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
}