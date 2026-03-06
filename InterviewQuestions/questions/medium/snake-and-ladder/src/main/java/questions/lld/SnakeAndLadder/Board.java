package questions.lld.SnakeAndLadder;

import java.util.*;

public class Board {
    private final int size;
    private final Map<Integer, BoardEntity> entityMap; // position -> snake or ladder

    public Board(int size) {
        this.size = size;
        this.entityMap = new HashMap<>();
    }

    public void addSnake(int head, int tail) {
        if (head <= tail) throw new IllegalArgumentException("Snake head must be above tail");
        if (head >= size || tail < 1) throw new IllegalArgumentException("Invalid snake position");
        entityMap.put(head, new BoardEntity(head, tail));
    }

    public void addLadder(int bottom, int top) {
        if (bottom >= top) throw new IllegalArgumentException("Ladder bottom must be below top");
        if (top >= size || bottom < 1) throw new IllegalArgumentException("Invalid ladder position");
        entityMap.put(bottom, new BoardEntity(bottom, top));
    }

    /**
     * Returns the final position after applying any snake/ladder at the given position.
     */
    public int getFinalPosition(int position) {
        if (entityMap.containsKey(position)) {
            return entityMap.get(position).getEnd();
        }
        return position;
    }

    public boolean hasEntityAt(int position) {
        return entityMap.containsKey(position);
    }

    public BoardEntity getEntityAt(int position) {
        return entityMap.get(position);
    }

    public int getSize() { return size; }
    public int getWinningPosition() { return size; }
}
