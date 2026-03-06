package questions.lld.ChessGame;

/**
 * Represents the 8x8 chess board.
 * Row 0 = rank 1 (white side), Row 7 = rank 8 (black side).
 * Col 0 = a-file, Col 7 = h-file.
 */
public class Board {

    private final Piece[][] grid = new Piece[8][8];

    /** Creates a board with the standard starting position. */
    public Board() {
        setupBackRank(0, Color.WHITE);
        setupPawns(1, Color.WHITE);
        setupPawns(6, Color.BLACK);
        setupBackRank(7, Color.BLACK);
    }

    private void setupBackRank(int row, Color color) {
        grid[row][0] = new Rook(color);
        grid[row][1] = new Knight(color);
        grid[row][2] = new Bishop(color);
        grid[row][3] = new Queen(color);
        grid[row][4] = new King(color);
        grid[row][5] = new Bishop(color);
        grid[row][6] = new Knight(color);
        grid[row][7] = new Rook(color);
    }

    private void setupPawns(int row, Color color) {
        for (int c = 0; c < 8; c++) grid[row][c] = new Pawn(color);
    }

    public Piece getPiece(Position pos) { return grid[pos.getRow()][pos.getCol()]; }

    public void setPiece(Position pos, Piece piece) { grid[pos.getRow()][pos.getCol()] = piece; }

    /** Moves a piece from 'from' to 'to'. Returns captured piece or null. */
    public Piece movePiece(Position from, Position to) {
        Piece captured = grid[to.getRow()][to.getCol()];
        grid[to.getRow()][to.getCol()] = grid[from.getRow()][from.getCol()];
        grid[from.getRow()][from.getCol()] = null;
        return captured;
    }

    /** Finds the position of the king of the given color. */
    public Position findKing(Color color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p instanceof King && p.getColor() == color) return new Position(r, c);
            }
        }
        throw new IllegalStateException("King not found for " + color);
    }

    /** Returns true if the given color's king is in check. */
    public boolean isInCheck(Color color) {
        Position kingPos = findKing(color);
        Color opponent = color.opposite();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p != null && p.getColor() == opponent) {
                    Position from = new Position(r, c);
                    if (p.getCandidateMoves(from, this).contains(kingPos)) return true;
                }
            }
        }
        return false;
    }

    /** Prints the board from black's perspective (rank 8 on top). */
    public void print() {
        System.out.println("  a b c d e f g h");
        for (int r = 7; r >= 0; r--) {
            System.out.print((r + 1) + " ");
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                System.out.print((p == null ? "." : p.getSymbol()) + " ");
            }
            System.out.println(r + 1);
        }
        System.out.println("  a b c d e f g h");
    }
}
