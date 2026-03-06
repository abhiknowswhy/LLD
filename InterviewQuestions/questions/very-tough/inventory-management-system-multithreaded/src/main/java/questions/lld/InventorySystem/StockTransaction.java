package questions.lld.InventorySystem;

import java.time.Instant;

/**
 * Immutable record capturing a single stock operation event.
 * Records the product, warehouse, type of operation, quantity, and timestamp.
 */
public record StockTransaction(
        Product product,
        String warehouseId,
        StockOperation operation,
        int quantity,
        Instant timestamp
) {

    public StockTransaction {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        if (warehouseId == null || warehouseId.isBlank()) {
            throw new IllegalArgumentException("Warehouse id must not be null or blank");
        }
        if (operation == null) {
            throw new IllegalArgumentException("Operation must not be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp must not be null");
        }
    }
}
