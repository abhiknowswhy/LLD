package questions.lld.VendingMachine.State;

import questions.lld.VendingMachine.Slot;
import questions.lld.VendingMachine.VendingMachine;

/**
 * HasMoney state — money inserted, waiting for product selection.
 */
public class HasMoneyState implements VendingState {

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        machine.addBalance(amount);
        System.out.printf("Added $%.2f. Balance: $%.2f%n", amount, machine.getBalance());
    }

    @Override
    public void selectProduct(VendingMachine machine, String slotCode) {
        Slot slot = machine.getSlot(slotCode);

        if (slot == null) {
            System.out.println("Invalid slot code: " + slotCode);
            return;
        }
        if (slot.isEmpty()) {
            System.out.println("Slot " + slotCode + " is empty. Choose another.");
            return;
        }
        if (machine.getBalance() < slot.getProduct().getPrice()) {
            System.out.printf("Insufficient funds. Need $%.2f, have $%.2f%n",
                    slot.getProduct().getPrice(), machine.getBalance());
            return;
        }

        machine.setSelectedSlot(slotCode);
        System.out.println("Selected: " + slot.getProduct());
        machine.setState(new DispensingState());
        machine.getState().dispense(machine);
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Please select a product first.");
    }

    @Override
    public void cancelTransaction(VendingMachine machine) {
        double refund = machine.getBalance();
        machine.resetBalance();
        machine.setSelectedSlot(null);
        System.out.printf("Transaction cancelled. Refund: $%.2f%n", refund);
        machine.setState(new IdleState());
    }

    @Override
    public String getStateName() { return "HAS_MONEY"; }
}
