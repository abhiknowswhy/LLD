package questions.lld.Board;

/**
 * Connect Four board — fixed 6x7, gravity-based (pieces fall to lowest empty row).
 * 4-in-a-row to win.
 */
public class ConnectFourBoard extends Board {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final int WIN_LENGTH = 4;

    public ConnectFourBoard() {
        super(ROWS, COLS);
    }

    /**
     * In Connect Four, you don't choose a row — you drop into a column.
     * This finds the lowest empty row in the given column.
     * Returns the row index, or -1 if the column is full.
     */
    public int dropPiece(int col, String symbol) {
        if (col < 0 || col >= COLS) return -1;

        for (int row = ROWS - 1; row >= 0; row--) {
            if (grid[row][col].isEmpty()) {
                grid[row][col].setSymbol(symbol);
                return row;
            }
        }
        return -1; // column full
    }

    @Override
    public boolean validateMove(int row, int col) {
        // For Connect Four, only column matters — piece always drops to bottom
        return col >= 0 && col < COLS && grid[0][col].isEmpty();
    }

    @Override
    public boolean checkWin(String symbol) {
        // Horizontal
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j <= COLS - WIN_LENGTH; j++) {
                if (checkDirection(symbol, i, j, 0, 1)) return true;
            }
        }
        // Vertical
        for (int i = 0; i <= ROWS - WIN_LENGTH; i++) {
            for (int j = 0; j < COLS; j++) {
                if (checkDirection(symbol, i, j, 1, 0)) return true;
            }
        }
        // Diagonal down-right
        for (int i = 0; i <= ROWS - WIN_LENGTH; i++) {
            for (int j = 0; j <= COLS - WIN_LENGTH; j++) {
                if (checkDirection(symbol, i, j, 1, 1)) return true;
            }
        }
        // Diagonal down-left
        for (int i = 0; i <= ROWS - WIN_LENGTH; i++) {
            for (int j = WIN_LENGTH - 1; j < COLS; j++) {
                if (checkDirection(symbol, i, j, 1, -1)) return true;
            }
        }
        return false;
    }

    private boolean checkDirection(String symbol, int row, int col, int dRow, int dCol) {
        for (int k = 0; k < WIN_LENGTH; k++) {
            if (!symbol.equals(grid[row + k * dRow][col + k * dCol].getSymbol())) {
                return false;
            }
        }
        return true;
    }
}
