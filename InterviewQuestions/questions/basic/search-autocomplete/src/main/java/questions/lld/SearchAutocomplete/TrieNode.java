package questions.lld.SearchAutocomplete;

import java.util.HashMap;
import java.util.Map;

/**
 * Trie node for the autocomplete system.
 * Each node tracks the top-k most frequently searched terms passing through it.
 */
public class TrieNode {
    private final Map<Character, TrieNode> children;
    private boolean endOfSentence;
    private int frequency; // how many times this sentence was searched

    public TrieNode() {
        this.children = new HashMap<>();
        this.endOfSentence = false;
        this.frequency = 0;
    }

    public Map<Character, TrieNode> getChildren() { return children; }
    public boolean isEndOfSentence() { return endOfSentence; }
    public void setEndOfSentence(boolean end) { this.endOfSentence = end; }
    public int getFrequency() { return frequency; }
    public void setFrequency(int frequency) { this.frequency = frequency; }
    public void incrementFrequency() { this.frequency++; }

    public boolean hasChild(char c) { return children.containsKey(c); }
    public TrieNode getChild(char c) { return children.get(c); }
    public TrieNode getOrCreateChild(char c) {
        return children.computeIfAbsent(c, k -> new TrieNode());
    }
}
