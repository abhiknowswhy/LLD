package questions.lld;

import questions.lld.Dictionary.Dictionary;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Dictionary (Trie) Demo ===\n");

        Dictionary dict = new Dictionary();

        // Add words
        dict.addWord("apple", "A round fruit with red or green skin");
        dict.addWord("application", "A program designed to fulfil a purpose");
        dict.addWord("apply", "To make a formal request");
        dict.addWord("banana", "A long curved yellow fruit");
        dict.addWord("band", "A group of musicians");
        dict.addWord("ban", "To officially prohibit");

        System.out.println("Dictionary size: " + dict.size());

        // Lookup
        System.out.println("\nLookup 'apple': " + dict.lookup("apple"));
        System.out.println("Lookup 'banana': " + dict.lookup("banana"));
        System.out.println("Lookup 'cherry': " + dict.lookup("cherry"));

        // Prefix search
        System.out.println("\nWords starting with 'app': " + dict.wordsWithPrefix("app"));
        System.out.println("Words starting with 'ban': " + dict.wordsWithPrefix("ban"));
        System.out.println("Words starting with 'z':   " + dict.wordsWithPrefix("z"));

        // Delete
        System.out.println("\nDeleting 'apply': " + dict.delete("apply"));
        System.out.println("Words starting with 'app': " + dict.wordsWithPrefix("app"));
        System.out.println("Dictionary size: " + dict.size());

        // All words
        System.out.println("\nAll words: " + dict.allWords());
    }
}