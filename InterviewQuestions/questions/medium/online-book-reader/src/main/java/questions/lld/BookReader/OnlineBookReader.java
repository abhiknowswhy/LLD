package questions.lld.BookReader;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Online Book Reader System: manages users, book catalog, and reading sessions.
 */
public class OnlineBookReader {
    private final Map<String, Book> bookCatalog;
    private final Map<String, User> users;
    // Key: "userId:bookId" → ReadingSession
    private final Map<String, ReadingSession> sessions;

    public OnlineBookReader() {
        this.bookCatalog = new LinkedHashMap<>();
        this.users = new LinkedHashMap<>();
        this.sessions = new HashMap<>();
    }

    // --- Book Catalog ---
    public void addBook(Book book) {
        bookCatalog.put(book.getBookId(), book);
    }

    public Book findBook(String bookId) {
        return bookCatalog.get(bookId);
    }

    public List<Book> searchByTitle(String query) {
        String q = query.toLowerCase();
        return bookCatalog.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String query) {
        String q = query.toLowerCase();
        return bookCatalog.values().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    // --- User Management ---
    public void registerUser(User user) {
        users.put(user.getUserId(), user);
    }

    public User findUser(String userId) {
        return users.get(userId);
    }

    // --- Reading Sessions ---
    public ReadingSession openBook(String userId, String bookId) {
        User user = users.get(userId);
        if (user == null) throw new IllegalArgumentException("User not found: " + userId);
        Book book = bookCatalog.get(bookId);
        if (book == null) throw new IllegalArgumentException("Book not found: " + bookId);

        String key = sessionKey(userId, bookId);
        ReadingSession session = sessions.get(key);
        if (session == null) {
            session = new ReadingSession(user, book);
            sessions.put(key, session);
        }
        return session;
    }

    public ReadingSession getSession(String userId, String bookId) {
        return sessions.get(sessionKey(userId, bookId));
    }

    public List<ReadingSession> getUserSessions(String userId) {
        String prefix = userId + ":";
        return sessions.entrySet().stream()
                .filter(e -> e.getKey().startsWith(prefix))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private String sessionKey(String userId, String bookId) {
        return userId + ":" + bookId;
    }

    public int getTotalBooks() { return bookCatalog.size(); }
    public int getTotalUsers() { return users.size(); }
    public Collection<Book> getAllBooks() { return bookCatalog.values(); }
}
