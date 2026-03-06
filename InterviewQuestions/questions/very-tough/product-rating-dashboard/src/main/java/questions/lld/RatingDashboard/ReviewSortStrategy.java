package questions.lld.RatingDashboard;

import java.util.Comparator;

/**
 * Enumeration of sorting strategies for reviews.
 * Each strategy provides a Comparator that orders reviews accordingly.
 */
public enum ReviewSortStrategy {
    MOST_RECENT(Comparator.comparing(Review::getTimestamp).reversed()),
    HIGHEST_RATED(Comparator.comparingInt(Review::getRating).reversed()),
    LOWEST_RATED(Comparator.comparingInt(Review::getRating)),
    MOST_HELPFUL(Comparator.comparingInt(Review::getHelpfulVotes).reversed());

    private final Comparator<Review> comparator;

    ReviewSortStrategy(Comparator<Review> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Review> getComparator() { return comparator; }
}
