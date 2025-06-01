package pattern;

public class GumballMachineWithLocation extends GumballMachine {
    private String location;

    public GumballMachineWithLocation(String location, int numberGumballs) {
        super(numberGumballs);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("\nMighty Gumball, Inc.");
        result.append("\nJava-enabled Standing Gumball Model #2004");
        result.append("\nLocation: " + location);
        result.append("\nInventory: " + getCount() + " gumball");
        if (getCount() != 1) {
            result.append("s");
        }
        result.append("\n");
        result.append("Machine is " + getState() + "\n");
        return result.toString();
    }
}
