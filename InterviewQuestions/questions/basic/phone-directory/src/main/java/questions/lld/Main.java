package questions.lld;

import questions.lld.PhoneDirectory.Contact;
import questions.lld.PhoneDirectory.PhoneDirectory;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Phone Directory Demo ===\n");

        PhoneDirectory directory = new PhoneDirectory();

        // Add contacts
        directory.addContact(new Contact("Alice Johnson", "555-0101", "alice@email.com"));
        directory.addContact(new Contact("Alice Smith", "555-0102"));
        directory.addContact(new Contact("Bob Brown", "555-0201", "bob@email.com"));
        directory.addContact(new Contact("Charlie Davis", "555-0301"));
        directory.addContact(new Contact("Alice Williams", "555-0103"));

        System.out.println("Directory size: " + directory.size());

        // Exact lookup
        System.out.println("\nLookup 'Bob Brown': " + directory.findContact("Bob Brown"));
        System.out.println("Lookup 'Unknown': " + directory.findContact("Unknown"));

        // Reverse lookup
        System.out.println("\nReverse lookup '555-0201': " + directory.findByPhoneNumber("555-0201"));

        // Prefix search
        System.out.println("\nContacts starting with 'Alice':");
        for (Contact c : directory.searchByPrefix("Alice")) {
            System.out.println("  " + c);
        }

        // Delete
        directory.deleteContact("Alice Smith");
        System.out.println("\nAfter deleting 'Alice Smith':");
        for (Contact c : directory.searchByPrefix("Alice")) {
            System.out.println("  " + c);
        }

        // All contacts
        System.out.println("\nAll contacts:");
        for (Contact c : directory.allContacts()) {
            System.out.println("  " + c);
        }
    }
}