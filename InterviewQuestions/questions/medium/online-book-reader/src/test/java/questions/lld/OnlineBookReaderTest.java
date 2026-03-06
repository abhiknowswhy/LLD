package questions.lld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.lld.BookReader.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OnlineBookReaderTest {

    private OnlineBookReader system;

    @BeforeEach
    void setUp() {
        system = new OnlineBookReader();
        system.addBook(new Book("B1", "Clean Code", "Robert Martin",
                List.of("Page 1", "Page 2", "Page 3", "Page 4", "Page 5")));
        system.addBook(new Book("B2", "Design Patterns", "GoF",
                List.of("Intro", "Creational", "Structural")));
        system.registerUser(new User("U1", "Alice"));
        system.registerUser(new User("U2", "Bob"));
    }

    @Test
    void testBookCatalog() {
        assertEquals(2, system.getTotalBooks());
        assertNotNull(system.findBook("B1"));
    }

    @Test
    void testSearchByTitle() {
        List<Book> results = system.searchByTitle("clean");
        assertEquals(1, results.size());
        assertEquals("Clean Code", results.get(0).getTitle());
    }

    @Test
    void testSearchByAuthor() {
        List<Book> results = system.searchByAuthor("gof");
        assertEquals(1, results.size());
    }

    @Test
    void testOpenBook() {
        ReadingSession session = system.openBook("U1", "B1");
        assertNotNull(session);
        assertEquals(1, session.getCurrentPage());
        assertEquals("Page 1", session.readCurrentPage());
    }

    @Test
    void testNavigatePages() {
        ReadingSession session = system.openBook("U1", "B1");
        assertTrue(session.nextPage());
        assertEquals(2, session.getCurrentPage());
        assertEquals("Page 2", session.readCurrentPage());

        assertTrue(session.previousPage());
        assertEquals(1, session.getCurrentPage());
    }

    @Test
    void testCannotGoPastFirstPage() {
        ReadingSession session = system.openBook("U1", "B1");
        assertFalse(session.previousPage());
        assertEquals(1, session.getCurrentPage());
    }

    @Test
    void testCannotGoPastLastPage() {
        ReadingSession session = system.openBook("U1", "B1");
        for (int i = 0; i < 4; i++) session.nextPage();
        assertEquals(5, session.getCurrentPage());
        assertFalse(session.nextPage());
    }

    @Test
    void testGoToPage() {
        ReadingSession session = system.openBook("U1", "B1");
        session.goToPage(3);
        assertEquals(3, session.getCurrentPage());
        assertEquals("Page 3", session.readCurrentPage());
    }

    @Test
    void testInvalidPage() {
        ReadingSession session = system.openBook("U1", "B1");
        assertThrows(IllegalArgumentException.class, () -> session.goToPage(0));
        assertThrows(IllegalArgumentException.class, () -> session.goToPage(6));
    }

    @Test
    void testBookmark() {
        ReadingSession session = system.openBook("U1", "B1");
        session.goToPage(3);
        session.setBookmark();
        assertEquals(3, session.getBookmark());

        session.goToPage(5);
        session.goToBookmark();
        assertEquals(3, session.getCurrentPage());
    }

    @Test
    void testNoBookmarkSet() {
        ReadingSession session = system.openBook("U1", "B1");
        assertThrows(IllegalStateException.class, session::goToBookmark);
    }

    @Test
    void testProgress() {
        ReadingSession session = system.openBook("U1", "B1");
        assertEquals(20.0, session.getProgress(), 0.1); // 1/5 = 20%
        session.goToPage(5);
        assertEquals(100.0, session.getProgress(), 0.1);
    }

    @Test
    void testSessionPersistence() {
        ReadingSession s1 = system.openBook("U1", "B1");
        s1.goToPage(3);

        // Reopen same book — should get same session
        ReadingSession s2 = system.openBook("U1", "B1");
        assertEquals(3, s2.getCurrentPage());
        assertSame(s1, s2);
    }

    @Test
    void testMultipleUserSessions() {
        system.openBook("U1", "B1");
        system.openBook("U1", "B2");
        List<ReadingSession> sessions = system.getUserSessions("U1");
        assertEquals(2, sessions.size());
    }

    @Test
    void testInvalidUser() {
        assertThrows(IllegalArgumentException.class, () -> system.openBook("INVALID", "B1"));
    }
}
