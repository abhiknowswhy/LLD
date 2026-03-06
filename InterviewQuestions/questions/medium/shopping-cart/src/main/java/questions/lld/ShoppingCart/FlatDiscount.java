package questions.lld.ShoppingCart;

public class FlatDiscount implements DiscountStrategy {
    private final double amount;

    public FlatDiscount(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount must be non-negative");
        this.amount = amount;
    }

    @Override
    public double applyDiscount(double total) {
        return Math.max(0, total - amount);
    }

    @Override
    public String getDescription() {
        return String.format("$%.2f off", amount);
    }
}
