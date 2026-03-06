package questions.lld;

import questions.lld.Library.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Library Management System Demo ===\n");

        Library library = new Library("City Library");

        // Add books
        library.addBook(new Book("978-0-13-468599-1", "The Pragmatic Programmer", "Andy Hunt"));
        library.addBook(new Book("978-0-201-63361-0", "Design Patterns", "Gang of Four"));
        library.addBook(new Book("978-0-596-51774-8", "JavaScript: The Good Parts", "Douglas Crockford"));

        // Register members
        library.registerMember(new Member("M001", "Alice"));
        library.registerMember(new Member("M002", "Bob"));

        System.out.println("Books in library: " + library.getTotalBooks());
        System.out.println("Members: " + library.getTotalMembers());

        // Borrow a book
        BookLoan loan = library.borrowBook("M001", "978-0-13-468599-1");
        System.out.println("\n" + loan);

        // Search
        System.out.println("\nSearch 'Design': " + library.searchByTitle("Design"));
        System.out.println("Available books: " + library.getAvailableBooks().size());

        // Return with fine
        double fine = library.returnBook("M001", "978-0-13-468599-1");
        System.out.println("\nFine on return: $" + fine);
        System.out.println("Available books after return: " + library.getAvailableBooks().size());
    }
}