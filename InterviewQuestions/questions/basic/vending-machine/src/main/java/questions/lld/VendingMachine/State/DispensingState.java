package questions.lld.VendingMachine.State;

import questions.lld.VendingMachine.Product;
import questions.lld.VendingMachine.Slot;
import questions.lld.VendingMachine.VendingMachine;

/**
 * Dispensing state — product selected, dispensing and returning change.
 */
public class DispensingState implements VendingState {

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        System.out.println("Please wait, dispensing in progress...");
    }

    @Override
    public void selectProduct(VendingMachine machine, String slotCode) {
        System.out.println("Please wait, dispensing in progress...");
    }

    @Override
    public void dispense(VendingMachine machine) {
        Slot slot = machine.getSlot(machine.getSelectedSlot());
        Product product = slot.dispense();
        double cost = product.getPrice();
        double change = machine.getBalance() - cost;

        System.out.println("Dispensing: " + product);
        if (change > 0) {
            System.out.printf("Change returned: $%.2f%n", change);
        }

        machine.resetBalance();
        machine.setSelectedSlot(null);
        machine.setState(new IdleState());
    }

    @Override
    public void cancelTransaction(VendingMachine machine) {
        System.out.println("Cannot cancel during dispensing.");
    }

    @Override
    public String getStateName() { return "DISPENSING"; }
}
