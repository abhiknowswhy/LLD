package questions.lld.VendingMachine;

/**
 * A slot in the vending machine holding a product with a quantity.
 */
public class Slot {
    private final String code; // e.g., "A1", "B2"
    private Product product;
    private int quantity;

    public Slot(String code) {
        this.code = code;
        this.product = null;
        this.quantity = 0;
    }

    public String getCode() { return code; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public boolean isEmpty() { return product == null || quantity <= 0; }

    public void stock(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public void addStock(int additionalQuantity) {
        if (additionalQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity += additionalQuantity;
    }

    public Product dispense() {
        if (isEmpty()) {
            throw new IllegalStateException("Slot " + code + " is empty");
        }
        quantity--;
        return product;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return code + ": [EMPTY]";
        }
        return code + ": " + product + " (qty: " + quantity + ")";
    }
}
