package questions.lld.VendingMachine.State;

import questions.lld.VendingMachine.VendingMachine;

/**
 * Idle state — waiting for money.
 */
public class IdleState implements VendingState {

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount. Please insert a positive value.");
            return;
        }
        machine.addBalance(amount);
        System.out.printf("Inserted $%.2f. Balance: $%.2f%n", amount, machine.getBalance());
        machine.setState(new HasMoneyState());
    }

    @Override
    public void selectProduct(VendingMachine machine, String slotCode) {
        System.out.println("Please insert money first.");
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("Please insert money and select a product first.");
    }

    @Override
    public void cancelTransaction(VendingMachine machine) {
        System.out.println("No transaction in progress.");
    }

    @Override
    public String getStateName() { return "IDLE"; }
}
