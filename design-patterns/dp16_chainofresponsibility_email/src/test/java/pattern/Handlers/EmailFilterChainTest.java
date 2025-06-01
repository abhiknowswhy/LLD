package pattern.Handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pattern.Email;
import pattern.Handlers.Impl.BlockedEmailHandler;
import pattern.Handlers.Impl.SpamKeywordHandler;
import pattern.Handlers.Impl.PromotionsHandler;
import pattern.Handlers.Impl.PriorityHandler;
import static org.junit.jupiter.api.Assertions.*;

class EmailFilterChainTest {
    private EmailHandler chain;

    @BeforeEach
    void setUp() {
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

        chain = blockedHandler;
    }

    @Test
    void shouldBlockSpamEmailRegardlessOfContent() {
        Email email = new Email(
            "spam@example.com",
            "Important Business Proposal",
            "Legitimate looking content"
        );
        chain.handle(email);
        assertEquals("blocked", email.getCategory());
    }

    @Test
    void shouldDetectSpamKeywordsAfterBlockCheck() {
        Email email = new Email(
            "random@email.com",
            "Click Here for Prize!",
            "You've won a special offer!"
        );
        chain.handle(email);
        assertEquals("spam", email.getCategory());
    }

    @Test
    void shouldCategorizePromotionalEmail() {
        Email email = new Email(
            "newsletter@marketing.com",
            "Weekly Newsletter",
            "Latest updates and news"
        );
        chain.handle(email);
        assertEquals("promotions", email.getCategory());
    }

    @Test
    void shouldIdentifyPriorityEmail() {
        Email email = new Email(
            "boss@company.com",
            "Urgent: Project Update",
            "Please review ASAP"
        );
        chain.handle(email);
        assertEquals("priority", email.getCategory());
    }

    @Test
    void shouldKeepInboxForNormalEmail() {
        Email email = new Email(
            "friend@gmail.com",
            "Hello",
            "Just saying hi!"
        );
        chain.handle(email);
        assertEquals("inbox", email.getCategory());
    }

    @Test
    void shouldHandleComplexEmailCorrectly() {
        // This email contains promotional content but is from a priority sender
        Email email = new Email(
            "boss@company.com",
            "Special Offer for Team",
            "Click here to view our team building options"
        );
        chain.handle(email);
        // Should be marked as priority despite having spam keywords
        assertEquals("priority", email.getCategory());
    }
}
