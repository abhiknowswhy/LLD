package questions.lld.JigsawGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the jigsaw puzzle board and tracks placed/unplaced pieces.
 */
public class Puzzle {

    private final int rows;
    private final int cols;
    private final Piece[][] board;
    private final List<Piece> unplacedPieces;

    public Puzzle(int rows, int cols, List<Piece> pieces) {
        this.rows = rows;
        this.cols = cols;
        this.board = new Piece[rows][cols];
        this.unplacedPieces = new ArrayList<>(pieces);
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getPieceCount() { return rows * cols; }
    public Piece getPieceAt(int row, int col) { return board[row][col]; }
    public List<Piece> getUnplacedPieces() { return unplacedPieces; }

    /**
     * Attempts to place a piece at the given position.
     * Returns true if the piece fits (edges match neighbors and borders).
     */
    public boolean placePiece(Piece piece, int row, int col) {
        if (board[row][col] != null) return false;
        if (!fitsAt(piece, row, col)) return false;
        board[row][col] = piece;
        unplacedPieces.remove(piece);
        return true;
    }

    /** Removes a piece from the board and returns it to the unplaced list. */
    public void removePiece(int row, int col) {
        Piece piece = board[row][col];
        if (piece != null) {
            board[row][col] = null;
            unplacedPieces.add(piece);
        }
    }

    /** Checks if a piece fits at the given position. */
    public boolean fitsAt(Piece piece, int row, int col) {
        // Top edge: must match bottom of piece above, or FLAT if top border
        if (row == 0) {
            if (piece.getTop().getShape() != EdgeShape.FLAT) return false;
        } else if (board[row - 1][col] != null) {
            if (!piece.getTop().fitsWidth(board[row - 1][col].getBottom())) return false;
        }

        // Bottom edge: must be FLAT if bottom border
        if (row == rows - 1) {
            if (piece.getBottom().getShape() != EdgeShape.FLAT) return false;
        }

        // Left edge: must match right of piece to the left, or FLAT if left border
        if (col == 0) {
            if (piece.getLeft().getShape() != EdgeShape.FLAT) return false;
        } else if (board[row][col - 1] != null) {
            if (!piece.getLeft().fitsWidth(board[row][col - 1].getRight())) return false;
        }

        // Right edge: must be FLAT if right border
        if (col == cols - 1) {
            if (piece.getRight().getShape() != EdgeShape.FLAT) return false;
        }

        return true;
    }

    /** Returns true if all cells on the board are filled. */
    public boolean isSolved() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (board[r][c] == null) return false;
        return true;
    }

    /** Prints the board showing piece IDs. */
    public void printBoard() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Piece p = board[r][c];
                System.out.printf("%4s", p == null ? "." : "P" + p.getId());
            }
            System.out.println();
        }
    }
}
