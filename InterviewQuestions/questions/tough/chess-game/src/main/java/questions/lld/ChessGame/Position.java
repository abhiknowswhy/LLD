package questions.lld.ChessGame;

/**
 * Represents a position on the 8x8 chess board.
 * Row 0 = White's back rank, Row 7 = Black's back rank.
 * Col 0 = a-file, Col 7 = h-file.
 */
public class Position {

    private final int row;
    private final int col;

    public Position(int row, int col) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            throw new IllegalArgumentException("Position out of bounds: (" + row + "," + col + ")");
        }
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    /** Returns algebraic notation like "e4". */
    public String toAlgebraic() {
        return "" + (char) ('a' + col) + (row + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position other)) return false;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() { return row * 8 + col; }

    @Override
    public String toString() { return toAlgebraic(); }
}
