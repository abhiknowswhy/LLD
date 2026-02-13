package questions.lld.Dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * A Trie-based dictionary that supports:
 * - Add word with meaning
 * - Look up word and its meaning
 * - Delete a word
 * - Find all words with a given prefix (autocomplete)
 * - Check if any word starts with a prefix
 *
 * All operations are O(L) where L is the length of the word/prefix.
 */
public class Dictionary {

    private final TrieNode root;
    private int wordCount;

    public Dictionary() {
        this.root = new TrieNode();
        this.wordCount = 0;
    }

    /**
     * Add a word with its meaning to the dictionary.
     * If the word already exists, updates the meaning.
     */
    public void addWord(String word, String meaning) {
        validateWord(word);
        String normalized = word.toLowerCase();

        TrieNode current = root;
        for (char c : normalized.toCharArray()) {
            if (!current.hasChild(c)) {
                current.addChild(c, new TrieNode());
            }
            current = current.getChild(c);
        }

        if (!current.isEndOfWord()) {
            wordCount++;
        }
        current.setEndOfWord(true);
        current.setMeaning(meaning);
    }

    /**
     * Look up a word. Returns the meaning if found, null otherwise.
     */
    public String lookup(String word) {
        validateWord(word);
        TrieNode node = findNode(word.toLowerCase());
        if (node != null && node.isEndOfWord()) {
            return node.getMeaning();
        }
        return null;
    }

    /**
     * Check if a word exists in the dictionary.
     */
    public boolean contains(String word) {
        validateWord(word);
        TrieNode node = findNode(word.toLowerCase());
        return node != null && node.isEndOfWord();
    }

    /**
     * Check if any word starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        validateWord(prefix);
        return findNode(prefix.toLowerCase()) != null;
    }

    /**
     * Delete a word from the dictionary.
     * Returns true if the word was found and deleted.
     */
    public boolean delete(String word) {
        validateWord(word);
        boolean deleted = deleteHelper(root, word.toLowerCase(), 0);
        if (deleted) {
            wordCount--;
        }
        return deleted;
    }

    /**
     * Find all words that start with the given prefix (autocomplete).
     */
    public List<String> wordsWithPrefix(String prefix) {
        validateWord(prefix);
        String normalized = prefix.toLowerCase();
        List<String> results = new ArrayList<>();

        TrieNode prefixNode = findNode(normalized);
        if (prefixNode != null) {
            collectWords(prefixNode, new StringBuilder(normalized), results);
        }
        return results;
    }

    /**
     * Return all words in the dictionary.
     */
    public List<String> allWords() {
        List<String> results = new ArrayList<>();
        collectWords(root, new StringBuilder(), results);
        return results;
    }

    public int size() {
        return wordCount;
    }

    public boolean isEmpty() {
        return wordCount == 0;
    }

    // --- Private helpers ---

    private TrieNode findNode(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!current.hasChild(c)) {
                return null;
            }
            current = current.getChild(c);
        }
        return current;
    }

    /**
     * Recursive delete. Returns true if the word existed and was deleted.
     * Also prunes nodes that are no longer needed.
     */
    private boolean deleteHelper(TrieNode current, String word, int index) {
        if (index == word.length()) {
            if (!current.isEndOfWord()) {
                return false; // word doesn't exist
            }
            current.setEndOfWord(false);
            current.setMeaning(null);
            return true;
        }

        char c = word.charAt(index);
        TrieNode child = current.getChild(c);
        if (child == null) {
            return false;
        }

        boolean deleted = deleteHelper(child, word, index + 1);

        // Prune child if it's no longer needed
        if (deleted && !child.isEndOfWord() && child.hasNoChildren()) {
            current.removeChild(c);
        }
        return deleted;
    }

    /**
     * DFS to collect all words under a node.
     */
    private void collectWords(TrieNode node, StringBuilder prefix, List<String> results) {
        if (node.isEndOfWord()) {
            results.add(prefix.toString());
        }
        for (var entry : node.getChildren().entrySet()) {
            prefix.append(entry.getKey());
            collectWords(entry.getValue(), prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    private void validateWord(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Word cannot be null or empty");
        }
    }
}
