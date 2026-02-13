package questions.lld.Board;

/**
 * A tic-tac-toe board (variable size, 3-in-a-row to win).
 * Strategy pattern: win-check algorithm is encapsulated in this class.
 */
public class TicTacToeBoard extends Board {
    private final int winLength;

    public TicTacToeBoard(int size) {
        this(size, 3);
    }

    public TicTacToeBoard(int size, int winLength) {
        super(size, size);
        this.winLength = winLength;
    }

    @Override
    public boolean checkWin(String symbol) {
        // Check rows
        for (int i = 0; i < rows; i++) {
            if (checkLine(symbol, i, 0, 0, 1)) return true;
        }
        // Check columns
        for (int j = 0; j < cols; j++) {
            if (checkLine(symbol, 0, j, 1, 0)) return true;
        }
        // Check diagonals
        for (int i = 0; i <= rows - winLength; i++) {
            for (int j = 0; j <= cols - winLength; j++) {
                if (checkLine(symbol, i, j, 1, 1)) return true;
            }
            for (int j = winLength - 1; j < cols; j++) {
                if (checkLine(symbol, i, j, 1, -1)) return true;
            }
        }
        return false;
    }

    private boolean checkLine(String symbol, int startRow, int startCol, int dRow, int dCol) {
        int count = 0;
        int r = startRow, c = startCol;
        while (r >= 0 && r < rows && c >= 0 && c < cols) {
            if (symbol.equals(grid[r][c].getSymbol())) {
                count++;
                if (count >= winLength) return true;
            } else {
                count = 0;
            }
            r += dRow;
            c += dCol;
        }
        return false;
    }
}
