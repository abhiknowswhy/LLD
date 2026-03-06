package questions.lld.PaymentWallet;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Currency;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Orchestrator for all wallet operations.
 * Provides thread-safe balance updates and atomic cross-wallet transfers.
 * Uses consistent lock ordering (by wallet ID) to prevent deadlocks.
 */
public class WalletService {

    private final Map<String, Wallet> wallets;
    private final Map<String, User> users;

    public WalletService() {
        this.wallets = new ConcurrentHashMap<>();
        this.users = new ConcurrentHashMap<>();
    }

    /**
     * Creates a new wallet with the given currency and registers it.
     *
     * @return the newly created wallet
     */
    public Wallet createWallet(Currency currency) {
        Wallet wallet = new Wallet(currency);
        wallets.put(wallet.getId(), wallet);
        return wallet;
    }

    /**
     * Registers a user (and their wallet) with the service.
     */
    public void registerUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        users.put(user.getId(), user);
        wallets.putIfAbsent(user.getWallet().getId(), user.getWallet());
    }

    /**
     * Adds money (credit) to a wallet. Thread-safe.
     *
     * @return the completed transaction
     */
    public Transaction addMoney(String walletId, BigDecimal amount,
                                PaymentMethod method, String description) {
        Wallet wallet = getWalletOrThrow(walletId);
        Transaction txn = new Transaction(TransactionType.CREDIT, amount,
                null, walletId, description);
        ReentrantLock lock = wallet.getLock();
        lock.lock();
        try {
            wallet.credit(amount);
            txn.setStatus(TransactionStatus.COMPLETED);
            wallet.addTransaction(txn);
        } catch (Exception e) {
            txn.setStatus(TransactionStatus.FAILED);
            wallet.addTransaction(txn);
            throw e;
        } finally {
            lock.unlock();
        }
        return txn;
    }

    /**
     * Withdraws money (debit) from a wallet. Thread-safe.
     * Throws {@link InsufficientBalanceException} if balance is too low.
     *
     * @return the completed transaction
     */
    public Transaction withdraw(String walletId, BigDecimal amount,
                                PaymentMethod method, String description)
            throws InsufficientBalanceException {
        Wallet wallet = getWalletOrThrow(walletId);
        Transaction txn = new Transaction(TransactionType.DEBIT, amount,
                walletId, null, description);
        ReentrantLock lock = wallet.getLock();
        lock.lock();
        try {
            wallet.debit(amount);
            txn.setStatus(TransactionStatus.COMPLETED);
            wallet.addTransaction(txn);
        } catch (InsufficientBalanceException e) {
            txn.setStatus(TransactionStatus.FAILED);
            wallet.addTransaction(txn);
            throw e;
        } finally {
            lock.unlock();
        }
        return txn;
    }

    /**
     * Transfers money atomically between two wallets.
     * Uses consistent lock ordering (lower wallet-ID first) to prevent deadlocks.
     *
     * @return the debit-side transaction (the credit-side transaction is also recorded)
     * @throws InsufficientBalanceException if the source wallet has insufficient funds
     */
    public Transaction transfer(String fromWalletId, String toWalletId,
                                BigDecimal amount, String description)
            throws InsufficientBalanceException {
        if (fromWalletId.equals(toWalletId)) {
            throw new IllegalArgumentException("Cannot transfer to the same wallet");
        }
        Wallet from = getWalletOrThrow(fromWalletId);
        Wallet to = getWalletOrThrow(toWalletId);

        // Consistent lock ordering by wallet ID to prevent deadlocks
        Wallet first = fromWalletId.compareTo(toWalletId) < 0 ? from : to;
        Wallet second = first == from ? to : from;

        Transaction debitTxn = new Transaction(TransactionType.TRANSFER, amount,
                fromWalletId, toWalletId, "DEBIT: " + description);
        Transaction creditTxn = new Transaction(TransactionType.TRANSFER, amount,
                fromWalletId, toWalletId, "CREDIT: " + description);

        first.getLock().lock();
        try {
            second.getLock().lock();
            try {
                // Atomic: debit source, then credit destination
                from.debit(amount);
                to.credit(amount);

                debitTxn.setStatus(TransactionStatus.COMPLETED);
                creditTxn.setStatus(TransactionStatus.COMPLETED);
                from.addTransaction(debitTxn);
                to.addTransaction(creditTxn);
            } catch (InsufficientBalanceException e) {
                debitTxn.setStatus(TransactionStatus.FAILED);
                creditTxn.setStatus(TransactionStatus.FAILED);
                from.addTransaction(debitTxn);
                to.addTransaction(creditTxn);
                throw e;
            } finally {
                second.getLock().unlock();
            }
        } finally {
            first.getLock().unlock();
        }
        return debitTxn;
    }

    /**
     * Returns the current balance of the specified wallet.
     */
    public BigDecimal getBalance(String walletId) {
        Wallet wallet = getWalletOrThrow(walletId);
        wallet.getLock().lock();
        try {
            return wallet.getBalance();
        } finally {
            wallet.getLock().unlock();
        }
    }

    /**
     * Returns an unmodifiable view of the transaction history.
     */
    public List<Transaction> getTransactionHistory(String walletId) {
        Wallet wallet = getWalletOrThrow(walletId);
        wallet.getLock().lock();
        try {
            return wallet.getTransactionHistory();
        } finally {
            wallet.getLock().unlock();
        }
    }

    /**
     * Reverses a previously completed transaction by applying
     * the inverse operation and marking the original as REVERSED.
     *
     * @return the reversal transaction
     */
    public Transaction reverseTransaction(String walletId, String transactionId)
            throws InsufficientBalanceException {
        Wallet wallet = getWalletOrThrow(walletId);
        wallet.getLock().lock();
        try {
            Transaction original = wallet.getTransactionHistory().stream()
                    .filter(t -> t.getId().equals(transactionId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Transaction not found: " + transactionId));

            if (original.getStatus() != TransactionStatus.COMPLETED) {
                throw new IllegalStateException(
                        "Only COMPLETED transactions can be reversed; current status: "
                                + original.getStatus());
            }

            Transaction reversal;
            switch (original.getType()) {
                case CREDIT -> {
                    wallet.debit(original.getAmount());
                    reversal = new Transaction(TransactionType.DEBIT, original.getAmount(),
                            walletId, null,
                            "REVERSAL of " + original.getId().substring(0, 8));
                }
                case DEBIT -> {
                    wallet.credit(original.getAmount());
                    reversal = new Transaction(TransactionType.CREDIT, original.getAmount(),
                            null, walletId,
                            "REVERSAL of " + original.getId().substring(0, 8));
                }
                default -> throw new UnsupportedOperationException(
                        "Use transfer-specific reversal for TRANSFER transactions");
            }

            original.setStatus(TransactionStatus.REVERSED);
            reversal.setStatus(TransactionStatus.COMPLETED);
            wallet.addTransaction(reversal);
            return reversal;
        } finally {
            wallet.getLock().unlock();
        }
    }

    /**
     * Reverses an atomic transfer by crediting back the source and debiting the destination.
     *
     * @return the reversal debit-side transaction
     */
    public Transaction reverseTransfer(String fromWalletId, String toWalletId,
                                       String debitTxnId, String creditTxnId)
            throws InsufficientBalanceException {
        if (fromWalletId.equals(toWalletId)) {
            throw new IllegalArgumentException("Wallets must be different for transfer reversal");
        }
        Wallet from = getWalletOrThrow(fromWalletId);
        Wallet to = getWalletOrThrow(toWalletId);

        Wallet first = fromWalletId.compareTo(toWalletId) < 0 ? from : to;
        Wallet second = first == from ? to : from;

        first.getLock().lock();
        try {
            second.getLock().lock();
            try {
                Transaction origDebit = findTransaction(from, debitTxnId);
                Transaction origCredit = findTransaction(to, creditTxnId);

                if (origDebit.getStatus() != TransactionStatus.COMPLETED
                        || origCredit.getStatus() != TransactionStatus.COMPLETED) {
                    throw new IllegalStateException("Both transactions must be COMPLETED to reverse");
                }

                BigDecimal amount = origDebit.getAmount();

                // Reverse: credit the original source, debit the original destination
                to.debit(amount);
                from.credit(amount);

                origDebit.setStatus(TransactionStatus.REVERSED);
                origCredit.setStatus(TransactionStatus.REVERSED);

                Transaction revCredit = new Transaction(TransactionType.TRANSFER, amount,
                        toWalletId, fromWalletId,
                        "REVERSAL-CREDIT of " + origDebit.getId().substring(0, 8));
                Transaction revDebit = new Transaction(TransactionType.TRANSFER, amount,
                        toWalletId, fromWalletId,
                        "REVERSAL-DEBIT of " + origCredit.getId().substring(0, 8));
                revCredit.setStatus(TransactionStatus.COMPLETED);
                revDebit.setStatus(TransactionStatus.COMPLETED);
                from.addTransaction(revCredit);
                to.addTransaction(revDebit);
                return revCredit;
            } finally {
                second.getLock().unlock();
            }
        } finally {
            first.getLock().unlock();
        }
    }

    /**
     * Looks up a wallet by ID or throws.
     */
    private Wallet getWalletOrThrow(String walletId) {
        Wallet wallet = wallets.get(walletId);
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet not found: " + walletId);
        }
        return wallet;
    }

    /**
     * Finds a transaction in a wallet's history or throws.
     */
    private Transaction findTransaction(Wallet wallet, String txnId) {
        return wallet.getTransactionHistory().stream()
                .filter(t -> t.getId().equals(txnId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Transaction not found: " + txnId));
    }
}
