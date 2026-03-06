package questions.lld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.lld.Library.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library("Test Library");
        library.addBook(new Book("ISBN-1", "Clean Code", "Robert Martin"));
        library.addBook(new Book("ISBN-2", "Design Patterns", "Gang of Four"));
        library.addBook(new Book("ISBN-3", "Clean Architecture", "Robert Martin"));
        library.registerMember(new Member("M1", "Alice"));
        library.registerMember(new Member("M2", "Bob"));
    }

    @Test
    void testAddAndFindBook() {
        Book book = library.findBookByIsbn("ISBN-1");
        assertNotNull(book);
        assertEquals("Clean Code", book.getTitle());
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }

    @Test
    void testSearchByTitle() {
        List<Book> results = library.searchByTitle("clean");
        assertEquals(2, results.size()); // Clean Code + Clean Architecture
    }

    @Test
    void testSearchByAuthor() {
        List<Book> results = library.searchByAuthor("robert");
        assertEquals(2, results.size());
    }

    @Test
    void testBorrowBook() {
        BookLoan loan = library.borrowBook("M1", "ISBN-1");
        assertNotNull(loan);
        assertEquals("Clean Code", loan.getBook().getTitle());
        assertEquals(BookStatus.CHECKED_OUT, library.findBookByIsbn("ISBN-1").getStatus());
        assertEquals(1, library.findMember("M1").getActiveLoanCount());
    }

    @Test
    void testReturnBookNoFine() {
        library.borrowBook("M1", "ISBN-1");
        double fine = library.returnBook("M1", "ISBN-1");
        assertEquals(0.0, fine);
        assertEquals(BookStatus.AVAILABLE, library.findBookByIsbn("ISBN-1").getStatus());
        assertEquals(0, library.findMember("M1").getActiveLoanCount());
    }

    @Test
    void testBorrowUnavailableBook() {
        library.borrowBook("M1", "ISBN-1");
        assertThrows(IllegalStateException.class, () -> library.borrowBook("M2", "ISBN-1"));
    }

    @Test
    void testBorrowLimit() {
        // Add more books for this test
        for (int i = 10; i < 16; i++) {
            library.addBook(new Book("ISBN-" + i, "Book " + i, "Author"));
        }
        // Borrow 5 books (max)
        for (int i = 10; i < 15; i++) {
            library.borrowBook("M1", "ISBN-" + i);
        }
        assertEquals(5, library.findMember("M1").getActiveLoanCount());
        assertThrows(IllegalStateException.class, () -> library.borrowBook("M1", "ISBN-15"));
    }

    @Test
    void testOverdueLoanDetection() {
        // Create a loan with a past borrow date (30 days ago, so 16 days overdue)
        Book book = library.findBookByIsbn("ISBN-1");
        Member member = library.findMember("M1");
        BookLoan loan = new BookLoan(book, member, LocalDate.now().minusDays(30));
        book.setStatus(BookStatus.CHECKED_OUT);
        member.addLoan(loan);

        assertTrue(loan.isOverdue());
        assertTrue(loan.calculateFine() > 0);
    }

    @Test
    void testFineCalculation() {
        Book book = library.findBookByIsbn("ISBN-1");
        Member member = library.findMember("M1");
        // Borrowed 20 days ago (14 day period, so 6 days overdue), returned today
        BookLoan loan = new BookLoan(book, member, LocalDate.now().minusDays(20));
        loan.returnBook(LocalDate.now());
        // 6 overdue days * $0.50 = $3.00
        assertEquals(3.0, loan.calculateFine(), 0.001);
    }

    @Test
    void testAvailableBooks() {
        assertEquals(3, library.getAvailableBooks().size());
        library.borrowBook("M1", "ISBN-1");
        assertEquals(2, library.getAvailableBooks().size());
    }

    @Test
    void testInvalidMember() {
        assertThrows(IllegalArgumentException.class, () -> library.borrowBook("INVALID", "ISBN-1"));
    }

    @Test
    void testInvalidBook() {
        assertThrows(IllegalArgumentException.class, () -> library.borrowBook("M1", "INVALID"));
    }
}
