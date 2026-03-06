package questions.lld.Library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookLoan {
    private final Book book;
    private final Member member;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private static final int LOAN_PERIOD_DAYS = 14;
    private static final double FINE_PER_DAY = 0.50;

    public BookLoan(Book book, Member member) {
        this.book = book;
        this.member = member;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(LOAN_PERIOD_DAYS);
    }

    public BookLoan(Book book, Member member, LocalDate borrowDate) {
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(LOAN_PERIOD_DAYS);
    }

    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    public void returnBook() {
        this.returnDate = LocalDate.now();
    }

    public void returnBook(LocalDate date) {
        this.returnDate = date;
    }

    public boolean isOverdue() {
        LocalDate checkDate = returnDate != null ? returnDate : LocalDate.now();
        return checkDate.isAfter(dueDate);
    }

    public double calculateFine() {
        LocalDate checkDate = returnDate != null ? returnDate : LocalDate.now();
        if (!checkDate.isAfter(dueDate)) return 0.0;
        long overdueDays = ChronoUnit.DAYS.between(dueDate, checkDate);
        return overdueDays * FINE_PER_DAY;
    }

    @Override
    public String toString() {
        return String.format("Loan{book='%s', member='%s', due=%s}", book.getTitle(), member.getName(), dueDate);
    }
}
