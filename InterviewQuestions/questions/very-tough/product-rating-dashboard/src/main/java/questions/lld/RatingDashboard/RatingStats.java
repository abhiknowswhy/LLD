package questions.lld.RatingDashboard;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Computed rating statistics for a single product.
 * Includes the average rating, total review count, a 1–5 histogram
 * of rating distribution, and the most helpful review (if any).
 */
public class RatingStats {
    private final Product product;
    private final double averageRating;
    private final int totalReviews;
    private final Map<Integer, Integer> ratingDistribution; // rating → count
    private final Review mostHelpfulReview; // nullable

    public RatingStats(Product product, List<Review> reviews) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        if (reviews == null) {
            throw new IllegalArgumentException("Reviews list must not be null");
        }
        this.product = product;
        this.totalReviews = reviews.size();

        // Build histogram
        Map<Integer, Integer> dist = new java.util.LinkedHashMap<>();
        for (int star = 1; star <= 5; star++) {
            dist.put(star, 0);
        }
        for (Review r : reviews) {
            dist.merge(r.getRating(), 1, Integer::sum);
        }
        this.ratingDistribution = Collections.unmodifiableMap(dist);

        // Average
        this.averageRating = reviews.isEmpty()
                ? 0.0
                : reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);

        // Most helpful
        this.mostHelpfulReview = reviews.stream()
                .max(java.util.Comparator.comparingInt(Review::getHelpfulVotes))
                .filter(r -> r.getHelpfulVotes() > 0)
                .orElse(null);
    }

    public Product getProduct() { return product; }
    public double getAverageRating() { return averageRating; }
    public int getTotalReviews() { return totalReviews; }
    public Map<Integer, Integer> getRatingDistribution() { return ratingDistribution; }
    public Optional<Review> getMostHelpfulReview() { return Optional.ofNullable(mostHelpfulReview); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RatingStats for '%s':\n".formatted(product.getName()));
        sb.append("  Average: %.2f / 5  |  Total reviews: %d\n".formatted(averageRating, totalReviews));
        sb.append("  Distribution:");
        ratingDistribution.forEach((star, count) -> {
            String bar = "█".repeat(count);
            sb.append("\n    %d★ : %-20s (%d)".formatted(star, bar, count));
        });
        if (mostHelpfulReview != null) {
            sb.append("\n  Most helpful: \"%s\" by %s (%d votes)"
                    .formatted(mostHelpfulReview.getTitle(),
                            mostHelpfulReview.getUser().getName(),
                            mostHelpfulReview.getHelpfulVotes()));
        }
        return sb.toString();
    }
}
