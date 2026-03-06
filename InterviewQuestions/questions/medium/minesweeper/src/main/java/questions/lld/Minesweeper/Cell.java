package questions.lld.Minesweeper;

public class Cell {
    private boolean mine;
    private CellState state;
    private int adjacentMines;

    public Cell() {
        this.mine = false;
        this.state = CellState.HIDDEN;
        this.adjacentMines = 0;
    }

    public boolean isMine() { return mine; }
    public void setMine(boolean mine) { this.mine = mine; }
    public CellState getState() { return state; }
    public void setState(CellState state) { this.state = state; }
    public int getAdjacentMines() { return adjacentMines; }
    public void setAdjacentMines(int count) { this.adjacentMines = count; }

    public boolean isRevealed() { return state == CellState.REVEALED; }
    public boolean isFlagged() { return state == CellState.FLAGGED; }
    public boolean isHidden() { return state == CellState.HIDDEN; }

    public char getDisplay() {
        return switch (state) {
            case HIDDEN -> '.';
            case FLAGGED -> 'F';
            case REVEALED -> mine ? '*' : (adjacentMines == 0 ? ' ' : (char) ('0' + adjacentMines));
        };
    }
}
