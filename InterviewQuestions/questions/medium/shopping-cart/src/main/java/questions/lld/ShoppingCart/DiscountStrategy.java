package questions.lld.ShoppingCart;

/**
 * Strategy pattern for discount/pricing rules.
 */
public interface DiscountStrategy {
    double applyDiscount(double total);
    String getDescription();
}
