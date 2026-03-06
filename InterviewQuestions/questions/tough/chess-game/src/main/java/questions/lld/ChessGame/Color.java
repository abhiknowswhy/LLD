package questions.lld.ChessGame;

/**
 * The two sides in a chess game.
 */
public enum Color {
    WHITE, BLACK;

    public Color opposite() { return this == WHITE ? BLACK : WHITE; }
}
