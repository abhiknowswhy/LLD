package questions.lld.Othello;

import java.util.ArrayList;
import java.util.List;

/**
 * The 8x8 Othello game board.
 * Handles disc placement, flipping, and move validation.
 */
public class Board {

    private static final int SIZE = 8;
    private static final int[][] DIRECTIONS = {
        {-1, -1}, {-1, 0}, {-1, 1},
        { 0, -1},          { 0, 1},
        { 1, -1}, { 1, 0}, { 1, 1}
    };

    private final Disc[][] grid = new Disc[SIZE][SIZE];

    /** Creates a board with the standard starting position. */
    public Board() {
        grid[3][3] = Disc.WHITE;
        grid[3][4] = Disc.BLACK;
        grid[4][3] = Disc.BLACK;
        grid[4][4] = Disc.WHITE;
    }

    public Disc getDisc(int row, int col) { return grid[row][col]; }

    /**
     * Places a disc and flips captured opponent discs.
     * Returns the number of discs flipped, or 0 if the move is invalid.
     */
    public int placeDisc(int row, int col, Disc disc) {
        if (!isValidPosition(row, col) || grid[row][col] != null) return 0;

        List<int[]> toFlip = getDiscsToFlip(row, col, disc);
        if (toFlip.isEmpty()) return 0;

        grid[row][col] = disc;
        for (int[] pos : toFlip) {
            grid[pos[0]][pos[1]] = disc;
        }
        return toFlip.size();
    }

    /** Returns all valid move positions for the given disc color. */
    public List<int[]> getValidMoves(Disc disc) {
        List<int[]> moves = new ArrayList<>();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (grid[r][c] == null && !getDiscsToFlip(r, c, disc).isEmpty()) {
                    moves.add(new int[]{r, c});
                }
            }
        }
        return moves;
    }

    /** Counts discs of the given color. */
    public int count(Disc disc) {
        int count = 0;
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                if (grid[r][c] == disc) count++;
        return count;
    }

    /** Returns true if the board is completely filled. */
    public boolean isFull() {
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                if (grid[r][c] == null) return false;
        return true;
    }

    /** Prints the board. */
    public void print() {
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int r = 0; r < SIZE; r++) {
            System.out.print(r + " ");
            for (int c = 0; c < SIZE; c++) {
                Disc d = grid[r][c];
                System.out.print((d == null ? "." : d.symbol()) + " ");
            }
            System.out.println();
        }
    }

    // --- Private helpers ---

    private List<int[]> getDiscsToFlip(int row, int col, Disc disc) {
        List<int[]> allFlips = new ArrayList<>();
        for (int[] dir : DIRECTIONS) {
            List<int[]> lineFlips = new ArrayList<>();
            int r = row + dir[0], c = col + dir[1];
            while (isValidPosition(r, c) && grid[r][c] == disc.opposite()) {
                lineFlips.add(new int[]{r, c});
                r += dir[0];
                c += dir[1];
            }
            // Must terminate with own disc to be a valid capture
            if (!lineFlips.isEmpty() && isValidPosition(r, c) && grid[r][c] == disc) {
                allFlips.addAll(lineFlips);
            }
        }
        return allFlips;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }
}
