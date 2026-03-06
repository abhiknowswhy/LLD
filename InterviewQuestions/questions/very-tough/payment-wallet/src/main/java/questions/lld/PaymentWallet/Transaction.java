package questions.lld.PaymentWallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an immutable record of a single wallet transaction.
 * Captures amount, type, status, timestamp, and optional source/destination wallets.
 */
public class Transaction {

    private final String id;
    private final TransactionType type;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;
    private final String sourceWalletId;
    private final String destinationWalletId;
    private volatile TransactionStatus status;
    private final String description;

    public Transaction(TransactionType type, BigDecimal amount,
                       String sourceWalletId, String destinationWalletId,
                       String description) {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type must not be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Transaction description must not be blank");
        }
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.sourceWalletId = sourceWalletId;
        this.destinationWalletId = destinationWalletId;
        this.status = TransactionStatus.PENDING;
        this.description = description;
    }

    public String getId() { return id; }

    public TransactionType getType() { return type; }

    public BigDecimal getAmount() { return amount; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public String getSourceWalletId() { return sourceWalletId; }

    public String getDestinationWalletId() { return destinationWalletId; }

    public TransactionStatus getStatus() { return status; }

    public String getDescription() { return description; }

    /**
     * Moves the transaction to a new status.
     * Package-private so only the service layer can mutate status.
     */
    void setStatus(TransactionStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status must not be null");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{id='%s', type=%s, amount=%s, status=%s, desc='%s', src=%s, dst=%s, ts=%s}"
                .formatted(id.substring(0, 8), type, amount, status, description,
                        sourceWalletId, destinationWalletId, timestamp);
    }
}
