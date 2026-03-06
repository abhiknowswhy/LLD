package questions.lld;

import questions.lld.PaymentWallet.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demo driver for the Payment Wallet LLD.
 * Exercises wallet creation, add money, withdrawal, atomic transfers,
 * overdraft prevention, transaction history, reversal, and concurrent operations.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        // ╔══════════════════════════════════════════════════════════════╗
        // ║                    PAYMENT WALLET SYSTEM                    ║
        // ╚══════════════════════════════════════════════════════════════╝

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    PAYMENT WALLET SYSTEM                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // ── SETUP PHASE ──────────────────────────────────────────────

        System.out.println("\n========== SETUP PHASE ==========\n");

        WalletService service = new WalletService();
        Currency inr = Currency.getInstance("INR");

        Wallet aliceWallet = service.createWallet(inr);
        Wallet bobWallet = service.createWallet(inr);
        Wallet charlieWallet = service.createWallet(inr);

        User alice = new User("Alice", "alice@example.com", aliceWallet);
        User bob = new User("Bob", "bob@example.com", bobWallet);
        User charlie = new User("Charlie", "charlie@example.com", charlieWallet);

        service.registerUser(alice);
        service.registerUser(bob);
        service.registerUser(charlie);

        System.out.println("Registered users:");
        System.out.println("  " + alice);
        System.out.println("  " + bob);
        System.out.println("  " + charlie);

        // ── EXERCISE PHASE 1: ADD MONEY ──────────────────────────────

        System.out.println("\n========== ADD MONEY ==========\n");

        Transaction t1 = service.addMoney(aliceWallet.getId(),
                new BigDecimal("5000.00"), PaymentMethod.BANK_TRANSFER,
                "Initial deposit via bank");
        System.out.println("Alice deposit: " + t1);
        System.out.println("Alice balance: " + service.getBalance(aliceWallet.getId()));

        Transaction t2 = service.addMoney(bobWallet.getId(),
                new BigDecimal("3000.00"), PaymentMethod.UPI,
                "UPI load");
        System.out.println("Bob deposit:   " + t2);
        System.out.println("Bob balance:   " + service.getBalance(bobWallet.getId()));

        Transaction t3 = service.addMoney(charlieWallet.getId(),
                new BigDecimal("1000.00"), PaymentMethod.WALLET_BALANCE,
                "Cash load");
        System.out.println("Charlie deposit: " + t3);
        System.out.println("Charlie balance: " + service.getBalance(charlieWallet.getId()));

        // ── EXERCISE PHASE 2: WITHDRAWAL ─────────────────────────────

        System.out.println("\n========== WITHDRAWAL ==========\n");

        Transaction t4 = service.withdraw(aliceWallet.getId(),
                new BigDecimal("500.00"), PaymentMethod.WALLET_BALANCE,
                "ATM withdrawal");
        System.out.println("Alice withdrawal: " + t4);
        System.out.println("Alice balance:    " + service.getBalance(aliceWallet.getId()));

        // ── EXERCISE PHASE 3: OVERDRAFT PREVENTION ───────────────────

        System.out.println("\n========== OVERDRAFT PREVENTION ==========\n");

        try {
            service.withdraw(charlieWallet.getId(),
                    new BigDecimal("9999.00"), PaymentMethod.WALLET_BALANCE,
                    "Excessive withdrawal attempt");
            System.out.println("ERROR: Should have thrown InsufficientBalanceException!");
        } catch (InsufficientBalanceException e) {
            System.out.println("Correctly prevented overdraft: " + e.getMessage());
        }
        System.out.println("Charlie balance unchanged: " + service.getBalance(charlieWallet.getId()));

        // ── EXERCISE PHASE 4: ATOMIC TRANSFER ────────────────────────

        System.out.println("\n========== ATOMIC TRANSFER ==========\n");

        BigDecimal aliceBefore = service.getBalance(aliceWallet.getId());
        BigDecimal bobBefore = service.getBalance(bobWallet.getId());
        System.out.println("Before transfer — Alice: " + aliceBefore + ", Bob: " + bobBefore);

        Transaction t5 = service.transfer(aliceWallet.getId(), bobWallet.getId(),
                new BigDecimal("1200.00"), "Alice pays Bob for dinner");
        System.out.println("Transfer txn: " + t5);

        BigDecimal aliceAfter = service.getBalance(aliceWallet.getId());
        BigDecimal bobAfter = service.getBalance(bobWallet.getId());
        System.out.println("After transfer — Alice: " + aliceAfter + ", Bob: " + bobAfter);

        // Verify atomicity: total unchanged
        BigDecimal totalBefore = aliceBefore.add(bobBefore);
        BigDecimal totalAfter = aliceAfter.add(bobAfter);
        System.out.println("Total before: " + totalBefore + ", Total after: " + totalAfter
                + " — Atomic: " + totalBefore.compareTo(totalAfter) + " (0 = correct)");

        // ── EXERCISE PHASE 5: FAILED TRANSFER (INSUFFICIENT FUNDS) ──

        System.out.println("\n========== FAILED TRANSFER ==========\n");

        try {
            service.transfer(charlieWallet.getId(), aliceWallet.getId(),
                    new BigDecimal("50000.00"), "Charlie tries to send too much");
            System.out.println("ERROR: Should have thrown InsufficientBalanceException!");
        } catch (InsufficientBalanceException e) {
            System.out.println("Correctly prevented overdraft transfer: " + e.getMessage());
        }
        System.out.println("Charlie balance unchanged: " + service.getBalance(charlieWallet.getId()));
        System.out.println("Alice balance unchanged:   " + service.getBalance(aliceWallet.getId()));

        // ── EXERCISE PHASE 6: TRANSACTION HISTORY ────────────────────

        System.out.println("\n========== TRANSACTION HISTORY ==========\n");

        List<Transaction> aliceHistory = service.getTransactionHistory(aliceWallet.getId());
        System.out.println("Alice has " + aliceHistory.size() + " transaction(s):");
        aliceHistory.forEach(tx -> System.out.println("  " + tx));

        // ── EXERCISE PHASE 7: REVERSE A TRANSACTION ──────────────────

        System.out.println("\n========== REVERSE TRANSACTION ==========\n");

        // Reverse Alice's initial deposit
        System.out.println("Reversing Alice's ATM withdrawal (txn " + t4.getId().substring(0, 8) + ")...");
        BigDecimal balanceBefore = service.getBalance(aliceWallet.getId());
        Transaction reversal = service.reverseTransaction(aliceWallet.getId(), t4.getId());
        BigDecimal balanceAfterRev = service.getBalance(aliceWallet.getId());
        System.out.println("Reversal txn: " + reversal);
        System.out.println("Original txn status now: " + t4.getStatus());
        System.out.println("Alice balance: " + balanceBefore + " → " + balanceAfterRev);

        // ── EXERCISE PHASE 8: REVERSE A TRANSFER ─────────────────────

        System.out.println("\n========== REVERSE TRANSFER ==========\n");

        // Find the credit-side txn in Bob's history that matches t5
        Transaction bobCreditTxn = service.getTransactionHistory(bobWallet.getId()).stream()
                .filter(tx -> tx.getType() == TransactionType.TRANSFER
                        && tx.getSourceWalletId().equals(aliceWallet.getId())
                        && tx.getStatus() == TransactionStatus.COMPLETED)
                .findFirst()
                .orElseThrow();

        BigDecimal alicePreRev = service.getBalance(aliceWallet.getId());
        BigDecimal bobPreRev = service.getBalance(bobWallet.getId());
        System.out.println("Before reversal — Alice: " + alicePreRev + ", Bob: " + bobPreRev);

        Transaction transferRev = service.reverseTransfer(
                aliceWallet.getId(), bobWallet.getId(),
                t5.getId(), bobCreditTxn.getId());
        System.out.println("Transfer reversal txn: " + transferRev);

        BigDecimal alicePostRev = service.getBalance(aliceWallet.getId());
        BigDecimal bobPostRev = service.getBalance(bobWallet.getId());
        System.out.println("After reversal  — Alice: " + alicePostRev + ", Bob: " + bobPostRev);

        // ── EXERCISE PHASE 9: CONCURRENT DEPOSITS ────────────────────

        System.out.println("\n========== CONCURRENT DEPOSITS ==========\n");

        // Reset Charlie's wallet with a known balance
        BigDecimal charlieStart = service.getBalance(charlieWallet.getId());
        System.out.println("Charlie starting balance: " + charlieStart);

        int threadCount = 10;
        BigDecimal depositPerThread = new BigDecimal("100.00");
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            pool.submit(() -> {
                try {
                    service.addMoney(charlieWallet.getId(), depositPerThread,
                            PaymentMethod.UPI, "Concurrent deposit #" + idx);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await(5, TimeUnit.SECONDS);
        pool.shutdown();

        BigDecimal expected = charlieStart.add(depositPerThread.multiply(new BigDecimal(threadCount)));
        BigDecimal actual = service.getBalance(charlieWallet.getId());
        System.out.println("Expected balance: " + expected);
        System.out.println("Actual balance:   " + actual);
        System.out.println("Thread-safe: " + (expected.compareTo(actual) == 0 ? "PASS ✓" : "FAIL ✗"));

        // ── EXERCISE PHASE 10: CONCURRENT TRANSFERS ──────────────────

        System.out.println("\n========== CONCURRENT TRANSFERS ==========\n");

        // Give Alice and Bob fresh known balances
        service.addMoney(aliceWallet.getId(), new BigDecimal("10000.00"),
                PaymentMethod.BANK_TRANSFER, "Reload for concurrency test");
        service.addMoney(bobWallet.getId(), new BigDecimal("10000.00"),
                PaymentMethod.BANK_TRANSFER, "Reload for concurrency test");

        BigDecimal aliceStart = service.getBalance(aliceWallet.getId());
        BigDecimal bobStart = service.getBalance(bobWallet.getId());
        BigDecimal totalStart = aliceStart.add(bobStart);
        System.out.println("Alice: " + aliceStart + ", Bob: " + bobStart + ", Total: " + totalStart);

        int transferCount = 20;
        CountDownLatch latch2 = new CountDownLatch(transferCount);
        ExecutorService pool2 = Executors.newFixedThreadPool(8);
        BigDecimal transferAmt = new BigDecimal("50.00");

        for (int i = 0; i < transferCount; i++) {
            final int idx = i;
            pool2.submit(() -> {
                try {
                    // Even: Alice→Bob, Odd: Bob→Alice (test deadlock freedom)
                    if (idx % 2 == 0) {
                        service.transfer(aliceWallet.getId(), bobWallet.getId(),
                                transferAmt, "Concurrent xfer A→B #" + idx);
                    } else {
                        service.transfer(bobWallet.getId(), aliceWallet.getId(),
                                transferAmt, "Concurrent xfer B→A #" + idx);
                    }
                } catch (InsufficientBalanceException e) {
                    System.out.println("  Transfer #" + idx + " failed (insufficient): " + e.getMessage());
                } finally {
                    latch2.countDown();
                }
            });
        }
        latch2.await(10, TimeUnit.SECONDS);
        pool2.shutdown();

        BigDecimal aliceEnd = service.getBalance(aliceWallet.getId());
        BigDecimal bobEnd = service.getBalance(bobWallet.getId());
        BigDecimal totalEnd = aliceEnd.add(bobEnd);
        System.out.println("Alice: " + aliceEnd + ", Bob: " + bobEnd + ", Total: " + totalEnd);
        System.out.println("Conservation of money: "
                + (totalStart.compareTo(totalEnd) == 0 ? "PASS ✓" : "FAIL ✗"));

        // ── VERIFICATION PHASE ───────────────────────────────────────

        System.out.println("\n========== VERIFICATION PHASE ==========\n");

        System.out.println("Final balances:");
        System.out.println("  Alice:   " + service.getBalance(aliceWallet.getId()));
        System.out.println("  Bob:     " + service.getBalance(bobWallet.getId()));
        System.out.println("  Charlie: " + service.getBalance(charlieWallet.getId()));

        System.out.println("\nTransaction counts:");
        System.out.println("  Alice:   " + service.getTransactionHistory(aliceWallet.getId()).size());
        System.out.println("  Bob:     " + service.getTransactionHistory(bobWallet.getId()).size());
        System.out.println("  Charlie: " + service.getTransactionHistory(charlieWallet.getId()).size());

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              PAYMENT WALLET DEMO COMPLETE                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
}