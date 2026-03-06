package questions.lld.PaymentWallet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a digital wallet holding a balance in a specific currency.
 * All balance mutations are thread-safe via a {@link ReentrantLock}.
 */
public class Wallet {

    private final String id;
    private BigDecimal balance;
    private final List<Transaction> transactionHistory;
    private final Currency currency;
    private final ReentrantLock lock;

    public Wallet(Currency currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Currency must not be null");
        }
        this.id = UUID.randomUUID().toString();
        this.balance = BigDecimal.ZERO;
        this.transactionHistory = new ArrayList<>();
        this.currency = currency;
        this.lock = new ReentrantLock(true); // fair lock
    }

    public String getId() { return id; }

    public BigDecimal getBalance() { return balance; }

    public Currency getCurrency() { return currency; }

    public List<Transaction> getTransactionHistory() { return Collections.unmodifiableList(transactionHistory); }

    /**
     * Returns the internal lock so the service layer can coordinate
     * multi-wallet atomic operations (e.g. transfers).
     */
    public ReentrantLock getLock() { return lock; }

    /**
     * Credits the wallet by the given amount.
     * Caller MUST hold the lock.
     */
    void credit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Credit amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }

    /**
     * Debits the wallet by the given amount.
     * Caller MUST hold the lock.
     *
     * @throws InsufficientBalanceException if balance is less than amount
     */
    void debit(BigDecimal amount) throws InsufficientBalanceException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Debit amount must be positive");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException(
                    "Insufficient balance: available=%s, requested=%s".formatted(this.balance, amount));
        }
        this.balance = this.balance.subtract(amount);
    }

    /**
     * Records a transaction in this wallet's history.
     * Caller MUST hold the lock.
     */
    void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction must not be null");
        }
        this.transactionHistory.add(transaction);
    }

    @Override
    public String toString() {
        return "Wallet{id='%s', balance=%s %s, txns=%d}"
                .formatted(id.substring(0, 8), balance, currency.getCurrencyCode(),
                        transactionHistory.size());
    }
}
