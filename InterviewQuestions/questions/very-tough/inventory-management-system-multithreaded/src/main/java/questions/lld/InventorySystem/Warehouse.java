package questions.lld.InventorySystem;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a physical warehouse that holds inventory for products.
 * All inventory mutations are thread-safe, backed by a {@link ConcurrentHashMap}
 * and guarded by a {@link ReentrantLock} for compound operations.
 */
public class Warehouse {

    private final String id;
    private final String name;
    private final ConcurrentHashMap<Product, Integer> inventory;
    private final ReentrantLock lock;

    public Warehouse(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Warehouse id must not be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Warehouse name must not be null or blank");
        }
        this.id = id;
        this.name = name;
        this.inventory = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
    }

    public String getId() { return id; }

    public String getName() { return name; }

    /**
     * Returns the lock used for guarding compound operations on this warehouse.
     * The {@link InventoryManager} uses this to implement deadlock-free transfers.
     */
    public ReentrantLock getLock() { return lock; }

    /**
     * Returns an unmodifiable snapshot of the current inventory.
     */
    public Map<Product, Integer> getInventory() { return Collections.unmodifiableMap(inventory); }

    /**
     * Returns the current stock quantity for the given product, or 0 if absent.
     */
    public int getStock(Product product) {
        return inventory.getOrDefault(product, 0);
    }

    /**
     * Adds the given quantity of a product to inventory.
     * Caller must hold the warehouse lock.
     */
    public void addStock(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        inventory.merge(product, quantity, Integer::sum);
    }

    /**
     * Removes the given quantity of a product from inventory.
     * Caller must hold the warehouse lock.
     *
     * @throws IllegalStateException if insufficient stock is available
     */
    public void removeStock(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        int current = inventory.getOrDefault(product, 0);
        if (current < quantity) {
            throw new IllegalStateException(
                    "Insufficient stock for %s in warehouse %s: available=%d, requested=%d"
                            .formatted(product.getName(), name, current, quantity));
        }
        int remaining = current - quantity;
        if (remaining == 0) {
            inventory.remove(product);
        } else {
            inventory.put(product, remaining);
        }
    }

    @Override
    public String toString() {
        return "Warehouse{id='%s', name='%s', products=%d}".formatted(id, name, inventory.size());
    }
}
