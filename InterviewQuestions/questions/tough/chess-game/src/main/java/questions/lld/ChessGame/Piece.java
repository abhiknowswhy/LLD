package questions.lld.ChessGame;

import java.util.List;

/**
 * Abstract base class for all chess pieces.
 * Each piece knows its color and can generate a list of candidate moves.
 */
public abstract class Piece {

    private final Color color;

    protected Piece(Color color) {
        this.color = color;
    }

    public Color getColor() { return color; }

    /** Returns the single-character symbol for this piece (uppercase=white, lowercase=black). */
    public abstract char getSymbol();

    /**
     * Returns all candidate destination positions this piece can move to
     * from the given position on the given board.
     * Does NOT filter for putting own king in check — that is done at the Game level.
     */
    public abstract List<Position> getCandidateMoves(Position from, Board board);

    @Override
    public String toString() {
        return String.valueOf(getSymbol());
    }

    // --- Helper for sliding pieces (rook, bishop, queen) ---

    protected void addSlidingMoves(List<Position> moves, Position from, Board board, int dRow, int dCol) {
        int r = from.getRow() + dRow;
        int c = from.getCol() + dCol;
        while (r >= 0 && r <= 7 && c >= 0 && c <= 7) {
            Position target = new Position(r, c);
            Piece occupant = board.getPiece(target);
            if (occupant == null) {
                moves.add(target);
            } else {
                if (occupant.getColor() != this.color) moves.add(target); // capture
                break; // blocked
            }
            r += dRow;
            c += dCol;
        }
    }
}
