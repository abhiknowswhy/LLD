package questions.lld.Dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * A node in the Trie. Each node represents one character
 * and can have children for subsequent characters.
 */
public class TrieNode {
    private final Map<Character, TrieNode> children;
    private boolean endOfWord;
    private String meaning;

    public TrieNode() {
        this.children = new HashMap<>();
        this.endOfWord = false;
        this.meaning = null;
    }

    public Map<Character, TrieNode> getChildren() { return children; }
    public boolean isEndOfWord() { return endOfWord; }
    public void setEndOfWord(boolean endOfWord) { this.endOfWord = endOfWord; }
    public String getMeaning() { return meaning; }
    public void setMeaning(String meaning) { this.meaning = meaning; }

    public boolean hasChild(char c) { return children.containsKey(c); }
    public TrieNode getChild(char c) { return children.get(c); }
    public void addChild(char c, TrieNode node) { children.put(c, node); }
    public void removeChild(char c) { children.remove(c); }
    public boolean hasNoChildren() { return children.isEmpty(); }
}
