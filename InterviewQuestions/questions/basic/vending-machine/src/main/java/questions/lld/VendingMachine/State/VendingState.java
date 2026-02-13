package questions.lld.VendingMachine.State;

import questions.lld.VendingMachine.VendingMachine;

/**
 * State pattern — each state handles actions differently.
 * States: Idle → HasMoney → Dispensing → (back to Idle)
 */
public interface VendingState {
    void insertMoney(VendingMachine machine, double amount);
    void selectProduct(VendingMachine machine, String slotCode);
    void dispense(VendingMachine machine);
    void cancelTransaction(VendingMachine machine);
    String getStateName();
}
