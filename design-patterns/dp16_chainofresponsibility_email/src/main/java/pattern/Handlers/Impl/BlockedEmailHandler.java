package pattern.Handlers.Impl;

import pattern.Email;
import pattern.Handlers.EmailHandler;
import java.util.Arrays;
import java.util.List;

public class BlockedEmailHandler extends EmailHandler {
    private final List<String> blockedEmails = Arrays.asList(
        "spam@example.com",
        "blacklisted@domain.com",
        "unwanted@mail.com"
    );

    @Override
    protected boolean canHandle(Email email) {
        return blockedEmails.contains(email.getFrom().toLowerCase());
    }

    @Override
    protected void process(Email email) {
        System.out.println("Blocked email from: " + email.getFrom());
        email.setCategory("blocked");
    }
}
