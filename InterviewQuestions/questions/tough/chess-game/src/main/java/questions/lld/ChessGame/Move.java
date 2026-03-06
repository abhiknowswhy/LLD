package questions.lld.ChessGame;

/**
 * Records a single move in the chess game.
 */
public record Move(Position from, Position to, Piece pieceMoved, Piece pieceCaptured) {

    @Override
    public String toString() {
        String capture = pieceCaptured != null ? "x" + pieceCaptured.getSymbol() : "";
        return pieceMoved.getSymbol() + " " + from + "→" + to + capture;
    }
}
