package questions.lld.JigsawGame;

/**
 * Represents a single jigsaw puzzle piece with 4 edges.
 * Supports rotation (0, 90, 180, 270 degrees).
 */
public class Piece {

    private final int id;
    private Edge top, right, bottom, left;
    private int rotation; // 0, 1, 2, 3 (number of 90° clockwise rotations)

    public Piece(int id, Edge top, Edge right, Edge bottom, Edge left) {
        this.id = id;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
        this.rotation = 0;
    }

    public int getId() { return id; }
    public Edge getTop() { return top; }
    public Edge getRight() { return right; }
    public Edge getBottom() { return bottom; }
    public Edge getLeft() { return left; }
    public int getRotation() { return rotation; }

    /** Rotates the piece 90 degrees clockwise. */
    public void rotateClockwise() {
        Edge temp = top;
        top = left;
        left = bottom;
        bottom = right;
        right = temp;
        rotation = (rotation + 1) % 4;
    }

    /** Resets rotation to original orientation. */
    public void resetRotation() {
        while (rotation != 0) rotateClockwise();
    }

    /** Returns true if this piece is a corner piece (exactly 2 flat edges). */
    public boolean isCorner() {
        int flat = countFlat();
        return flat == 2;
    }

    /** Returns true if this piece is a border piece (exactly 1 flat edge). */
    public boolean isBorder() {
        return countFlat() == 1;
    }

    private int countFlat() {
        int count = 0;
        if (top.getShape() == EdgeShape.FLAT) count++;
        if (right.getShape() == EdgeShape.FLAT) count++;
        if (bottom.getShape() == EdgeShape.FLAT) count++;
        if (left.getShape() == EdgeShape.FLAT) count++;
        return count;
    }

    @Override
    public String toString() {
        return "Piece-" + id + "[T=" + top + " R=" + right + " B=" + bottom + " L=" + left + "]";
    }
}
