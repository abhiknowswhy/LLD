package questions.lld.ChessGame;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Color color) { super(color); }

    @Override
    public char getSymbol() { return getColor() == Color.WHITE ? 'P' : 'p'; }

    @Override
    public List<Position> getCandidateMoves(Position from, Board board) {
        List<Position> moves = new ArrayList<>();
        int direction = (getColor() == Color.WHITE) ? 1 : -1;
        int startRow = (getColor() == Color.WHITE) ? 1 : 6;
        int r = from.getRow(), c = from.getCol();

        // Single step forward
        int fwd = r + direction;
        if (fwd >= 0 && fwd <= 7 && board.getPiece(new Position(fwd, c)) == null) {
            moves.add(new Position(fwd, c));
            // Double step from starting position
            int fwd2 = r + 2 * direction;
            if (r == startRow && board.getPiece(new Position(fwd2, c)) == null) {
                moves.add(new Position(fwd2, c));
            }
        }

        // Diagonal captures
        for (int dc : new int[]{-1, 1}) {
            int nc = c + dc;
            if (fwd >= 0 && fwd <= 7 && nc >= 0 && nc <= 7) {
                Piece target = board.getPiece(new Position(fwd, nc));
                if (target != null && target.getColor() != getColor()) {
                    moves.add(new Position(fwd, nc));
                }
            }
        }
        return moves;
    }
}
