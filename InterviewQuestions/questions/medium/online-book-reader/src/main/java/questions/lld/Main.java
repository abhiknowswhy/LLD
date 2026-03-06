package questions.lld;

import questions.lld.BookReader.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Online Book Reader Demo ===\n");

        OnlineBookReader reader = new OnlineBookReader();

        reader.addBook(new Book("B1", "Clean Code", "Robert Martin",
                List.of("Chapter 1: Clean Code", "Chapter 2: Meaningful Names", "Chapter 3: Functions")));
        reader.addBook(new Book("B2", "Design Patterns", "GoF",
                List.of("Introduction", "Creational Patterns", "Structural Patterns", "Behavioral Patterns")));

        reader.registerUser(new User("U1", "Alice"));

        ReadingSession session = reader.openBook("U1", "B1");
        System.out.println("Reading: " + session.getBook());
        System.out.println("Page 1: " + session.readCurrentPage());

        session.nextPage();
        System.out.println("Page 2: " + session.readCurrentPage());

        session.setBookmark();
        System.out.println("Bookmark set at page " + session.getBookmark());

        session.nextPage();
        System.out.println("Page 3: " + session.readCurrentPage());

        session.goToBookmark();
        System.out.println("Returned to bookmark, page: " + session.getCurrentPage());
        System.out.println("Progress: " + String.format("%.0f%%", session.getProgress()));
    }
}