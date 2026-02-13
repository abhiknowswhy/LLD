package questions.lld.SearchAutocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Search autocomplete system (similar to Google search suggestions).
 *
 * Features:
 * - Record search queries with frequency tracking
 * - Return top-k suggestions for a given prefix, ranked by frequency
 * - Support interactive typing: call input(char) for each character
 * - '#' character signals end of search (records the query)
 *
 * Uses a Trie for O(L) prefix matching where L = prefix length.
 */
public class AutocompleteSystem {

    private final TrieNode root;
    private final int maxSuggestions;
    private StringBuilder currentInput;
    private TrieNode currentNode; // tracks position in trie as user types

    public AutocompleteSystem(int maxSuggestions) {
        this.root = new TrieNode();
        this.maxSuggestions = maxSuggestions;
        this.currentInput = new StringBuilder();
        this.currentNode = root;
    }

    public AutocompleteSystem() {
        this(5); // default top-5
    }

    /**
     * Pre-load sentences with their frequencies (historical data).
     */
    public void loadHistory(String[] sentences, int[] frequencies) {
        if (sentences.length != frequencies.length) {
            throw new IllegalArgumentException("Sentences and frequencies arrays must be same length");
        }
        for (int i = 0; i < sentences.length; i++) {
            addSentence(sentences[i], frequencies[i]);
        }
    }

    /**
     * Record a sentence with a given frequency.
     */
    public void addSentence(String sentence, int frequency) {
        if (sentence == null || sentence.isEmpty()) return;

        String normalized = sentence.toLowerCase();
        TrieNode current = root;
        for (char c : normalized.toCharArray()) {
            current = current.getOrCreateChild(c);
        }
        current.setEndOfSentence(true);
        current.setFrequency(current.getFrequency() + frequency);
    }

    /**
     * Process one character of input.
     * Returns top-k suggestions for the current prefix.
     * '#' signals end of input — records the query.
     */
    public List<Suggestion> input(char c) {
        if (c == '#') {
            // Record the completed search
            String query = currentInput.toString();
            if (!query.isEmpty()) {
                addSentence(query, 1);
            }
            // Reset for next search
            currentInput = new StringBuilder();
            currentNode = root;
            return Collections.emptyList();
        }

        currentInput.append(c);

        // Navigate trie
        if (currentNode != null) {
            currentNode = currentNode.hasChild(c) ? currentNode.getChild(c) : null;
        }

        if (currentNode == null) {
            return Collections.emptyList(); // no matches
        }

        // Collect all sentences under current node
        List<Suggestion> suggestions = new ArrayList<>();
        collectSuggestions(currentNode, new StringBuilder(currentInput), suggestions);
        Collections.sort(suggestions);

        // Return top-k
        return suggestions.subList(0, Math.min(maxSuggestions, suggestions.size()));
    }

    /**
     * One-shot search: find top-k suggestions for a complete prefix.
     */
    public List<Suggestion> search(String prefix) {
        if (prefix == null || prefix.isEmpty()) return Collections.emptyList();

        String normalized = prefix.toLowerCase();
        TrieNode node = root;
        for (char c : normalized.toCharArray()) {
            if (!node.hasChild(c)) return Collections.emptyList();
            node = node.getChild(c);
        }

        List<Suggestion> suggestions = new ArrayList<>();
        collectSuggestions(node, new StringBuilder(normalized), suggestions);
        Collections.sort(suggestions);

        return suggestions.subList(0, Math.min(maxSuggestions, suggestions.size()));
    }

    /**
     * Record a completed search query (increments frequency by 1).
     */
    public void recordSearch(String query) {
        addSentence(query, 1);
    }

    /**
     * Reset the current interactive input state.
     */
    public void resetInput() {
        currentInput = new StringBuilder();
        currentNode = root;
    }

    // --- Private helpers ---

    private void collectSuggestions(TrieNode node, StringBuilder prefix, List<Suggestion> results) {
        if (node.isEndOfSentence()) {
            results.add(new Suggestion(prefix.toString(), node.getFrequency()));
        }
        for (var entry : node.getChildren().entrySet()) {
            prefix.append(entry.getKey());
            collectSuggestions(entry.getValue(), prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
