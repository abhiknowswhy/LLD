package pattern;

public class GumballMonitor {
    GumballMachineWithLocation machine;
    
    public GumballMonitor(GumballMachineWithLocation machine) {
        this.machine = machine;
    }
    
    public void report() {
        System.out.println("Gumball Machine Location: " + machine.getLocation());
        System.out.println("Current inventory: " + machine.getCount() + " gumballs");
        System.out.println("Current state: " + machine.getState());
    }
}
