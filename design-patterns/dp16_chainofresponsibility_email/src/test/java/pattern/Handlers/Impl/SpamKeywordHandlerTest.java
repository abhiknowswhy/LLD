package pattern.Handlers.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pattern.Email;
import static org.junit.jupiter.api.Assertions.*;

class SpamKeywordHandlerTest {
    private SpamKeywordHandler handler;

    @BeforeEach
    void setUp() {
        handler = new SpamKeywordHandler();
    }

    @Test
    void shouldDetectSpamKeywordInSubject() {
        Email email = new Email("user@test.com", "Special Offer Inside!", "Normal content");
        handler.handle(email);
        assertEquals("spam", email.getCategory());
    }

    @Test
    void shouldDetectSpamKeywordInContent() {
        Email email = new Email("user@test.com", "Hello", "Click here to win a prize!");
        handler.handle(email);
        assertEquals("spam", email.getCategory());
    }

    @Test
    void shouldNotMarkNormalEmailAsSpam() {
        Email email = new Email("user@test.com", "Meeting Notes", "Here are the meeting minutes.");
        handler.handle(email);
        assertEquals("inbox", email.getCategory());
    }
}
