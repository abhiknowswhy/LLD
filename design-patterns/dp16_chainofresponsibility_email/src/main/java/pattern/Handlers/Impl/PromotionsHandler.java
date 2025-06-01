package pattern.Handlers.Impl;

import pattern.Email;
import pattern.Handlers.EmailHandler;
import java.util.Arrays;
import java.util.List;

public class PromotionsHandler extends EmailHandler {
    private final List<String> promotionalSenders = Arrays.asList(
        "@shopping.com",
        "@marketing.com",
        "@newsletter.com",
        "@deals.com"
    );

    @Override
    protected boolean canHandle(Email email) {
        return promotionalSenders.stream()
            .anyMatch(domain -> email.getFrom().toLowerCase().endsWith(domain));
    }

    @Override
    protected void process(Email email) {
        System.out.println("Moving promotional email to Promotions: " + email.getSubject());
        email.setCategory("promotions");
    }
}
