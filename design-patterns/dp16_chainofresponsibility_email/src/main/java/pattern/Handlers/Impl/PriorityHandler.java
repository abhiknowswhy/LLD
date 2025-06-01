package pattern.Handlers.Impl;

import pattern.Email;
import pattern.Handlers.EmailHandler;
import java.util.Arrays;
import java.util.List;

public class PriorityHandler extends EmailHandler {
    private final List<String> priorityDomains = Arrays.asList(
        "@company.com",
        "@client.com",
        "@important.com"
    );

    @Override
    protected boolean canHandle(Email email) {
        return priorityDomains.stream()
            .anyMatch(domain -> email.getFrom().toLowerCase().endsWith(domain));
    }

    @Override
    protected void process(Email email) {
        System.out.println("Marking as priority email: " + email.getSubject());
        email.setCategory("priority");
    }
}
