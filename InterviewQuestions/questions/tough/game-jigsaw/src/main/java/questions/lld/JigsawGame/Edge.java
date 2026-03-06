package questions.lld.JigsawGame;

/**
 * Represents a single edge of a puzzle piece with a shape and a unique ID
 * for matching (edges with complementary shapes and same matchId pair together).
 */
public class Edge {

    private final EdgeShape shape;
    private final int matchId; // edges that should connect share the same matchId

    public Edge(EdgeShape shape, int matchId) {
        this.shape = shape;
        this.matchId = matchId;
    }

    public EdgeShape getShape() { return shape; }
    public int getMatchId() { return matchId; }

    /** Returns true if this edge can connect to the other edge. */
    public boolean fitsWidth(Edge other) {
        if (shape == EdgeShape.FLAT && other.shape == EdgeShape.FLAT) return true;
        return shape.matches(other.shape) && matchId == other.matchId;
    }

    @Override
    public String toString() {
        return shape == EdgeShape.FLAT ? "F" : (shape == EdgeShape.IN ? "I" : "O") + matchId;
    }
}
