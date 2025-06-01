package pattern.Handlers.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pattern.Email;
import static org.junit.jupiter.api.Assertions.*;

class PromotionsHandlerTest {
    private PromotionsHandler handler;

    @BeforeEach
    void setUp() {
        handler = new PromotionsHandler();
    }

    @Test
    void shouldDetectPromotionalEmail() {
        Email email = new Email("newsletter@marketing.com", "Weekly Updates", "Content");
        handler.handle(email);
        assertEquals("promotions", email.getCategory());
    }

    @Test
    void shouldDetectShoppingEmail() {
        Email email = new Email("deals@shopping.com", "New Products", "Content");
        handler.handle(email);
        assertEquals("promotions", email.getCategory());
    }

    @Test
    void shouldNotMarkNormalEmailAsPromotional() {
        Email email = new Email("user@company.com", "Project Update", "Content");
        handler.handle(email);
        assertEquals("inbox", email.getCategory());
    }
}
