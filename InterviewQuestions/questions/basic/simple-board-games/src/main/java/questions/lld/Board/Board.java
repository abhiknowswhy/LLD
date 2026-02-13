package questions.lld.Board;

/**
 * Abstract base class for all boards.
 * Handles grid initialization, display, validation, and move placement.
 * Concrete boards implement win-condition checking.
 */
public abstract class Board {
    protected final int rows;
    protected final int cols;
    protected final Cell[][] grid;

    protected Board(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Board dimensions must be positive");
        }
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[rows][cols];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * Validate that a move can be placed at (row, col).
     */
    public boolean validateMove(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && grid[row][col].isEmpty();
    }

    /**
     * Place a symbol on the board. Returns true if successful.
     */
    public boolean placeMove(int row, int col, String symbol) {
        if (!validateMove(row, col)) return false;
        grid[row][col].setSymbol(symbol);
        return true;
    }

    /**
     * Clear a cell (for undo).
     */
    public void clearCell(int row, int col) {
        validateCoordinates(row, col);
        grid[row][col].clear();
    }

    /**
     * Check if the given symbol has won. Implemented by concrete boards.
     */
    public abstract boolean checkWin(String symbol);

    /**
     * Check if the board is completely filled.
     */
    public boolean isFull() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j].isEmpty()) return false;
            }
        }
        return true;
    }

    public void resetBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].clear();
            }
        }
    }

    public void displayBoard() {
        // Column headers
        System.out.print("  ");
        for (int j = 0; j < cols; j++) {
            System.out.print(" " + j + " ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < cols; j++) {
                System.out.print("[" + grid[i][j] + "]");
            }
            System.out.println();
        }
        System.out.println();
    }

    public Cell getCell(int row, int col) {
        validateCoordinates(row, col);
        return grid[row][col];
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    protected void validateCoordinates(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Invalid coordinates: (" + row + ", " + col + ")");
        }
    }
}
