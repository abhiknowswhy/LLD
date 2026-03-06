package questions.lld.FoodDeliverySystem;

/**
 * Immutable record representing a single item on a restaurant's menu,
 * including its name, price, and category.
 */
public record MenuItem(String name, double price, MenuCategory category) {

    /**
     * Compact constructor that validates all fields.
     */
    public MenuItem {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("MenuItem name must not be null or blank");
        }
        if (price < 0) {
            throw new IllegalArgumentException("MenuItem price must not be negative");
        }
        if (category == null) {
            throw new IllegalArgumentException("MenuItem category must not be null");
        }
    }

    @Override
    public String toString() {
        return String.format("%s ($%.2f, %s)", name, price, category);
    }
}
