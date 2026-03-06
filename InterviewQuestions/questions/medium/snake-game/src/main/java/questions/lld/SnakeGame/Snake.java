package questions.lld.SnakeGame;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

public class Snake {
    private final Deque<Position> body;
    private final Set<Position> bodySet;
    private Direction currentDirection;

    public Snake(Position start) {
        this.body = new LinkedList<>();
        this.bodySet = new HashSet<>();
        this.body.addFirst(start);
        this.bodySet.add(start);
        this.currentDirection = Direction.RIGHT;
    }

    public Position getHead() {
        return body.peekFirst();
    }

    public int getLength() {
        return body.size();
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setDirection(Direction direction) {
        // Prevent 180-degree turn
        if (body.size() > 1 && direction == currentDirection.opposite()) {
            return;
        }
        this.currentDirection = direction;
    }

    /**
     * Moves the snake forward. Returns the new head position.
     * @param grow if true, the tail is not removed (snake grows)
     */
    public Position move(boolean grow) {
        Position newHead = getHead().move(currentDirection);

        if (!grow) {
            Position tail = body.removeLast();
            bodySet.remove(tail);
        }

        // Add new head after removing tail so set stays consistent
        body.addFirst(newHead);
        // If newHead collides with existing body, the set already has it → self-collision
        bodySet.add(newHead);

        return newHead;
    }

    public boolean occupies(Position pos) {
        return bodySet.contains(pos);
    }

    /**
     * Checks if the head collides with the rest of the body.
     * After move, head position may appear more than once in the deque
     * while the set only stores unique positions.
     */
    public boolean isSelfCollision() {
        Position head = getHead();
        int count = 0;
        for (Position p : body) {
            if (p.equals(head)) count++;
        }
        return count > 1;
    }

    public Deque<Position> getBody() {
        return body;
    }
}
