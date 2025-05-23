package pattern;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GumballMachineTest {
    @Test
    public void testInitialState_NoQuarter() {
        GumballMachine machine = new GumballMachine(5);
        assertEquals(5, machine.getCount());
        assertEquals(machine.getNoQuarterState(), machine.getState());
    }

    @Test
    public void testInsertQuarterAndTurnCrank() {
        GumballMachine machine = new GumballMachine(2);
        machine.insertQuarter();
        machine.turnCrank();
        assertEquals(1, machine.getCount());
    }

    @Test
    public void testEjectQuarter() {
        GumballMachine machine = new GumballMachine(2);
        machine.insertQuarter();
        machine.ejectQuarter();
        assertEquals(machine.getNoQuarterState(), machine.getState());
    }

    @Test
    public void testSoldOutState() {
        GumballMachine machine = new GumballMachine(1);
        machine.insertQuarter();
        machine.turnCrank();
        assertEquals(0, machine.getCount());
        assertEquals(machine.getSoldOutState(), machine.getState());
    }

    @Test
    public void testRefill() {
        GumballMachine machine = new GumballMachine(0);
        assertEquals(machine.getSoldOutState(), machine.getState());
        machine.refill(3);
        assertEquals(3, machine.getCount());
    }
}
