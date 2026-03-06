package questions.lld.PaymentWallet;

/**
 * Thrown when a wallet operation is attempted with insufficient funds.
 */
public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
