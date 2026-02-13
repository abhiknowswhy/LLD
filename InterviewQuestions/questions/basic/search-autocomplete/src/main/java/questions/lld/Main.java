package questions.lld;

import questions.lld.SearchAutocomplete.AutocompleteSystem;
import questions.lld.SearchAutocomplete.Suggestion;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Search Autocomplete Demo ===\n");

        AutocompleteSystem system = new AutocompleteSystem(3);

        // Load historical data
        String[] sentences = {
            "i love you", "island", "iroman", "i love leetcode",
            "java programming", "java design patterns", "javascript",
            "design patterns", "data structures"
        };
        int[] frequencies = {5, 3, 2, 2, 4, 3, 1, 6, 4};
        system.loadHistory(sentences, frequencies);

        // One-shot search
        System.out.println("Search 'i':");
        printSuggestions(system.search("i"));

        System.out.println("\nSearch 'java':");
        printSuggestions(system.search("java"));

        System.out.println("\nSearch 'de':");
        printSuggestions(system.search("de"));

        // Interactive input simulation
        System.out.println("\n--- Interactive Mode ---");
        System.out.println("Typing 'i':");
        printSuggestions(system.input('i'));

        System.out.println("Typing ' ':");
        printSuggestions(system.input(' '));

        System.out.println("Typing 'l':");
        printSuggestions(system.input('l'));

        // End search with '#'
        System.out.println("Submitting search...");
        system.input('o');
        system.input('v');
        system.input('e');
        system.input('#');

        // Now "i love" has been searched, frequency updated
        System.out.println("\nSearch 'i lo' after recording:");
        printSuggestions(system.search("i lo"));
    }

    private static void printSuggestions(List<Suggestion> suggestions) {
        if (suggestions.isEmpty()) {
            System.out.println("  (no suggestions)");
        }
        for (Suggestion s : suggestions) {
            System.out.println("  → " + s);
        }
    }
}