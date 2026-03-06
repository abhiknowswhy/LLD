package questions.lld;

import questions.lld.ElevatorSystem.*;

/**
 * Demonstrates a multi-elevator system with request scheduling.
 *
 * Features:
 * - Multiple elevators with independent state
 * - External requests (floor button) and internal requests (cabin button)
 * - Nearest-elevator dispatching strategy
 * - SCAN (elevator) scheduling algorithm
 * - Step-by-step simulation
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Elevator System Demo ===\n");

        // Building with 10 floors (0-9) and 2 elevators
        ElevatorController controller = new ElevatorController(10, 2);
        controller.printStatus();

        // External requests — people pressing buttons on floors
        System.out.println("\n--- External Requests ---");
        controller.requestElevator(3, Direction.UP);   // someone on floor 3 going up
        controller.requestElevator(7, Direction.DOWN);  // someone on floor 7 going down
        controller.requestElevator(1, Direction.UP);    // someone on floor 1 going up

        // Simulate steps
        System.out.println("\n--- Simulation Steps ---");
        for (int step = 1; step <= 15; step++) {
            System.out.println("\n[Step " + step + "]");
            controller.step();
            controller.printStatus();
            if (controller.isIdle()) {
                System.out.println("All elevators idle.");
                break;
            }
        }

        // Internal request — passenger inside elevator presses floor button
        System.out.println("\n--- Internal Request (passenger presses floor 9) ---");
        controller.pressFloorButton(0, 9); // elevator 0, go to floor 9

        for (int step = 1; step <= 15; step++) {
            System.out.println("\n[Step " + step + "]");
            controller.step();
            controller.printStatus();
            if (controller.isIdle()) {
                System.out.println("All elevators idle.");
                break;
            }
        }

        System.out.println("\n=== Elevator System Demo Complete ===");
    }
}