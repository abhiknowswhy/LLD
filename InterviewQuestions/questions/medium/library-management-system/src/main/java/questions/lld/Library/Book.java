package questions.lld.Library;

public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private BookStatus status;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.status = BookStatus.AVAILABLE;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }

    public boolean isAvailable() { return status == BookStatus.AVAILABLE; }

    @Override
    public String toString() {
        return String.format("Book{isbn='%s', title='%s', author='%s', status=%s}", isbn, title, author, status);
    }
}
