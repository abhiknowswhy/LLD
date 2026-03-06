package questions.lld.Othello;

/**
 * Represents the disc colors in Othello.
 */
public enum Disc {
    BLACK, WHITE;

    public Disc opposite() { return this == BLACK ? WHITE : BLACK; }

    public char symbol() { return this == BLACK ? 'B' : 'W'; }
}
