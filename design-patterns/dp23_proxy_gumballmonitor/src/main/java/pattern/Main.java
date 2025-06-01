package pattern;

public class Main {
    public static void main(String[] args) {
        // Create gumball machine with a specific location and inventory
        GumballMachineWithLocation gumballMachine = new GumballMachineWithLocation("Seattle", 5);
        
        // Create a monitor for this machine
        GumballMonitor monitor = new GumballMonitor(gumballMachine);

        System.out.println("\nInitial state:");
        monitor.report();

        // Test the machine by inserting quarters and turning crank
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        System.out.println("\nAfter selling one gumball:");
        monitor.report();

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        System.out.println("\nAfter selling more gumballs:");
        monitor.report();
    }
}