package questions.lld.FoodDeliverySystem;

import java.util.*;

/**
 * Represents a restaurant in the food delivery system.
 * Maintains a menu of {@link MenuItem}s, an address, and a cumulative rating.
 */
public class Restaurant {
    private final String id;
    private final String name;
    private final String address;
    private final List<MenuItem> menu;
    private double totalRating;
    private int ratingCount;

    public Restaurant(String id, String name, String address) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Restaurant id must not be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Restaurant name must not be null or blank");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Restaurant address must not be null or blank");
        }
        this.id = id;
        this.name = name;
        this.address = address;
        this.menu = new ArrayList<>();
        this.totalRating = 0.0;
        this.ratingCount = 0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public List<MenuItem> getMenu() { return Collections.unmodifiableList(menu); }

    /**
     * Returns the average rating, or 0.0 if not yet rated.
     */
    public double getRating() { return ratingCount == 0 ? 0.0 : totalRating / ratingCount; }

    /**
     * Adds a {@link MenuItem} to this restaurant's menu.
     *
     * @throws IllegalArgumentException if the item is null or already exists
     */
    public void addMenuItem(MenuItem item) {
        if (item == null) {
            throw new IllegalArgumentException("MenuItem must not be null");
        }
        if (menu.contains(item)) {
            throw new IllegalArgumentException("MenuItem already exists in the menu: " + item.name());
        }
        menu.add(item);
    }

    /**
     * Removes a {@link MenuItem} from this restaurant's menu.
     *
     * @throws IllegalArgumentException if the item is null or not found
     */
    public void removeMenuItem(MenuItem item) {
        if (item == null) {
            throw new IllegalArgumentException("MenuItem must not be null");
        }
        if (!menu.remove(item)) {
            throw new IllegalArgumentException("MenuItem not found in the menu: " + item.name());
        }
    }

    /**
     * Records a rating for this restaurant (1.0–5.0).
     *
     * @throws IllegalArgumentException if the rating is out of range
     */
    public void addRating(double rating) {
        if (rating < 1.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 1.0 and 5.0");
        }
        this.totalRating += rating;
        this.ratingCount++;
    }

    @Override
    public String toString() {
        return String.format("Restaurant{%s, '%s', menu=%d items, rating=%.1f}",
                id, name, menu.size(), getRating());
    }
}
