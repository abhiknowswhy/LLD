package questions.lld.ShoppingCart;

import java.util.*;

public class Cart {
    private final Map<String, CartItem> items; // productId -> CartItem
    private DiscountStrategy discount;

    public Cart() {
        this.items = new LinkedHashMap<>();
    }

    public void addItem(Product product, int quantity) {
        CartItem existing = items.get(product.getProductId());
        if (existing != null) {
            existing.addQuantity(quantity);
        } else {
            items.put(product.getProductId(), new CartItem(product, quantity));
        }
    }

    public void removeItem(String productId) {
        items.remove(productId);
    }

    public void updateQuantity(String productId, int quantity) {
        CartItem item = items.get(productId);
        if (item == null) throw new IllegalArgumentException("Product not in cart: " + productId);
        if (quantity <= 0) {
            items.remove(productId);
        } else {
            item.setQuantity(quantity);
        }
    }

    public void setDiscount(DiscountStrategy discount) {
        this.discount = discount;
    }

    public double getSubtotal() {
        return items.values().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public double getTotal() {
        double subtotal = getSubtotal();
        if (discount != null) {
            return discount.applyDiscount(subtotal);
        }
        return subtotal;
    }

    public double getDiscountAmount() {
        return getSubtotal() - getTotal();
    }

    public int getItemCount() {
        return items.values().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public boolean isEmpty() { return items.isEmpty(); }
    public Collection<CartItem> getItems() { return items.values(); }
    public CartItem getItem(String productId) { return items.get(productId); }

    public void clear() {
        items.clear();
        discount = null;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder("=== Cart ===\n");
        for (CartItem item : items.values()) {
            sb.append("  ").append(item).append('\n');
        }
        sb.append(String.format("Subtotal: $%.2f\n", getSubtotal()));
        if (discount != null) {
            sb.append(String.format("Discount (%s): -$%.2f\n", discount.getDescription(), getDiscountAmount()));
        }
        sb.append(String.format("Total: $%.2f", getTotal()));
        return sb.toString();
    }
}
