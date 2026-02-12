package questions.lld.Board;

import questions.lld.Player.Symbol;

public class Board {
    private final Cell[][] grid;
    private final int size;

    public Board(int size) {
        this.size = size;
        this.grid = new Cell[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Cell getCell(int row, int col) {
        validateCoordinates(row, col);
        return grid[row][col];
    }

    public boolean isCellEmpty(int row, int col) {
        validateCoordinates(row, col);
        return grid[row][col].isEmpty();
    }

    public void placeSymbol(int row, int col, Symbol symbol) {
        validateCoordinates(row, col);
        if (!isCellEmpty(row, col)) {
            throw new IllegalArgumentException("Cell (" + row + ", " + col + ") is already occupied");
        }
        grid[row][col].setSymbol(symbol);
    }

    public void clearCell(int row, int col) {
        validateCoordinates(row, col);
        grid[row][col].setSymbol(Symbol.EMPTY);
    }

    public boolean checkWin(Symbol symbol) {
        // Check rows
        for (int i = 0; i < size; i++) {
            if (checkRowWin(i, symbol)) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < size; j++) {
            if (checkColWin(j, symbol)) {
                return true;
            }
        }

        // Check diagonals
        if (checkDiagonalWin(symbol) || checkAntiDiagonalWin(symbol)) {
            return true;
        }

        return false;
    }

    private boolean checkRowWin(int row, Symbol symbol) {
        for (int col = 0; col <= size - 3; col++) {
            boolean win = true;
            for (int k = 0; k < 3; k++) {
                if (grid[row][col + k].getSymbol() != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }
        return false;
    }

    private boolean checkColWin(int col, Symbol symbol) {
        for (int row = 0; row <= size - 3; row++) {
            boolean win = true;
            for (int k = 0; k < 3; k++) {
                if (grid[row + k][col].getSymbol() != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }
        return false;
    }

    private boolean checkDiagonalWin(Symbol symbol) {
        // Check all diagonals that can have 3 consecutive cells
        for (int row = 0; row <= size - 3; row++) {
            for (int col = 0; col <= size - 3; col++) {
                boolean win = true;
                for (int k = 0; k < 3; k++) {
                    if (grid[row + k][col + k].getSymbol() != symbol) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        return false;
    }

    private boolean checkAntiDiagonalWin(Symbol symbol) {
        // Check all anti-diagonals that can have 3 consecutive cells
        for (int row = 0; row <= size - 3; row++) {
            for (int col = 2; col < size; col++) {
                boolean win = true;
                for (int k = 0; k < 3; k++) {
                    if (grid[row + k][col - k].getSymbol() != symbol) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        return false;
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void validateCoordinates(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Coordinates out of bounds: (" + row + ", " + col + ")");
        }
    }

    public void reset() {
        initializeBoard();
    }

    public void display() {
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(" " + grid[i][j] + " ");
                if (j < size - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < size - 1) {
                System.out.println("-----------");
            }
        }
        System.out.println();
    }
}
