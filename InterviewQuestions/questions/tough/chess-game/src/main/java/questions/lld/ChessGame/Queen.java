package questions.lld.ChessGame;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(Color color) { super(color); }

    @Override
    public char getSymbol() { return getColor() == Color.WHITE ? 'Q' : 'q'; }

    @Override
    public List<Position> getCandidateMoves(Position from, Board board) {
        List<Position> moves = new ArrayList<>();
        // Rook directions + Bishop directions
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}};
        for (int[] d : directions) addSlidingMoves(moves, from, board, d[0], d[1]);
        return moves;
    }
}
