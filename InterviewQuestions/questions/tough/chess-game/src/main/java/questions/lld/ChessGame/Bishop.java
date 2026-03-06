package questions.lld.ChessGame;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Color color) { super(color); }

    @Override
    public char getSymbol() { return getColor() == Color.WHITE ? 'B' : 'b'; }

    @Override
    public List<Position> getCandidateMoves(Position from, Board board) {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {{-1,-1},{-1,1},{1,-1},{1,1}};
        for (int[] d : directions) addSlidingMoves(moves, from, board, d[0], d[1]);
        return moves;
    }
}
