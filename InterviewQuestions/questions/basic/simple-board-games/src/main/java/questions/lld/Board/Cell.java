package questions.lld.Board;

/**
 * Represents a single cell on a board.
 * Uses Flyweight pattern — cells are shared/reused and only hold
 * their coordinate and current state (symbol).
 */
public class Cell {
    private final int row;
    private final int col;
    private String symbol; // null or empty = unoccupied

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.symbol = null;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public boolean isEmpty() { return symbol == null || symbol.isEmpty(); }

    public void clear() { this.symbol = null; }

    @Override
    public String toString() {
        return isEmpty() ? "." : symbol;
    }
}
