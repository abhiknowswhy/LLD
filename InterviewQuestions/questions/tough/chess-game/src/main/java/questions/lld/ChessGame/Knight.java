package questions.lld.ChessGame;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(Color color) { super(color); }

    @Override
    public char getSymbol() { return getColor() == Color.WHITE ? 'N' : 'n'; }

    @Override
    public List<Position> getCandidateMoves(Position from, Board board) {
        List<Position> moves = new ArrayList<>();
        int[][] offsets = {{-2,-1},{-2,1},{-1,-2},{-1,2},{1,-2},{1,2},{2,-1},{2,1}};
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
