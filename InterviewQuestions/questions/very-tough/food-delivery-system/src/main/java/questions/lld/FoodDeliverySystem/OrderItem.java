package questions.lld.FoodDeliverySystem;

/**
 * Immutable record representing a line item in an order,
 * binding a {@link MenuItem} to the requested quantity.
 */
public record OrderItem(MenuItem menuItem, int quantity) {

    /**
     * Compact constructor that validates all fields.
     */
    public OrderItem {
        if (menuItem == null) {
            throw new IllegalArgumentException("OrderItem menuItem must not be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("OrderItem quantity must be positive");
        }
    }

    /**
     * Returns the subtotal for this line item (price × quantity).
     */
    public double subtotal() {
        return menuItem.price() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s x%d ($%.2f)", menuItem.name(), quantity, subtotal());
    }
}
