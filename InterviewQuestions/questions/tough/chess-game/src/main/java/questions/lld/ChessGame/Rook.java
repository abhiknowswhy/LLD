package questions.lld.ChessGame;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(Color color) { super(color); }

    @Override
    public char getSymbol() { return getColor() == Color.WHITE ? 'R' : 'r'; }

    @Override
    public List<Position> getCandidateMoves(Position from, Board board) {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] d : directions) addSlidingMoves(moves, from, board, d[0], d[1]);
        return moves;
    }
}
