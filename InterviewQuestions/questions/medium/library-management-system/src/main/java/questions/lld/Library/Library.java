package questions.lld.Library;

import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private final String name;
    private final Map<String, Book> bookCatalog;       // isbn -> Book
    private final Map<String, Member> members;          // memberId -> Member
    private final List<BookLoan> allLoans;

    public Library(String name) {
        this.name = name;
        this.bookCatalog = new HashMap<>();
        this.members = new HashMap<>();
        this.allLoans = new ArrayList<>();
    }

    // --- Book Management ---
    public void addBook(Book book) {
        bookCatalog.put(book.getIsbn(), book);
    }

    public Book findBookByIsbn(String isbn) {
        return bookCatalog.get(isbn);
    }

    public List<Book> searchByTitle(String titleQuery) {
        String query = titleQuery.toLowerCase();
        return bookCatalog.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String authorQuery) {
        String query = authorQuery.toLowerCase();
        return bookCatalog.values().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    public List<Book> getAvailableBooks() {
        return bookCatalog.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    // --- Member Management ---
    public void registerMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    public Member findMember(String memberId) {
        return members.get(memberId);
    }

    // --- Lending Operations ---
    public BookLoan borrowBook(String memberId, String isbn) {
        Member member = members.get(memberId);
        if (member == null) throw new IllegalArgumentException("Member not found: " + memberId);

        Book book = bookCatalog.get(isbn);
        if (book == null) throw new IllegalArgumentException("Book not found: " + isbn);
        if (!book.isAvailable()) throw new IllegalStateException("Book is not available: " + book.getTitle());
        if (!member.canBorrow()) throw new IllegalStateException("Member has reached borrowing limit");

        book.setStatus(BookStatus.CHECKED_OUT);
        BookLoan loan = new BookLoan(book, member);
        member.addLoan(loan);
        allLoans.add(loan);
        return loan;
    }

    public double returnBook(String memberId, String isbn) {
        Member member = members.get(memberId);
        if (member == null) throw new IllegalArgumentException("Member not found: " + memberId);

        BookLoan loan = member.getActiveLoans().stream()
                .filter(l -> l.getBook().getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No active loan found for book: " + isbn));

        loan.returnBook();
        loan.getBook().setStatus(BookStatus.AVAILABLE);
        member.removeLoan(loan);
        return loan.calculateFine();
    }

    // --- Queries ---
    public List<BookLoan> getOverdueLoans() {
        return allLoans.stream()
                .filter(l -> l.getReturnDate() == null && l.isOverdue())
                .collect(Collectors.toList());
    }

    public String getName() { return name; }
    public int getTotalBooks() { return bookCatalog.size(); }
    public int getTotalMembers() { return members.size(); }
    public Collection<Book> getAllBooks() { return bookCatalog.values(); }
}
