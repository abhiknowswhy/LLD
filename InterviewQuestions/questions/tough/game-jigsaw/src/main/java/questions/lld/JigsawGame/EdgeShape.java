package questions.lld.JigsawGame;

/**
 * Shape of a puzzle piece edge.
 * FLAT edges go on the border of the puzzle.
 * IN (concave) and OUT (convex) edges must pair with each other.
 */
public enum EdgeShape {
    FLAT,
    IN,   // concave — accepts a convex edge
    OUT;  // convex — fits into a concave edge

    /** Returns the complementary shape. FLAT pairs with FLAT, IN pairs with OUT. */
    public EdgeShape complement() {
        return switch (this) {
            case FLAT -> FLAT;
            case IN -> OUT;
            case OUT -> IN;
        };
    }

    public boolean matches(EdgeShape other) {
        return this.complement() == other;
    }
}
