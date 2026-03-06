package questions.lld.Minesweeper;

import java.util.*;

public class Board {
    private final int rows;
    private final int cols;
    private final int totalMines;
    private final Cell[][] grid;
    private GameStatus status;
    private int revealedCount;
    private int flagCount;

    public Board(int rows, int cols, int totalMines) {
        if (totalMines >= rows * cols) {
            throw new IllegalArgumentException("Too many mines for the board size");
        }
        this.rows = rows;
        this.cols = cols;
        this.totalMines = totalMines;
        this.grid = new Cell[rows][cols];
        this.status = GameStatus.IN_PROGRESS;
        this.revealedCount = 0;
        this.flagCount = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell();
            }
        }
    }

    /**
     * Places mines randomly, avoiding the first-click cell.
     */
    public void placeMines(int safeRow, int safeCol) {
        Random random = new Random();
        int placed = 0;
        while (placed < totalMines) {
            int r = random.nextInt(rows);
            int c = random.nextInt(cols);
            if ((r == safeRow && c == safeCol) || grid[r][c].isMine()) continue;
            grid[r][c].setMine(true);
            placed++;
        }
        calculateAdjacentMines();
    }

    /**
     * Places mines at specific positions (for testing).
     */
    public void placeMinesAt(int[][] positions) {
        for (int[] pos : positions) {
            grid[pos[0]][pos[1]].setMine(true);
        }
        calculateAdjacentMines();
    }

    private void calculateAdjacentMines() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!grid[r][c].isMine()) {
                    int count = 0;
                    for (int[] neighbor : getNeighbors(r, c)) {
                        if (grid[neighbor[0]][neighbor[1]].isMine()) count++;
                    }
                    grid[r][c].setAdjacentMines(count);
                }
            }
        }
    }

    /**
     * Reveals a cell. If it's a mine, game over. If it has 0 adjacent mines,
     * flood-fills to reveal all connected empty cells.
     */
    public GameStatus reveal(int row, int col) {
        if (status != GameStatus.IN_PROGRESS) return status;
        if (!isValid(row, col)) return status;

        Cell cell = grid[row][col];
        if (cell.isRevealed() || cell.isFlagged()) return status;

        cell.setState(CellState.REVEALED);
        revealedCount++;

        if (cell.isMine()) {
            status = GameStatus.LOST;
            revealAllMines();
            return status;
        }

        // Flood fill for empty cells
        if (cell.getAdjacentMines() == 0) {
            for (int[] neighbor : getNeighbors(row, col)) {
                reveal(neighbor[0], neighbor[1]);
            }
        }

        checkWin();
        return status;
    }

    /**
     * Toggles a flag on a hidden cell.
     */
    public void toggleFlag(int row, int col) {
        if (status != GameStatus.IN_PROGRESS) return;
        if (!isValid(row, col)) return;

        Cell cell = grid[row][col];
        if (cell.isRevealed()) return;

        if (cell.isFlagged()) {
            cell.setState(CellState.HIDDEN);
            flagCount--;
        } else {
            cell.setState(CellState.FLAGGED);
            flagCount++;
        }
    }

    private void checkWin() {
        // Win when all non-mine cells are revealed
        if (revealedCount == (rows * cols - totalMines)) {
            status = GameStatus.WON;
        }
    }

    private void revealAllMines() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c].isMine()) {
                    grid[r][c].setState(CellState.REVEALED);
                }
            }
        }
    }

    private List<int[]> getNeighbors(int row, int col) {
        List<int[]> neighbors = new ArrayList<>();
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = row + dr, nc = col + dc;
                if (isValid(nr, nc)) {
                    neighbors.add(new int[]{nr, nc});
                }
            }
        }
        return neighbors;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public Cell getCell(int row, int col) { return grid[row][col]; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public int getTotalMines() { return totalMines; }
    public GameStatus getStatus() { return status; }
    public int getFlagCount() { return flagCount; }
    public int getRevealedCount() { return revealedCount; }

    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int c = 0; c < cols; c++) sb.append(c);
        sb.append('\n');

        for (int r = 0; r < rows; r++) {
            sb.append(r).append(' ');
            for (int c = 0; c < cols; c++) {
                sb.append(grid[r][c].getDisplay());
            }
            sb.append('\n');
        }
        sb.append("Flags: ").append(flagCount).append("/").append(totalMines);
        return sb.toString();
    }
}
