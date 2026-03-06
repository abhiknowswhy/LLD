package questions.lld.ElevatorSystem;

/**
 * Represents an external elevator request (someone pressing a button on a floor).
 */
public record ElevatorRequest(int floor, Direction direction) {

    public ElevatorRequest {
        if (floor < 0) throw new IllegalArgumentException("Floor must be non-negative");
        if (direction == Direction.IDLE) throw new IllegalArgumentException("Request direction cannot be IDLE");
    }

    @Override
    public String toString() { return "Request(floor=" + floor + ", " + direction + ")"; }
}
