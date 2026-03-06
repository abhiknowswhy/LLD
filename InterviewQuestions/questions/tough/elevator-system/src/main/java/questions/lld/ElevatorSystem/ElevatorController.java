package questions.lld.ElevatorSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that manages multiple elevators and dispatches requests
 * to the nearest/cheapest elevator.
 *
 * Design:
 * - Strategy: nearest-elevator dispatching
 * - Each elevator uses SCAN (elevator) scheduling internally
 * - Step-based simulation for demonstration
 */
public class ElevatorController {

    private final int numFloors;
    private final List<Elevator> elevators;

    public ElevatorController(int numFloors, int numElevators) {
        if (numFloors <= 0) throw new IllegalArgumentException("Must have at least 1 floor");
        if (numElevators <= 0) throw new IllegalArgumentException("Must have at least 1 elevator");
        this.numFloors = numFloors;
        this.elevators = new ArrayList<>();
        for (int i = 0; i < numElevators; i++) {
            elevators.add(new Elevator(i, numFloors));
        }
    }

    /** Handles an external request (someone pressed a button on a floor). */
    public void requestElevator(int floor, Direction direction) {
        if (floor < 0 || floor >= numFloors) throw new IllegalArgumentException("Invalid floor: " + floor);
        Elevator best = findBestElevator(floor, direction);
        best.addStop(floor, direction);
        System.out.println("  Request floor " + floor + " " + direction + " → assigned to Elevator-" + best.getId());
    }

    /** Handles an internal request (passenger inside elevator presses a floor button). */
    public void pressFloorButton(int elevatorId, int floor) {
        if (elevatorId < 0 || elevatorId >= elevators.size()) throw new IllegalArgumentException("Invalid elevator ID");
        if (floor < 0 || floor >= numFloors) throw new IllegalArgumentException("Invalid floor: " + floor);
        elevators.get(elevatorId).addInternalStop(floor);
        System.out.println("  Elevator-" + elevatorId + " internal button: floor " + floor);
    }

    /** Advances all elevators by one step. */
    public void step() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }

    /** Returns true if all elevators are idle. */
    public boolean isIdle() {
        return elevators.stream().allMatch(Elevator::isIdle);
    }

    /** Prints the current status of all elevators. */
    public void printStatus() {
        for (Elevator elevator : elevators) {
            System.out.println("  " + elevator);
        }
    }

    /** Finds the elevator with the lowest cost to serve the given request. */
    private Elevator findBestElevator(int floor, Direction direction) {
        Elevator best = null;
        int bestCost = Integer.MAX_VALUE;
        for (Elevator elevator : elevators) {
            int cost = elevator.costToServe(floor, direction);
            if (cost < bestCost) {
                bestCost = cost;
                best = elevator;
            }
        }
        return best;
    }
}
