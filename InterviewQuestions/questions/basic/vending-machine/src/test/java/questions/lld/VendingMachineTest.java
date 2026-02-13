package questions.lld;

import questions.lld.VendingMachine.Product;
import questions.lld.VendingMachine.VendingMachine;
import questions.lld.VendingMachine.State.IdleState;
import questions.lld.VendingMachine.State.HasMoneyState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

    private VendingMachine vm;

    @BeforeEach
    void setUp() {
        vm = new VendingMachine(2, 2);
        vm.stockProduct("A1", new Product("Cola", 1.50), 3);
        vm.stockProduct("A2", new Product("Water", 1.00), 5);
        vm.stockProduct("B1", new Product("Chips", 2.00), 2);
    }

    @Test
    void testInitialState() {
        assertTrue(vm.getState() instanceof IdleState);
        assertEquals(0, vm.getBalance(), 0.001);
    }

    @Test
    void testInsertMoney() {
        vm.insertMoney(2.00);
        assertEquals(2.00, vm.getBalance(), 0.001);
        assertTrue(vm.getState() instanceof HasMoneyState);
    }

    @Test
    void testInsertMultipleTimes() {
        vm.insertMoney(1.00);
        vm.insertMoney(0.50);
        assertEquals(1.50, vm.getBalance(), 0.001);
    }

    @Test
    void testSuccessfulPurchase() {
        vm.insertMoney(2.00);
        vm.selectProduct("A1"); // Cola $1.50
        // After purchase: state back to idle, balance reset
        assertTrue(vm.getState() instanceof IdleState);
        assertEquals(0, vm.getBalance(), 0.001);
    }

    @Test
    void testQuantityDecreases() {
        assertEquals(3, vm.getSlot("A1").getQuantity());
        vm.insertMoney(2.00);
        vm.selectProduct("A1");
        assertEquals(2, vm.getSlot("A1").getQuantity());
    }

    @Test
    void testInsufficientFunds() {
        vm.insertMoney(1.00);
        vm.selectProduct("B1"); // Chips $2.00 — not enough
        // Should still be in HasMoneyState
        assertTrue(vm.getState() instanceof HasMoneyState);
        assertEquals(1.00, vm.getBalance(), 0.001);
    }

    @Test
    void testCancelTransaction() {
        vm.insertMoney(3.00);
        vm.cancelTransaction();
        assertTrue(vm.getState() instanceof IdleState);
        assertEquals(0, vm.getBalance(), 0.001);
    }

    @Test
    void testSelectWithoutMoney() {
        vm.selectProduct("A1");
        // Should remain in idle state
        assertTrue(vm.getState() instanceof IdleState);
    }

    @Test
    void testInvalidSlot() {
        vm.insertMoney(5.00);
        vm.selectProduct("Z9"); // doesn't exist
        assertTrue(vm.getState() instanceof HasMoneyState); // still waiting
    }

    @Test
    void testEmptySlot() {
        // B2 has no product
        vm.insertMoney(5.00);
        vm.selectProduct("B2");
        assertTrue(vm.getState() instanceof HasMoneyState);
    }

    @Test
    void testStockProduct() {
        vm.stockProduct("B2", new Product("Gum", 0.50), 10);
        assertFalse(vm.getSlot("B2").isEmpty());
        assertEquals(10, vm.getSlot("B2").getQuantity());
    }

    @Test
    void testAddStock() {
        vm.addStock("A1", 5);
        assertEquals(8, vm.getSlot("A1").getQuantity()); // was 3 + 5
    }

    @Test
    void testPurchaseUntilEmpty() {
        // Buy all 3 Colas
        for (int i = 0; i < 3; i++) {
            vm.insertMoney(1.50);
            vm.selectProduct("A1");
        }
        assertTrue(vm.getSlot("A1").isEmpty());

        // Try to buy another
        vm.insertMoney(1.50);
        vm.selectProduct("A1");
        assertTrue(vm.getState() instanceof HasMoneyState); // can't buy, slot empty
        vm.cancelTransaction(); // clean up
    }

    @Test
    void testCancelInIdleState() {
        // Cancel when no transaction — should be a no-op
        vm.cancelTransaction();
        assertTrue(vm.getState() instanceof IdleState);
    }
}
