package questions.lld.ChessGame;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Color color) { super(color); }

    @Override
    public char getSymbol() { return getColor() == Color.WHITE ? 'K' : 'k'; }

    @Override
    public List<Position> getCandidateMoves(Position from, Board board) {
        List<Position> moves = new ArrayList<>();
        int[][] offsets = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        for (int[] d : offsets) {
            int r = from.getRow() + d[0], c = from.getCol() + d[1];
            if (r >= 0 && r <= 7 && c >= 0 && c <= 7) {
                Position target = new Position(r, c);
                Piece occupant = board.getPiece(target);
                if (occupant == null || occupant.getColor() != getColor()) {
                    moves.add(target);
                }
            }
        }
        return moves;
    }
}
