package questions.lld.ElevatorSystem;

import java.util.TreeSet;

/**
 * Represents a single elevator with SCAN (elevator) scheduling.
 *
 * The elevator services floors in its current direction, then reverses
 * when there are no more requests in that direction (like a disk arm).
 */
public class Elevator {

    private final int id;
    private final int maxFloor;
    private int currentFloor;
    private Direction direction;
    private final TreeSet<Integer> upStops = new TreeSet<>();
    private final TreeSet<Integer> downStops = new TreeSet<>();

    public Elevator(int id, int maxFloor) {
        this.id = id;
        this.maxFloor = maxFloor;
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
    }

    public int getId() { return id; }
    public int getCurrentFloor() { return currentFloor; }
    public Direction getDirection() { return direction; }

    /** Adds a destination floor for this elevator. */
    public void addStop(int floor, Direction requestDir) {
        if (floor < 0 || floor >= maxFloor) throw new IllegalArgumentException("Invalid floor: " + floor);
        if (requestDir == Direction.UP) {
            upStops.add(floor);
        } else {
            downStops.add(floor);
        }
        if (direction == Direction.IDLE) {
            direction = (floor >= currentFloor) ? Direction.UP : Direction.DOWN;
        }
    }

    /** Adds an internal destination (passenger pressed a floor button inside). */
    public void addInternalStop(int floor) {
        if (floor > currentFloor) {
            upStops.add(floor);
            if (direction == Direction.IDLE) direction = Direction.UP;
        } else if (floor < currentFloor) {
            downStops.add(floor);
            if (direction == Direction.IDLE) direction = Direction.DOWN;
        }
    }

    /** Returns true if the elevator has no pending stops. */
    public boolean isIdle() {
        return upStops.isEmpty() && downStops.isEmpty();
    }

    /**
     * Advances the elevator by one step (one floor).
     * Opens doors if the current floor is a stop.
     */
    public void step() {
        if (isIdle()) {
            direction = Direction.IDLE;
            return;
        }

        if (direction == Direction.UP) {
            if (!upStops.isEmpty() && upStops.first() >= currentFloor) {
                currentFloor++;
                if (upStops.contains(currentFloor)) {
                    upStops.remove(currentFloor);
                    System.out.println("  Elevator " + id + " opens at floor " + currentFloor + " (UP)");
                }
                if (upStops.isEmpty()) {
                    direction = downStops.isEmpty() ? Direction.IDLE : Direction.DOWN;
                }
            } else {
                direction = downStops.isEmpty() ? Direction.IDLE : Direction.DOWN;
            }
        } else if (direction == Direction.DOWN) {
            if (!downStops.isEmpty() && downStops.last() <= currentFloor) {
                currentFloor--;
                if (downStops.contains(currentFloor)) {
                    downStops.remove(currentFloor);
                    System.out.println("  Elevator " + id + " opens at floor " + currentFloor + " (DOWN)");
                }
                if (downStops.isEmpty()) {
                    direction = upStops.isEmpty() ? Direction.IDLE : Direction.UP;
                }
            } else {
                direction = upStops.isEmpty() ? Direction.IDLE : Direction.UP;
            }
        }
    }

    /** Calculates the cost (distance) to serve a request — used for dispatching. */
    public int costToServe(int floor, Direction requestDir) {
        if (direction == Direction.IDLE) return Math.abs(currentFloor - floor);

        if (direction == Direction.UP) {
            if (requestDir == Direction.UP && floor >= currentFloor) {
                return floor - currentFloor;
            }
            // Must go up to highest, then come down
            int highest = upStops.isEmpty() ? currentFloor : upStops.last();
            return (highest - currentFloor) + (highest - floor);
        } else {
            if (requestDir == Direction.DOWN && floor <= currentFloor) {
                return currentFloor - floor;
            }
            int lowest = downStops.isEmpty() ? currentFloor : downStops.first();
            return (currentFloor - lowest) + (floor - lowest);
        }
    }

    @Override
    public String toString() {
        return "Elevator-" + id + "(floor=" + currentFloor + ", dir=" + direction +
                ", upStops=" + upStops + ", downStops=" + downStops + ")";
    }
}
