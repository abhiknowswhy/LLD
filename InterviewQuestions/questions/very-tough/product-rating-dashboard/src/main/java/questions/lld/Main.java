package questions.lld;

import questions.lld.RatingDashboard.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Demo driver for the Product Rating Dashboard LLD.
 * Exercises product/user registration, review submission, sorting, filtering,
 * rating statistics, top-rated queries, helpful votes, and user review lookup.
 */
public class Main {
    public static void main(String[] args) {

        // ╔══════════════════════════════════════════════════════════════╗
        // ║               PRODUCT RATING DASHBOARD                      ║
        // ╚══════════════════════════════════════════════════════════════╝

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║               PRODUCT RATING DASHBOARD                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // ── SETUP PHASE ──────────────────────────────────────────────

        System.out.println("\n========== SETUP PHASE ==========\n");

        RatingDashboardService service = new RatingDashboardService();

        // Products
        Product laptop = new Product("P1", "ProBook Laptop", ProductCategory.ELECTRONICS,
                "High-performance 15\" laptop");
        Product phone = new Product("P2", "SmartPhone X", ProductCategory.ELECTRONICS,
                "Flagship smartphone with AMOLED display");
        Product novel = new Product("P3", "The Great Journey", ProductCategory.BOOKS,
                "Bestselling adventure novel");
        Product tshirt = new Product("P4", "Classic Tee", ProductCategory.CLOTHING,
                "100% cotton crew-neck t-shirt");
        Product coffee = new Product("P5", "Dark Roast Beans", ProductCategory.FOOD,
                "Premium single-origin coffee beans");

        service.registerProduct(laptop);
        service.registerProduct(phone);
        service.registerProduct(novel);
        service.registerProduct(tshirt);
        service.registerProduct(coffee);

        System.out.println("Registered products:");
        System.out.println("  " + laptop);
        System.out.println("  " + phone);
        System.out.println("  " + novel);
        System.out.println("  " + tshirt);
        System.out.println("  " + coffee);

        // Users
        User alice = new User("U1", "Alice");
        User bob = new User("U2", "Bob");
        User charlie = new User("U3", "Charlie");
        User diana = new User("U4", "Diana");
        User ethan = new User("U5", "Ethan");

        service.registerUser(alice);
        service.registerUser(bob);
        service.registerUser(charlie);
        service.registerUser(diana);
        service.registerUser(ethan);

        System.out.println("\nRegistered users:");
        System.out.println("  " + alice);
        System.out.println("  " + bob);
        System.out.println("  " + charlie);
        System.out.println("  " + diana);
        System.out.println("  " + ethan);

        // ── EXERCISE PHASE 1: ADD REVIEWS ────────────────────────────

        System.out.println("\n========== ADD REVIEWS ==========\n");

        LocalDateTime now = LocalDateTime.of(2026, 2, 13, 10, 0);

        Review r1 = service.addReview("U1", "P1", 5, "Excellent laptop!",
                "Blazing fast performance and great screen quality.", now.minusDays(10));
        Review r2 = service.addReview("U2", "P1", 4, "Very good, minor issues",
                "Great specs but the fan noise is noticeable under load.", now.minusDays(8));
        Review r3 = service.addReview("U3", "P1", 3, "Average experience",
                "Nothing special for the price point.", now.minusDays(5));
        Review r4 = service.addReview("U4", "P1", 5, "Worth every penny",
                "Best laptop I have ever owned. Superb quality.", now.minusDays(2));
        Review r5 = service.addReview("U5", "P1", 2, "Disappointed",
                "Battery life is poor. Expected more from this brand.", now.minusDays(1));

        Review r6 = service.addReview("U1", "P2", 4, "Great phone",
                "Beautiful display and smooth performance.", now.minusDays(7));
        Review r7 = service.addReview("U2", "P2", 5, "Love it!",
                "Camera quality is outstanding.", now.minusDays(3));

        Review r8 = service.addReview("U1", "P3", 5, "Page-turner",
                "Could not put this book down. Highly recommended!", now.minusDays(6));
        Review r9 = service.addReview("U3", "P3", 4, "Solid read",
                "Engaging story but the ending felt rushed.", now.minusDays(4));

        Review r10 = service.addReview("U2", "P4", 3, "Decent tee",
                "Good fabric but sizing runs small.", now.minusDays(9));

        Review r11 = service.addReview("U4", "P5", 5, "Best coffee ever",
                "Rich aroma and smooth taste. My morning staple.", now.minusDays(3));

        System.out.println("Added 11 reviews across 5 products:");
        System.out.println("  " + r1);
        System.out.println("  " + r2);
        System.out.println("  " + r3);
        System.out.println("  " + r4);
        System.out.println("  " + r5);
        System.out.println("  " + r6);
        System.out.println("  " + r7);
        System.out.println("  " + r8);
        System.out.println("  " + r9);
        System.out.println("  " + r10);
        System.out.println("  " + r11);

        // ── EXERCISE PHASE 2: DUPLICATE REVIEW PREVENTION ────────────

        System.out.println("\n========== DUPLICATE REVIEW PREVENTION ==========\n");

        try {
            service.addReview("U1", "P1", 4, "Another review",
                    "Trying to review again", now);
            System.out.println("ERROR: Should have thrown IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly prevented duplicate review: " + e.getMessage());
        }

        // ── EXERCISE PHASE 3: SORTING REVIEWS ────────────────────────

        System.out.println("\n========== SORTING REVIEWS ==========\n");

        System.out.println("--- ProBook Laptop reviews (MOST RECENT) ---");
        List<Review> recentReviews = service.getProductReviews("P1",
                ReviewSortStrategy.MOST_RECENT, null);
        recentReviews.forEach(r -> System.out.println("  " + r));

        System.out.println("\n--- ProBook Laptop reviews (HIGHEST RATED) ---");
        List<Review> highestReviews = service.getProductReviews("P1",
                ReviewSortStrategy.HIGHEST_RATED, null);
        highestReviews.forEach(r -> System.out.println("  " + r));

        System.out.println("\n--- ProBook Laptop reviews (LOWEST RATED) ---");
        List<Review> lowestReviews = service.getProductReviews("P1",
                ReviewSortStrategy.LOWEST_RATED, null);
        lowestReviews.forEach(r -> System.out.println("  " + r));

        // ── EXERCISE PHASE 4: MARK HELPFUL & SORT ────────────────────

        System.out.println("\n========== HELPFUL VOTES ==========\n");

        service.markReviewHelpful(r1.getId());
        service.markReviewHelpful(r1.getId());
        service.markReviewHelpful(r1.getId()); // 3 votes
        service.markReviewHelpful(r4.getId());
        service.markReviewHelpful(r4.getId()); // 2 votes
        service.markReviewHelpful(r2.getId()); // 1 vote

        System.out.println("Votes assigned: r1=3, r4=2, r2=1");

        System.out.println("\n--- ProBook Laptop reviews (MOST HELPFUL) ---");
        List<Review> helpfulReviews = service.getProductReviews("P1",
                ReviewSortStrategy.MOST_HELPFUL, null);
        helpfulReviews.forEach(r -> System.out.println("  " + r));

        // ── EXERCISE PHASE 5: FILTERING REVIEWS ──────────────────────

        System.out.println("\n========== FILTERING REVIEWS ==========\n");

        // Filter: only 4-5 star reviews
        ReviewFilter highRatingFilter = new ReviewFilter.Builder()
                .minRating(4)
                .build();
        System.out.println("--- ProBook Laptop reviews (4-5 stars only) ---");
        List<Review> filtered1 = service.getProductReviews("P1",
                ReviewSortStrategy.MOST_RECENT, highRatingFilter);
        filtered1.forEach(r -> System.out.println("  " + r));

        // Filter: keyword "quality"
        ReviewFilter keywordFilter = new ReviewFilter.Builder()
                .keyword("quality")
                .build();
        System.out.println("\n--- ProBook Laptop reviews containing 'quality' ---");
        List<Review> filtered2 = service.getProductReviews("P1",
                ReviewSortStrategy.MOST_RECENT, keywordFilter);
        filtered2.forEach(r -> System.out.println("  " + r));

        // Filter: date range
        ReviewFilter dateFilter = new ReviewFilter.Builder()
                .fromDate(now.minusDays(6))
                .toDate(now)
                .build();
        System.out.println("\n--- ProBook Laptop reviews (last 6 days) ---");
        List<Review> filtered3 = service.getProductReviews("P1",
                ReviewSortStrategy.MOST_RECENT, dateFilter);
        filtered3.forEach(r -> System.out.println("  " + r));

        // Combined filter: 3+ stars AND keyword "performance"
        ReviewFilter combinedFilter = new ReviewFilter.Builder()
                .minRating(3)
                .keyword("performance")
                .build();
        System.out.println("\n--- ProBook Laptop reviews (3+ stars, keyword 'performance') ---");
        List<Review> filtered4 = service.getProductReviews("P1",
                ReviewSortStrategy.HIGHEST_RATED, combinedFilter);
        filtered4.forEach(r -> System.out.println("  " + r));

        // ── EXERCISE PHASE 6: RATING STATISTICS ──────────────────────

        System.out.println("\n========== RATING STATISTICS ==========\n");

        RatingStats laptopStats = service.getRatingStats("P1");
        System.out.println(laptopStats);

        System.out.println();

        RatingStats phoneStats = service.getRatingStats("P2");
        System.out.println(phoneStats);

        System.out.println();

        RatingStats novelStats = service.getRatingStats("P3");
        System.out.println(novelStats);

        // ── EXERCISE PHASE 7: TOP-RATED BY CATEGORY ──────────────────

        System.out.println("\n========== TOP-RATED BY CATEGORY ==========\n");

        System.out.println("--- Top 3 ELECTRONICS products ---");
        List<Product> topElectronics = service.getTopRatedProducts(
                ProductCategory.ELECTRONICS, 3);
        for (int i = 0; i < topElectronics.size(); i++) {
            Product p = topElectronics.get(i);
            RatingStats s = service.getRatingStats(p.getId());
            System.out.println("  #%d  %s  (avg: %.2f, %d reviews)"
                    .formatted(i + 1, p.getName(), s.getAverageRating(), s.getTotalReviews()));
        }

        System.out.println("\n--- Top 3 BOOKS products ---");
        List<Product> topBooks = service.getTopRatedProducts(ProductCategory.BOOKS, 3);
        for (int i = 0; i < topBooks.size(); i++) {
            Product p = topBooks.get(i);
            RatingStats s = service.getRatingStats(p.getId());
            System.out.println("  #%d  %s  (avg: %.2f, %d reviews)"
                    .formatted(i + 1, p.getName(), s.getAverageRating(), s.getTotalReviews()));
        }

        // ── EXERCISE PHASE 8: USER'S REVIEWS ─────────────────────────

        System.out.println("\n========== USER'S REVIEWS ==========\n");

        System.out.println("--- Alice's reviews ---");
        List<Review> aliceReviews = service.getUserReviews("U1");
        aliceReviews.forEach(r -> System.out.println("  " + r));

        System.out.println("\n--- Bob's reviews ---");
        List<Review> bobReviews = service.getUserReviews("U2");
        bobReviews.forEach(r -> System.out.println("  " + r));

        // ── VERIFICATION PHASE ───────────────────────────────────────

        System.out.println("\n========== VERIFICATION ==========\n");

        int passed = 0;
        int failed = 0;

        // V1: Total laptop reviews = 5
        if (service.getProductReviews("P1", ReviewSortStrategy.MOST_RECENT, null).size() == 5) {
            System.out.println("  ✓ V1: Laptop has 5 reviews");
            passed++;
        } else {
            System.out.println("  ✗ V1: Laptop review count mismatch");
            failed++;
        }

        // V2: Average laptop rating ≈ 3.80
        double laptopAvg = service.getRatingStats("P1").getAverageRating();
        if (Math.abs(laptopAvg - 3.80) < 0.01) {
            System.out.println("  ✓ V2: Laptop average rating = %.2f".formatted(laptopAvg));
            passed++;
        } else {
            System.out.println("  ✗ V2: Expected avg ~3.80, got %.2f".formatted(laptopAvg));
            failed++;
        }

        // V3: Most helpful review for laptop is r1
        var mostHelpful = service.getRatingStats("P1").getMostHelpfulReview();
        if (mostHelpful.isPresent() && mostHelpful.get().getId().equals(r1.getId())) {
            System.out.println("  ✓ V3: Most helpful review is '%s'".formatted(r1.getTitle()));
            passed++;
        } else {
            System.out.println("  ✗ V3: Most helpful review mismatch");
            failed++;
        }

        // V4: High-rating filter returns 3 reviews (the two 5-star + one 4-star)
        if (filtered1.size() == 3) {
            System.out.println("  ✓ V4: High-rating filter returned 3 reviews");
            passed++;
        } else {
            System.out.println("  ✗ V4: Expected 3 filtered reviews, got " + filtered1.size());
            failed++;
        }

        // V5: Keyword 'quality' filter returns 2 reviews (r1 mentions "quality", r4 mentions "quality")
        if (filtered2.size() == 2) {
            System.out.println("  ✓ V5: Keyword 'quality' filter returned 2 reviews");
            passed++;
        } else {
            System.out.println("  ✗ V5: Expected 2 keyword-filtered reviews, got " + filtered2.size());
            failed++;
        }

        // V6: Sorting by HIGHEST_RATED — first review should be 5-star
        if (highestReviews.get(0).getRating() == 5) {
            System.out.println("  ✓ V6: HIGHEST_RATED sort starts with 5-star review");
            passed++;
        } else {
            System.out.println("  ✗ V6: HIGHEST_RATED sort order incorrect");
            failed++;
        }

        // V7: Sorting by LOWEST_RATED — first review should be 2-star
        if (lowestReviews.get(0).getRating() == 2) {
            System.out.println("  ✓ V7: LOWEST_RATED sort starts with 2-star review");
            passed++;
        } else {
            System.out.println("  ✗ V7: LOWEST_RATED sort order incorrect");
            failed++;
        }

        // V8: Alice has 3 reviews (P1, P2, P3)
        if (aliceReviews.size() == 3) {
            System.out.println("  ✓ V8: Alice has 3 reviews");
            passed++;
        } else {
            System.out.println("  ✗ V8: Expected Alice to have 3 reviews, got " + aliceReviews.size());
            failed++;
        }

        // V9: Top electronics — phone should rank above laptop (4.5 avg > 3.8 avg)
        if (topElectronics.size() >= 2
                && topElectronics.get(0).getId().equals("P2")
                && topElectronics.get(1).getId().equals("P1")) {
            System.out.println("  ✓ V9: Top electronics: SmartPhone X > ProBook Laptop");
            passed++;
        } else {
            System.out.println("  ✗ V9: Top electronics ranking incorrect");
            failed++;
        }

        // V10: Helpful votes on r1 = 3
        if (service.getReview(r1.getId()).getHelpfulVotes() == 3) {
            System.out.println("  ✓ V10: r1 has 3 helpful votes");
            passed++;
        } else {
            System.out.println("  ✗ V10: r1 helpful-vote count mismatch");
            failed++;
        }

        System.out.println("\n  Results: %d passed, %d failed".formatted(passed, failed));

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                         DEMO COMPLETE                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
}