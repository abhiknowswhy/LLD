package questions.lld.Library;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private final String memberId;
    private final String name;
    private final List<BookLoan> activeLoans;
    private static final int MAX_BOOKS = 5;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.activeLoans = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public List<BookLoan> getActiveLoans() { return activeLoans; }
    public int getActiveLoanCount() { return activeLoans.size(); }
    public boolean canBorrow() { return activeLoans.size() < MAX_BOOKS; }
    public static int getMaxBooks() { return MAX_BOOKS; }

    public void addLoan(BookLoan loan) {
        activeLoans.add(loan);
    }

    public void removeLoan(BookLoan loan) {
        activeLoans.remove(loan);
    }

    @Override
    public String toString() {
        return String.format("Member{id='%s', name='%s', loans=%d}", memberId, name, activeLoans.size());
    }
}
