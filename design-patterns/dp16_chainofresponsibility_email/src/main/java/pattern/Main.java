package pattern;

import pattern.Handlers.EmailHandler;
import pattern.Handlers.Impl.BlockedEmailHandler;
import pattern.Handlers.Impl.PromotionsHandler;
import pattern.Handlers.Impl.PriorityHandler;
import pattern.Handlers.Impl.SpamKeywordHandler;

public class Main {
    public static void main(String[] args) {
        // Create handlers
        EmailHandler blockedHandler = new BlockedEmailHandler();
        EmailHandler spamHandler = new SpamKeywordHandler();
        EmailHandler promotionsHandler = new PromotionsHandler();
        EmailHandler priorityHandler = new PriorityHandler();

        // Create the chain
        blockedHandler
            .setNext(spamHandler)
            .setNext(promotionsHandler)
            .setNext(priorityHandler);

        // Create test emails
        Email[] emails = {
            new Email("spam@example.com", "Hello", "Just a test email"),
            new Email("user@shopping.com", "Special Offer!", "Don't miss out on these deals!"),
            new Email("newsletter@marketing.com", "Your Weekly Newsletter", "Here are this week's updates"),
            new Email("boss@company.com", "Project Update", "Please review the latest changes"),
            new Email("random@email.com", "Click Here for Prize", "You've won a lifetime supply of spam!"),
            new Email("contact@client.com", "Meeting Tomorrow", "Discussing project requirements")
        };

        // Process all emails through the chain
        for (Email email : emails) {
            System.out.println("\nProcessing email: " + email.getSubject());
            blockedHandler.handle(email);
            System.out.println("Final category: " + email.getCategory());
        }
    }
}