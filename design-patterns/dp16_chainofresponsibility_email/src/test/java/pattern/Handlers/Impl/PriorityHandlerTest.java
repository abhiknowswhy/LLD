package pattern.Handlers.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pattern.Email;
import static org.junit.jupiter.api.Assertions.*;

class PriorityHandlerTest {
    private PriorityHandler handler;

    @BeforeEach
    void setUp() {
        handler = new PriorityHandler();
    }

    @Test
    void shouldDetectCompanyEmail() {
        Email email = new Email("boss@company.com", "Important Update", "Content");
        handler.handle(email);
        assertEquals("priority", email.getCategory());
    }

    @Test
    void shouldDetectClientEmail() {
        Email email = new Email("contact@client.com", "Meeting Request", "Content");
        handler.handle(email);
        assertEquals("priority", email.getCategory());
    }

    @Test
    void shouldNotMarkNormalEmailAsPriority() {
        Email email = new Email("someone@gmail.com", "Hello", "Content");
        handler.handle(email);
        assertEquals("inbox", email.getCategory());
    }
}
