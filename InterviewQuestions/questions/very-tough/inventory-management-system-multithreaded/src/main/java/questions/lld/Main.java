package questions.lld;

import questions.lld.InventorySystem.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demo driver for the Multithreaded Inventory Management System LLD.
 * Exercises product/warehouse registration, concurrent stock operations,
 * atomic transfers, low-stock alerts, transaction history, and
 * deadlock-free concurrent transfers.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        // ╔══════════════════════════════════════════════════════════════╗
        // ║        MULTITHREADED INVENTORY MANAGEMENT SYSTEM            ║
        // ╚══════════════════════════════════════════════════════════════╝

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        MULTITHREADED INVENTORY MANAGEMENT SYSTEM            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // ── SETUP PHASE ──────────────────────────────────────────────

        System.out.println("\n========== SETUP PHASE ==========\n");

        InventoryManager manager = new InventoryManager(5);
        System.out.println("Created InventoryManager with low-stock threshold = " + manager.getLowStockThreshold());

        // --- Products ---
        Product laptop = new Product("P1", "Laptop", ProductCategory.ELECTRONICS, 999.99);
        Product phone = new Product("P2", "Smartphone", ProductCategory.ELECTRONICS, 699.99);
        Product tshirt = new Product("P3", "T-Shirt", ProductCategory.CLOTHING, 19.99);
        Product novel = new Product("P4", "Java in Action", ProductCategory.BOOKS, 49.99);
        Product pasta = new Product("P5", "Organic Pasta", ProductCategory.FOOD, 3.49);

        manager.registerProduct(laptop);
        manager.registerProduct(phone);
        manager.registerProduct(tshirt);
        manager.registerProduct(novel);
        manager.registerProduct(pasta);
        System.out.println("Registered products: " + laptop.getName() + ", " + phone.getName()
                + ", " + tshirt.getName() + ", " + novel.getName() + ", " + pasta.getName());

        // --- Warehouses ---
        Warehouse warehouseA = new Warehouse("W1", "East Coast Hub");
        Warehouse warehouseB = new Warehouse("W2", "West Coast Hub");
        Warehouse warehouseC = new Warehouse("W3", "Central Hub");

        manager.registerWarehouse(warehouseA);
        manager.registerWarehouse(warehouseB);
        manager.registerWarehouse(warehouseC);
        System.out.println("Registered warehouses: " + warehouseA.getName() + ", "
                + warehouseB.getName() + ", " + warehouseC.getName());

        // ── EXERCISE PHASE ───────────────────────────────────────────

        System.out.println("\n========== EXERCISE PHASE ==========\n");

        // --- 1. Single-threaded stock operations ---
        System.out.println("--- 1. Single-Threaded Stock Operations ---");
        manager.addStock("W1", "P1", 50);
        manager.addStock("W1", "P2", 100);
        manager.addStock("W1", "P3", 200);
        manager.addStock("W2", "P4", 80);
        manager.addStock("W2", "P5", 150);
        manager.addStock("W3", "P1", 30);
        manager.addStock("W3", "P2", 60);
        System.out.println("Initial stock loaded into all warehouses");

        System.out.println("  W1 Laptop stock : " + manager.getStockLevel("W1", "P1"));
        System.out.println("  W1 Phone stock  : " + manager.getStockLevel("W1", "P2"));
        System.out.println("  W2 Novel stock  : " + manager.getStockLevel("W2", "P4"));
        System.out.println("  W3 Laptop stock : " + manager.getStockLevel("W3", "P1"));

        manager.removeStock("W1", "P1", 10);
        System.out.println("Removed 10 laptops from W1 → remaining: " + manager.getStockLevel("W1", "P1"));

        // --- 2. Transfer between warehouses ---
        System.out.println("\n--- 2. Atomic Transfer Between Warehouses ---");
        System.out.println("  Before transfer — W1 Laptop: " + manager.getStockLevel("W1", "P1")
                + ", W3 Laptop: " + manager.getStockLevel("W3", "P1"));
        manager.transferStock("W1", "W3", "P1", 15);
        System.out.println("  Transferred 15 laptops W1 → W3");
        System.out.println("  After transfer  — W1 Laptop: " + manager.getStockLevel("W1", "P1")
                + ", W3 Laptop: " + manager.getStockLevel("W3", "P1"));

        // --- 3. Concurrent stock additions from multiple threads ---
        System.out.println("\n--- 3. Concurrent Stock Additions (10 threads × 100 units each) ---");
        int threadCount = 10;
        int unitsPerThread = 100;
        CountDownLatch addLatch = new CountDownLatch(threadCount);
        ExecutorService addPool = Executors.newFixedThreadPool(threadCount);

        int laptopBefore = manager.getStockLevel("W2", "P1");
        manager.addStock("W2", "P1", 0 + 1); // ensure product exists in W2
        // reset — remove the 1 we just added
        manager.removeStock("W2", "P1", 1);

        for (int i = 0; i < threadCount; i++) {
            addPool.submit(() -> {
                try {
                    for (int j = 0; j < unitsPerThread; j++) {
                        manager.addStock("W2", "P1", 1);
                    }
                } finally {
                    addLatch.countDown();
                }
            });
        }
        addLatch.await(10, TimeUnit.SECONDS);
        addPool.shutdown();

        int laptopAfter = manager.getStockLevel("W2", "P1");
        int expected = laptopBefore + (threadCount * unitsPerThread);
        System.out.println("  W2 Laptop before: " + laptopBefore + ", after: " + laptopAfter
                + ", expected: " + expected);
        System.out.println("  Thread-safety " + (laptopAfter == expected ? "PASSED ✓" : "FAILED ✗"));

        // --- 4. Concurrent transfers (deadlock prevention test) ---
        System.out.println("\n--- 4. Concurrent Bi-directional Transfers (Deadlock Prevention) ---");
        // Ensure both warehouses have enough stock
        manager.addStock("W1", "P2", 500);
        manager.addStock("W3", "P2", 500);

        int w1PhoneBefore = manager.getStockLevel("W1", "P2");
        int w3PhoneBefore = manager.getStockLevel("W3", "P2");
        int transfersPerDirection = 50;
        int transferAmount = 5;
        CountDownLatch transferLatch = new CountDownLatch(2);
        ExecutorService transferPool = Executors.newFixedThreadPool(2);

        // Thread 1: W1 → W3
        transferPool.submit(() -> {
            try {
                for (int i = 0; i < transfersPerDirection; i++) {
                    manager.transferStock("W1", "W3", "P2", transferAmount);
                }
            } finally {
                transferLatch.countDown();
            }
        });

        // Thread 2: W3 → W1 (opposite direction — classic deadlock scenario)
        transferPool.submit(() -> {
            try {
                for (int i = 0; i < transfersPerDirection; i++) {
                    manager.transferStock("W3", "W1", "P2", transferAmount);
                }
            } finally {
                transferLatch.countDown();
            }
        });

        boolean completed = transferLatch.await(15, TimeUnit.SECONDS);
        transferPool.shutdown();

        int w1PhoneAfter = manager.getStockLevel("W1", "P2");
        int w3PhoneAfter = manager.getStockLevel("W3", "P2");

        System.out.println("  Completed without deadlock: " + (completed ? "YES ✓" : "TIMED OUT ✗"));
        System.out.println("  W1 Phone before: " + w1PhoneBefore + ", after: " + w1PhoneAfter);
        System.out.println("  W3 Phone before: " + w3PhoneBefore + ", after: " + w3PhoneAfter);
        int totalBefore = w1PhoneBefore + w3PhoneBefore;
        int totalAfter = w1PhoneAfter + w3PhoneAfter;
        System.out.println("  Stock conservation (total before == after): "
                + totalBefore + " == " + totalAfter + " → "
                + (totalBefore == totalAfter ? "PASSED ✓" : "FAILED ✗"));

        // --- 5. Low-stock alerts ---
        System.out.println("\n--- 5. Low-Stock Alerts (threshold = " + manager.getLowStockThreshold() + ") ---");
        // Add a product with very low stock
        manager.addStock("W3", "P5", 3);
        Map<String, List<Product>> alerts = manager.getLowStockAlerts();
        for (var entry : alerts.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                System.out.println("  Warehouse " + entry.getKey() + " low-stock products:");
                for (Product p : entry.getValue()) {
                    System.out.println("    - " + p.getName() + " (qty: "
                            + manager.getStockLevel(entry.getKey(), p.getId()) + ")");
                }
            }
        }

        // ── VERIFICATION PHASE ───────────────────────────────────────

        System.out.println("\n========== VERIFICATION PHASE ==========\n");

        // --- Transaction history ---
        System.out.println("--- Transaction History Summary ---");
        List<StockTransaction> history = manager.getTransactionHistory();
        System.out.println("Total transactions recorded: " + history.size());

        long addCount = history.stream().filter(t -> t.operation() == StockOperation.ADD).count();
        long removeCount = history.stream().filter(t -> t.operation() == StockOperation.REMOVE).count();
        long transferCount = history.stream().filter(t -> t.operation() == StockOperation.TRANSFER).count();
        System.out.println("  ADD       : " + addCount);
        System.out.println("  REMOVE    : " + removeCount);
        System.out.println("  TRANSFER  : " + transferCount);

        System.out.println("\n--- W1 Transactions (first 5) ---");
        List<StockTransaction> w1Txns = manager.getTransactionsByWarehouse("W1");
        w1Txns.stream().limit(5).forEach(t ->
                System.out.println("  " + t.operation() + " " + t.product().getName()
                        + " qty=" + t.quantity() + " @ " + t.timestamp()));

        System.out.println("\n--- Laptop (P1) Transactions (first 5) ---");
        List<StockTransaction> laptopTxns = manager.getTransactionsByProduct("P1");
        laptopTxns.stream().limit(5).forEach(t ->
                System.out.println("  " + t.operation() + " wh=" + t.warehouseId()
                        + " qty=" + t.quantity() + " @ " + t.timestamp()));

        // --- Validation checks ---
        System.out.println("\n--- Validation Checks ---");

        // Duplicate warehouse
        try {
            manager.registerWarehouse(new Warehouse("W1", "Duplicate"));
            System.out.println("  Duplicate warehouse: FAILED ✗ (no exception)");
        } catch (IllegalArgumentException e) {
            System.out.println("  Duplicate warehouse rejected: PASSED ✓");
        }

        // Duplicate product
        try {
            manager.registerProduct(new Product("P1", "Dup", ProductCategory.OTHER, 1.0));
            System.out.println("  Duplicate product: FAILED ✗ (no exception)");
        } catch (IllegalArgumentException e) {
            System.out.println("  Duplicate product rejected: PASSED ✓");
        }

        // Remove more than available
        try {
            manager.removeStock("W1", "P3", 999_999);
            System.out.println("  Over-remove: FAILED ✗ (no exception)");
        } catch (IllegalStateException e) {
            System.out.println("  Over-remove rejected: PASSED ✓");
        }

        // Transfer to same warehouse
        try {
            manager.transferStock("W1", "W1", "P2", 1);
            System.out.println("  Self-transfer: FAILED ✗ (no exception)");
        } catch (IllegalArgumentException e) {
            System.out.println("  Self-transfer rejected: PASSED ✓");
        }

        // Negative-price product
        try {
            new Product("PX", "Bad", ProductCategory.OTHER, -10);
            System.out.println("  Negative price: FAILED ✗ (no exception)");
        } catch (IllegalArgumentException e) {
            System.out.println("  Negative price rejected: PASSED ✓");
        }

        // Final inventory snapshot
        System.out.println("\n--- Final Inventory Snapshots ---");
        for (String whId : List.of("W1", "W2", "W3")) {
            Map<Product, Integer> inv = manager.getWarehouseInventory(whId);
            System.out.println("  " + whId + ":");
            inv.forEach((p, qty) -> System.out.println("    " + p.getName() + " → " + qty));
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                        DEMO COMPLETE                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
}