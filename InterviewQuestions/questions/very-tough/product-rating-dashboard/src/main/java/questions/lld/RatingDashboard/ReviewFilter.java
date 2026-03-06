package questions.lld.RatingDashboard;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Immutable filter criteria for narrowing down reviews.
 * Supports filtering by minimum/maximum rating, date range, and keyword search
 * across the review title and comment.  Uses a builder for flexible construction.
 */
public class ReviewFilter {
    private final Integer minRating;       // nullable — no lower bound if null
    private final Integer maxRating;       // nullable — no upper bound if null
    private final LocalDateTime fromDate;  // nullable — no start bound if null
    private final LocalDateTime toDate;    // nullable — no end bound if null
    private final String keyword;          // nullable — no keyword filter if null

    private ReviewFilter(Builder builder) {
        if (builder.minRating != null && (builder.minRating < 1 || builder.minRating > 5)) {
            throw new IllegalArgumentException("minRating must be between 1 and 5");
        }
        if (builder.maxRating != null && (builder.maxRating < 1 || builder.maxRating > 5)) {
            throw new IllegalArgumentException("maxRating must be between 1 and 5");
        }
        if (builder.minRating != null && builder.maxRating != null
                && builder.minRating > builder.maxRating) {
            throw new IllegalArgumentException("minRating cannot exceed maxRating");
        }
        if (builder.fromDate != null && builder.toDate != null
                && builder.fromDate.isAfter(builder.toDate)) {
            throw new IllegalArgumentException("fromDate cannot be after toDate");
        }
        this.minRating = builder.minRating;
        this.maxRating = builder.maxRating;
        this.fromDate = builder.fromDate;
        this.toDate = builder.toDate;
        this.keyword = builder.keyword;
    }

    /**
     * Tests whether the given review matches all active filter criteria.
     */
    public boolean matches(Review review) {
        if (minRating != null && review.getRating() < minRating) return false;
        if (maxRating != null && review.getRating() > maxRating) return false;
        if (fromDate != null && review.getTimestamp().isBefore(fromDate)) return false;
        if (toDate != null && review.getTimestamp().isAfter(toDate)) return false;
        if (keyword != null && !keyword.isBlank()) {
            String lower = keyword.toLowerCase();
            boolean inTitle = review.getTitle().toLowerCase().contains(lower);
            boolean inComment = review.getComment().toLowerCase().contains(lower);
            if (!inTitle && !inComment) return false;
        }
        return true;
    }

    public Optional<Integer> getMinRating() { return Optional.ofNullable(minRating); }
    public Optional<Integer> getMaxRating() { return Optional.ofNullable(maxRating); }
    public Optional<LocalDateTime> getFromDate() { return Optional.ofNullable(fromDate); }
    public Optional<LocalDateTime> getToDate() { return Optional.ofNullable(toDate); }
    public Optional<String> getKeyword() { return Optional.ofNullable(keyword); }

    @Override
    public String toString() {
        return "ReviewFilter{minRating=%s, maxRating=%s, from=%s, to=%s, keyword='%s'}"
                .formatted(minRating, maxRating, fromDate, toDate, keyword);
    }

    /**
     * Builder for constructing ReviewFilter instances with a fluent API.
     */
    public static class Builder {
        private Integer minRating;
        private Integer maxRating;
        private LocalDateTime fromDate;
        private LocalDateTime toDate;
        private String keyword;

        public Builder minRating(int minRating) { this.minRating = minRating; return this; }
        public Builder maxRating(int maxRating) { this.maxRating = maxRating; return this; }
        public Builder fromDate(LocalDateTime fromDate) { this.fromDate = fromDate; return this; }
        public Builder toDate(LocalDateTime toDate) { this.toDate = toDate; return this; }
        public Builder keyword(String keyword) { this.keyword = keyword; return this; }

        public ReviewFilter build() { return new ReviewFilter(this); }
    }
}
