package pattern.Handlers.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pattern.Email;
import static org.junit.jupiter.api.Assertions.*;

class BlockedEmailHandlerTest {
    private BlockedEmailHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BlockedEmailHandler();
    }

    @Test
    void shouldBlockKnownSpamEmail() {
        Email email = new Email("spam@example.com", "Test Subject", "Test Content");
        handler.handle(email);
        assertEquals("blocked", email.getCategory());
    }

    @Test
    void shouldNotBlockLegitimateEmail() {
        Email email = new Email("user@legitimate.com", "Test Subject", "Test Content");
        handler.handle(email);
        assertEquals("inbox", email.getCategory());
    }
}
