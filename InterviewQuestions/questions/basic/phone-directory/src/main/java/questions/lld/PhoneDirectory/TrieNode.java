package questions.lld.PhoneDirectory;

import java.util.HashMap;
import java.util.Map;

/**
 * Trie node for prefix-based contact search.
 */
public class TrieNode {
    private final Map<Character, TrieNode> children;
    private Contact contact; // non-null if this node marks end of a contact name

    public TrieNode() {
        this.children = new HashMap<>();
        this.contact = null;
    }

    public Map<Character, TrieNode> getChildren() { return children; }
    public Contact getContact() { return contact; }
    public void setContact(Contact contact) { this.contact = contact; }
    public boolean isEndOfName() { return contact != null; }
    public boolean hasChild(char c) { return children.containsKey(c); }
    public TrieNode getChild(char c) { return children.get(c); }
    public void addChild(char c, TrieNode node) { children.put(c, node); }
    public void removeChild(char c) { children.remove(c); }
    public boolean hasNoChildren() { return children.isEmpty(); }
}
