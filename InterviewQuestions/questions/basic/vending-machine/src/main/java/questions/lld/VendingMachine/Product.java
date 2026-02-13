package questions.lld.VendingMachine;

/**
 * Represents a product in the vending machine.
 */
public class Product {
    private final String name;
    private final double price;

    public Product(String name, double price) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product other)) return false;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() { return name.hashCode(); }
}
