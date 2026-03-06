package questions.lld.PaymentWallet;

import java.util.UUID;

/**
 * Represents a registered user who owns a wallet.
 */
public class User {

    private final String id;
    private final String name;
    private final String email;
    private final Wallet wallet;

    public User(String name, String email, Wallet wallet) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name must not be blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("User email must not be blank");
        }
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet must not be null");
        }
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.wallet = wallet;
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public Wallet getWallet() { return wallet; }

    @Override
    public String toString() {
        return "User{id='%s', name='%s', email='%s', wallet=%s}"
                .formatted(id.substring(0, 8), name, email, wallet.getId().substring(0, 8));
    }
}
