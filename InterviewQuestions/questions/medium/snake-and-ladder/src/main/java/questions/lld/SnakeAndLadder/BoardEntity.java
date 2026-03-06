package questions.lld.SnakeAndLadder;

/**
 * Represents either a Snake (start > end) or Ladder (start < end).
 */
public class BoardEntity {
    private final int start;
    private final int end;

    public BoardEntity(int start, int end) {
        if (start == end) throw new IllegalArgumentException("Start and end cannot be the same");
        this.start = start;
        this.end = end;
    }

    public int getStart() { return start; }
    public int getEnd() { return end; }
    public boolean isSnake() { return start > end; }
    public boolean isLadder() { return start < end; }

    @Override
    public String toString() {
        return (isSnake() ? "Snake" : "Ladder") + "[" + start + " -> " + end + "]";
    }
}
