package questions.lld.BookReader;

import java.util.*;

public class Book {
    private final String bookId;
    private final String title;
    private final String author;
    private final List<String> pages; // each string is a page's content

    public Book(String bookId, String title, String author, List<String> pages) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.pages = new ArrayList<>(pages);
    }

    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getTotalPages() { return pages.size(); }

    public String getPage(int pageNumber) {
        if (pageNumber < 1 || pageNumber > pages.size()) {
            throw new IllegalArgumentException("Invalid page number: " + pageNumber);
        }
        return pages.get(pageNumber - 1);
    }

    @Override
    public String toString() {
        return String.format("'%s' by %s (%d pages)", title, author, pages.size());
    }
}
