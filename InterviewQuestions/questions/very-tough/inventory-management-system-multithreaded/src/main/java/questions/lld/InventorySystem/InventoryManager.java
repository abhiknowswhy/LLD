package questions.lld.InventorySystem;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Central orchestrator for the multithreaded inventory management system.
 * <p>
 * Manages warehouses and products, provides thread-safe add/remove/transfer
 * operations, tracks all transactions, and supports low-stock alerting.
 * <ul>
 *   <li>Fine-grained locking: each warehouse has its own {@link ReentrantLock}.</li>
 *   <li>Deadlock prevention: transfers lock warehouses in deterministic
 *       (id-sorted) order.</li>
 *   <li>Transaction history is append-only and thread-safe via
 *       {@link CopyOnWriteArrayList}.</li>
 * </ul>
 */
public class InventoryManager {

    private final ConcurrentHashMap<String, Warehouse> warehouses;
    private final ConcurrentHashMap<String, Product> products;
    private final CopyOnWriteArrayList<StockTransaction> transactionHistory;
    private final int lowStockThreshold;

    public InventoryManager(int lowStockThreshold) {
        if (lowStockThreshold < 0) {
            throw new IllegalArgumentException("Low-stock threshold must not be negative");
        }
        this.warehouses = new ConcurrentHashMap<>();
        this.products = new ConcurrentHashMap<>();
        this.transactionHistory = new CopyOnWriteArrayList<>();
        this.lowStockThreshold = lowStockThreshold;
    }

    // ── Registration ─────────────────────────────────────────────

    /**
     * Registers a warehouse so it can participate in inventory operations.
     */
    public void registerWarehouse(Warehouse warehouse) {
        if (warehouse == null) {
            throw new IllegalArgumentException("Warehouse must not be null");
        }
        if (warehouses.putIfAbsent(warehouse.getId(), warehouse) != null) {
            throw new IllegalArgumentException("Warehouse already registered: " + warehouse.getId());
        }
    }

    /**
     * Registers a product so it can be stocked in warehouses.
     */
    public void registerProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        if (products.putIfAbsent(product.getId(), product) != null) {
            throw new IllegalArgumentException("Product already registered: " + product.getId());
        }
    }

    // ── Stock Operations ─────────────────────────────────────────

    /**
     * Adds stock for a product in the specified warehouse.
     * Thread-safe — acquires the warehouse lock.
     */
    public void addStock(String warehouseId, String productId, int quantity) {
        Warehouse warehouse = resolveWarehouse(warehouseId);
        Product product = resolveProduct(productId);
        ReentrantLock lock = warehouse.getLock();
        lock.lock();
        try {
            warehouse.addStock(product, quantity);
            transactionHistory.add(new StockTransaction(
                    product, warehouseId, StockOperation.ADD, quantity, Instant.now()));
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes stock for a product from the specified warehouse.
     * Thread-safe — acquires the warehouse lock.
     *
     * @throws IllegalStateException if insufficient stock
     */
    public void removeStock(String warehouseId, String productId, int quantity) {
        Warehouse warehouse = resolveWarehouse(warehouseId);
        Product product = resolveProduct(productId);
        ReentrantLock lock = warehouse.getLock();
        lock.lock();
        try {
            warehouse.removeStock(product, quantity);
            transactionHistory.add(new StockTransaction(
                    product, warehouseId, StockOperation.REMOVE, quantity, Instant.now()));
        } finally {
            lock.unlock();
        }
    }

    /**
     * Atomically transfers stock of a product from one warehouse to another.
     * <p>
     * To prevent deadlocks, locks are always acquired in warehouse-id
     * lexicographic order regardless of source/destination.
     *
     * @throws IllegalStateException if insufficient stock in source
     */
    public void transferStock(String fromWarehouseId, String toWarehouseId, String productId, int quantity) {
        if (fromWarehouseId.equals(toWarehouseId)) {
            throw new IllegalArgumentException("Cannot transfer to the same warehouse");
        }
        Warehouse from = resolveWarehouse(fromWarehouseId);
        Warehouse to = resolveWarehouse(toWarehouseId);
        Product product = resolveProduct(productId);

        // Deterministic lock ordering to prevent deadlocks
        Warehouse first, second;
        if (fromWarehouseId.compareTo(toWarehouseId) < 0) {
            first = from;
            second = to;
        } else {
            first = to;
            second = from;
        }

        first.getLock().lock();
        try {
            second.getLock().lock();
            try {
                from.removeStock(product, quantity);
                to.addStock(product, quantity);
                Instant now = Instant.now();
                transactionHistory.add(new StockTransaction(
                        product, fromWarehouseId, StockOperation.TRANSFER, quantity, now));
                transactionHistory.add(new StockTransaction(
                        product, toWarehouseId, StockOperation.TRANSFER, quantity, now));
            } finally {
                second.getLock().unlock();
            }
        } finally {
            first.getLock().unlock();
        }
    }

    // ── Queries ──────────────────────────────────────────────────

    /**
     * Returns the current stock level for a product in a warehouse.
     */
    public int getStockLevel(String warehouseId, String productId) {
        return resolveWarehouse(warehouseId).getStock(resolveProduct(productId));
    }

    /**
     * Returns a snapshot of all inventory in the specified warehouse.
     */
    public Map<Product, Integer> getWarehouseInventory(String warehouseId) {
        return resolveWarehouse(warehouseId).getInventory();
    }

    /**
     * Returns an unmodifiable view of the full transaction history.
     */
    public List<StockTransaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    /**
     * Returns transactions filtered by warehouse id.
     */
    public List<StockTransaction> getTransactionsByWarehouse(String warehouseId) {
        return transactionHistory.stream()
                .filter(t -> t.warehouseId().equals(warehouseId))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Returns transactions filtered by product id.
     */
    public List<StockTransaction> getTransactionsByProduct(String productId) {
        Product product = resolveProduct(productId);
        return transactionHistory.stream()
                .filter(t -> t.product().equals(product))
                .collect(Collectors.toUnmodifiableList());
    }

    // ── Low-Stock Alerts ─────────────────────────────────────────

    /**
     * Scans all warehouses and returns a map of (warehouse → list of products)
     * whose stock is at or below the configured low-stock threshold.
     */
    public Map<String, List<Product>> getLowStockAlerts() {
        return warehouses.values().stream()
                .collect(Collectors.toUnmodifiableMap(
                        Warehouse::getId,
                        wh -> wh.getInventory().entrySet().stream()
                                .filter(e -> e.getValue() <= lowStockThreshold)
                                .map(Map.Entry::getKey)
                                .collect(Collectors.toUnmodifiableList()),
                        (a, b) -> a));
    }

    /**
     * Returns the configured low-stock threshold.
     */
    public int getLowStockThreshold() { return lowStockThreshold; }

    // ── Internals ────────────────────────────────────────────────

    private Warehouse resolveWarehouse(String warehouseId) {
        Warehouse wh = warehouses.get(warehouseId);
        if (wh == null) {
            throw new IllegalArgumentException("Unknown warehouse: " + warehouseId);
        }
        return wh;
    }

    private Product resolveProduct(String productId) {
        Product p = products.get(productId);
        if (p == null) {
            throw new IllegalArgumentException("Unknown product: " + productId);
        }
        return p;
    }
}
