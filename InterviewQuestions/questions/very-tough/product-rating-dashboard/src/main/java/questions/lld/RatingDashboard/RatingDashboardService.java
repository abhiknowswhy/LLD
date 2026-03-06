package questions.lld.RatingDashboard;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Central orchestrator for the Product Rating Dashboard.
 * Manages products, users, and reviews.  Provides operations to add reviews,
 * query reviews with sorting and filtering, compute rating statistics,
 * retrieve top-rated products per category, mark reviews as helpful,
 * and look up a user's reviews.
 */
public class RatingDashboardService {
    private final Map<String, Product> products = new ConcurrentHashMap<>();
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final Map<String, Review> reviews = new ConcurrentHashMap<>();

    // product-id → list of review-ids (insertion order)
    private final Map<String, List<String>> productReviews = new ConcurrentHashMap<>();
    // user-id → list of review-ids
    private final Map<String, List<String>> userReviews = new ConcurrentHashMap<>();

    private final AtomicInteger reviewIdSeq = new AtomicInteger(1);

    // ── Registration ─────────────────────────────────────────────

    /**
     * Registers a product so that it can receive reviews.
     */
    public void registerProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        products.put(product.getId(), product);
        productReviews.putIfAbsent(product.getId(), Collections.synchronizedList(new ArrayList<>()));
    }

    /**
     * Registers a user so that they can submit reviews.
     */
    public void registerUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        users.put(user.getId(), user);
        userReviews.putIfAbsent(user.getId(), Collections.synchronizedList(new ArrayList<>()));
    }

    // ── Core operations ──────────────────────────────────────────

    /**
     * Adds a new review for a registered product by a registered user.
     *
     * @return the created Review with an auto-generated id
     * @throws IllegalArgumentException if user/product not registered, rating invalid,
     *                                  or the user already reviewed this product
     */
    public Review addReview(String userId, String productId, int rating,
                            String title, String comment, java.time.LocalDateTime timestamp) {
        User user = users.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not registered: " + userId);
        }
        Product product = products.get(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not registered: " + productId);
        }

        // One review per user per product
        boolean alreadyReviewed = getReviewsForProduct(productId).stream()
                .anyMatch(r -> r.getUser().getId().equals(userId));
        if (alreadyReviewed) {
            throw new IllegalArgumentException(
                    "User '%s' has already reviewed product '%s'".formatted(userId, productId));
        }

        String reviewId = "REV-" + reviewIdSeq.getAndIncrement();
        Review review = new Review(reviewId, user, product, rating, title, comment, timestamp);

        reviews.put(reviewId, review);
        productReviews.get(productId).add(reviewId);
        userReviews.computeIfAbsent(userId, k -> Collections.synchronizedList(new ArrayList<>()))
                .add(reviewId);
        return review;
    }

    /**
     * Retrieves all reviews for a product, sorted by the given strategy,
     * optionally filtered by the provided filter.
     */
    public List<Review> getProductReviews(String productId,
                                          ReviewSortStrategy sort,
                                          ReviewFilter filter) {
        List<Review> all = getReviewsForProduct(productId);
        return all.stream()
                .filter(r -> filter == null || filter.matches(r))
                .sorted(sort.getComparator())
                .toList(); // unmodifiable
    }

    /**
     * Computes and returns the rating statistics for a product.
     */
    public RatingStats getRatingStats(String productId) {
        Product product = products.get(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not registered: " + productId);
        }
        return new RatingStats(product, getReviewsForProduct(productId));
    }

    /**
     * Returns the top-rated products within a category, limited to {@code limit} results.
     * Products are ranked by their average review rating (descending).
     * Products with no reviews are excluded.
     */
    public List<Product> getTopRatedProducts(ProductCategory category, int limit) {
        if (category == null) {
            throw new IllegalArgumentException("Category must not be null");
        }
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must be at least 1");
        }
        return products.values().stream()
                .filter(p -> p.getCategory() == category)
                .filter(p -> !getReviewsForProduct(p.getId()).isEmpty())
                .sorted(Comparator.comparingDouble(
                        (Product p) -> getRatingStats(p.getId()).getAverageRating()).reversed())
                .limit(limit)
                .toList();
    }

    /**
     * Increments the helpful-vote count on a review and returns the new total.
     */
    public int markReviewHelpful(String reviewId) {
        Review review = reviews.get(reviewId);
        if (review == null) {
            throw new IllegalArgumentException("Review not found: " + reviewId);
        }
        return review.markHelpful();
    }

    /**
     * Returns all reviews submitted by a given user, most recent first.
     */
    public List<Review> getUserReviews(String userId) {
        if (!users.containsKey(userId)) {
            throw new IllegalArgumentException("User not registered: " + userId);
        }
        List<String> ids = userReviews.getOrDefault(userId, List.of());
        return ids.stream()
                .map(reviews::get)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Review::getTimestamp).reversed())
                .toList();
    }

    // ── Helpers ──────────────────────────────────────────────────

    private List<Review> getReviewsForProduct(String productId) {
        List<String> ids = productReviews.getOrDefault(productId, List.of());
        return ids.stream()
                .map(reviews::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Product getProduct(String productId) { return products.get(productId); }
    public User getUser(String userId) { return users.get(userId); }
    public Review getReview(String reviewId) { return reviews.get(reviewId); }
}
