package questions.lld.BookReader;

/**
 * Tracks a user's reading session on a specific book.
 */
public class ReadingSession {
    private final User user;
    private final Book book;
    private int currentPage;
    private int bookmark;

    public ReadingSession(User user, Book book) {
        this.user = user;
        this.book = book;
        this.currentPage = 1;
        this.bookmark = -1; // no bookmark
    }

    public String readCurrentPage() {
        return book.getPage(currentPage);
    }

    public boolean nextPage() {
        if (currentPage < book.getTotalPages()) {
            currentPage++;
            return true;
        }
        return false; // already at last page
    }

    public boolean previousPage() {
        if (currentPage > 1) {
            currentPage--;
            return true;
        }
        return false;
    }

    public void goToPage(int page) {
        if (page < 1 || page > book.getTotalPages()) {
            throw new IllegalArgumentException("Invalid page: " + page);
        }
        currentPage = page;
    }

    public void setBookmark() {
        this.bookmark = currentPage;
    }

    public void goToBookmark() {
        if (bookmark < 1) throw new IllegalStateException("No bookmark set");
        currentPage = bookmark;
    }

    public int getCurrentPage() { return currentPage; }
    public int getBookmark() { return bookmark; }
    public User getUser() { return user; }
    public Book getBook() { return book; }
    public double getProgress() {
        return (double) currentPage / book.getTotalPages() * 100;
    }
}
