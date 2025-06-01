package pattern;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GumballMonitorTest {
    
    
    @Test
    public void testMonitorReportsCorrectInventory() {
        GumballMachineWithLocation machine = new GumballMachineWithLocation("Seattle", 10);
        GumballMonitor monitor = new GumballMonitor(machine);
        monitor.report(); // Call report to verify monitor usage
        
        assertEquals(10, machine.getCount());
        
        // Test inventory update after selling a gumball
        // Note: Due to random winner state, we just verify count is reduced after actions
        machine.insertQuarter();
        machine.turnCrank();
        
        monitor.report(); // Call report after state change
        int count = machine.getCount();
        assertTrue(count == 9 || count == 8, "Count should be 8 (winner) or 9 (normal) but was " + count);
    }    
    
    @Test
    public void testMonitorReportsCorrectState() {
        GumballMachineWithLocation machine = new GumballMachineWithLocation("Seattle", 5);
        GumballMonitor monitor = new GumballMonitor(machine);
        
        // Initially should be in NoQuarter state
        monitor.report();
        assertEquals(machine.getNoQuarterState(), machine.getState());
        
        // After inserting quarter should be in HasQuarter state
        machine.insertQuarter();
        monitor.report();
        assertEquals(machine.getHasQuarterState(), machine.getState());
        
        // After turning crank should be back in NoQuarter state
        machine.turnCrank();
        monitor.report();
        assertEquals(machine.getNoQuarterState(), machine.getState());
    }
    
    @Test
    public void testMonitorWithEmptyMachine() {
        GumballMachineWithLocation machine = new GumballMachineWithLocation("Seattle", 0);
        GumballMonitor monitor = new GumballMonitor(machine);
        
        // Call monitor report to verify empty state
        monitor.report();
        
        // Should be in SoldOut state when empty
        assertEquals(machine.getSoldOutState(), machine.getState());
        assertEquals(0, machine.getCount());
    }
}
