package questions.lld.InventorySystem;

import java.util.Objects;

/**
 * Represents a product in the inventory system with a unique id, name,
 * category, and price. Immutable once constructed.
 */
public class Product {

    private final String id;
    private final String name;
    private final ProductCategory category;
    private final double price;

    public Product(String id, String name, ProductCategory category, double price) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product id must not be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name must not be null or blank");
        }
        if (category == null) {
            throw new IllegalArgumentException("Product category must not be null");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Product price must not be negative");
        }
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public ProductCategory getCategory() { return category; }

    public double getPrice() { return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Product{id='%s', name='%s', category=%s, price=%.2f}".formatted(id, name, category, price);
    }
}
