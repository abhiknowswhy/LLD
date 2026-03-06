package questions.lld.JigsawGame;

import java.util.List;

/**
 * Solves a jigsaw puzzle using backtracking.
 * Tries each unplaced piece in each rotation at the next empty cell.
 */
public class PuzzleSolver {

    private final Puzzle puzzle;

    public PuzzleSolver(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    /** Attempts to solve the puzzle. Returns true if a solution is found. */
    public boolean solve() {
        return backtrack(0, 0);
    }

    private boolean backtrack(int row, int col) {
        if (row == puzzle.getRows()) return true; // all rows filled

        int nextRow = (col + 1 == puzzle.getCols()) ? row + 1 : row;
        int nextCol = (col + 1 == puzzle.getCols()) ? 0 : col + 1;

        List<Piece> candidates = List.copyOf(puzzle.getUnplacedPieces());
        for (Piece piece : candidates) {
            for (int rot = 0; rot < 4; rot++) {
                if (puzzle.placePiece(piece, row, col)) {
                    if (backtrack(nextRow, nextCol)) return true;
                    puzzle.removePiece(row, col);
                }
                piece.rotateClockwise();
            }
        }
        return false;
    }
}
