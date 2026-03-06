package questions.lld.RatingDashboard;

import java.time.LocalDateTime;

/**
 * Represents a single review submitted by a user for a product.
 * Rating is on a 1–5 integer scale. Helpful votes can be incremented
 * by other users to surface the most useful reviews.
 */
public class Review {
    private final String id;
    private final User user;
    private final Product product;
    private final int rating;
    private final String title;
    private final String comment;
    private final LocalDateTime timestamp;
    private int helpfulVotes;

    public Review(String id, User user, Product product, int rating,
                  String title, String comment, LocalDateTime timestamp) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Review id must not be null or blank");
        }
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5, got: " + rating);
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Review title must not be null or blank");
        }
        if (comment == null) {
            throw new IllegalArgumentException("Review comment must not be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp must not be null");
        }
        this.id = id;
        this.user = user;
        this.product = product;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
        this.timestamp = timestamp;
        this.helpfulVotes = 0;
    }

    public String getId() { return id; }
    public User getUser() { return user; }
    public Product getProduct() { return product; }
    public int getRating() { return rating; }
    public String getTitle() { return title; }
    public String getComment() { return comment; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getHelpfulVotes() { return helpfulVotes; }

    /**
     * Increments the helpful-vote counter by one and returns the new total.
     */
    public int markHelpful() { return ++helpfulVotes; }

    @Override
    public String toString() {
        return "Review{id='%s', user='%s', product='%s', rating=%d, title='%s', votes=%d, at=%s}"
                .formatted(id, user.getName(), product.getName(), rating, title, helpfulVotes, timestamp);
    }
}
