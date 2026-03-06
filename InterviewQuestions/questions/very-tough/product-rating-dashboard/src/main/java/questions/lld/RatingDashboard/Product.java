package questions.lld.RatingDashboard;

/**
 * Represents a product in the rating dashboard.
 * Each product has a unique identifier, name, category, and description.
 */
public class Product {
    private final String id;
    private final String name;
    private final ProductCategory category;
    private final String description;

    public Product(String id, String name, ProductCategory category, String description) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product id must not be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name must not be null or blank");
        }
        if (category == null) {
            throw new IllegalArgumentException("Product category must not be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("Product description must not be null");
        }
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public ProductCategory getCategory() { return category; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "Product{id='%s', name='%s', category=%s}".formatted(id, name, category);
    }
}
