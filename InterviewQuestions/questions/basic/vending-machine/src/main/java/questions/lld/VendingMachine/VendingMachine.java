package questions.lld.VendingMachine;

import questions.lld.VendingMachine.State.IdleState;
import questions.lld.VendingMachine.State.VendingState;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Vending Machine using State Pattern.
 *
 * States: Idle → HasMoney → Dispensing → Idle
 *
 * Features:
 * - Insert money (supports multiple insertions)
 * - Select a product by slot code
 * - Dispense product and return change
 * - Cancel transaction and get refund
 * - Stock management (add products, check inventory)
 */
public class VendingMachine {
    private final Map<String, Slot> slots;
    private VendingState state;
    private double balance;
    private String selectedSlot;

    public VendingMachine(int numRows, int numCols) {
        this.slots = new LinkedHashMap<>();
        this.state = new IdleState();
        this.balance = 0;
        this.selectedSlot = null;

        // Initialize slots: A1, A2, ..., B1, B2, ...
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                String code = String.valueOf((char) ('A' + i)) + (j + 1);
                slots.put(code, new Slot(code));
            }
        }
    }

    // --- Public actions (delegated to current state) ---

    public void insertMoney(double amount) {
        state.insertMoney(this, amount);
    }

    public void selectProduct(String slotCode) {
        state.selectProduct(this, slotCode.toUpperCase());
    }

    public void cancelTransaction() {
        state.cancelTransaction(this);
    }

    // --- Stock management ---

    public void stockProduct(String slotCode, Product product, int quantity) {
        Slot slot = slots.get(slotCode.toUpperCase());
        if (slot == null) {
            throw new IllegalArgumentException("Invalid slot code: " + slotCode);
        }
        slot.stock(product, quantity);
    }

    public void addStock(String slotCode, int quantity) {
        Slot slot = slots.get(slotCode.toUpperCase());
        if (slot == null) {
            throw new IllegalArgumentException("Invalid slot code: " + slotCode);
        }
        slot.addStock(quantity);
    }

    // --- State management (package-private for states to use) ---

    public VendingState getState() { return state; }
    public void setState(VendingState state) { this.state = state; }

    public double getBalance() { return balance; }
    public void addBalance(double amount) { this.balance += amount; }
    public void resetBalance() { this.balance = 0; }

    public String getSelectedSlot() { return selectedSlot; }
    public void setSelectedSlot(String slotCode) { this.selectedSlot = slotCode; }

    public Slot getSlot(String code) {
        return slots.get(code);
    }

    // --- Display ---

    public void displayInventory() {
        System.out.println("\n=== Vending Machine Inventory ===");
        for (Slot slot : slots.values()) {
            System.out.println("  " + slot);
        }
        System.out.println("State: " + state.getStateName());
        System.out.printf("Balance: $%.2f%n", balance);
        System.out.println();
    }
}
