package questions.lld.PhoneDirectory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A phone directory that supports:
 * - Add/update/delete contacts
 * - Lookup by exact name
 * - Search by name prefix (autocomplete)
 * - Reverse lookup by phone number
 * - List all contacts sorted alphabetically
 *
 * Uses a Trie for O(L) name-based operations and a HashMap for O(1) reverse lookup.
 */
public class PhoneDirectory {

    private final TrieNode root;
    private final Map<String, Contact> phoneIndex; // phone number -> contact (reverse lookup)
    private int contactCount;

    public PhoneDirectory() {
        this.root = new TrieNode();
        this.phoneIndex = new HashMap<>();
        this.contactCount = 0;
    }

    /**
     * Add a new contact or update an existing one.
     */
    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }

        String normalized = contact.getName().toLowerCase();

        // Remove old phone index if updating
        Contact existing = findContact(contact.getName());
        if (existing != null) {
            phoneIndex.remove(existing.getPhoneNumber());
        } else {
            contactCount++;
        }

        TrieNode current = root;
        for (char c : normalized.toCharArray()) {
            if (!current.hasChild(c)) {
                current.addChild(c, new TrieNode());
            }
            current = current.getChild(c);
        }
        current.setContact(contact);
        phoneIndex.put(contact.getPhoneNumber(), contact);
    }

    /**
     * Find a contact by exact name.
     */
    public Contact findContact(String name) {
        if (name == null || name.isEmpty()) return null;

        TrieNode node = findNode(name.toLowerCase());
        if (node != null && node.isEndOfName()) {
            return node.getContact();
        }
        return null;
    }

    /**
     * Reverse lookup: find contact by phone number.
     */
    public Contact findByPhoneNumber(String phoneNumber) {
        return phoneIndex.get(phoneNumber);
    }

    /**
     * Delete a contact by name.
     */
    public boolean deleteContact(String name) {
        if (name == null || name.isEmpty()) return false;

        Contact contact = findContact(name);
        if (contact == null) return false;

        phoneIndex.remove(contact.getPhoneNumber());
        deleteHelper(root, name.toLowerCase(), 0);
        contactCount--;
        return true;
    }

    /**
     * Search contacts by name prefix.
     */
    public List<Contact> searchByPrefix(String prefix) {
        if (prefix == null || prefix.isEmpty()) return Collections.emptyList();

        String normalized = prefix.toLowerCase();
        TrieNode prefixNode = findNode(normalized);

        if (prefixNode == null) return Collections.emptyList();

        List<Contact> results = new ArrayList<>();
        collectContacts(prefixNode, results);
        Collections.sort(results);
        return results;
    }

    /**
     * Get all contacts sorted alphabetically.
     */
    public List<Contact> allContacts() {
        List<Contact> results = new ArrayList<>();
        collectContacts(root, results);
        Collections.sort(results);
        return results;
    }

    public int size() { return contactCount; }
    public boolean isEmpty() { return contactCount == 0; }

    // --- Private helpers ---

    private TrieNode findNode(String name) {
        TrieNode current = root;
        for (char c : name.toCharArray()) {
            if (!current.hasChild(c)) return null;
            current = current.getChild(c);
        }
        return current;
    }

    private boolean deleteHelper(TrieNode current, String name, int index) {
        if (index == name.length()) {
            if (!current.isEndOfName()) return false;
            current.setContact(null);
            return true;
        }

        char c = name.charAt(index);
        TrieNode child = current.getChild(c);
        if (child == null) return false;

        boolean deleted = deleteHelper(child, name, index + 1);
        if (deleted && !child.isEndOfName() && child.hasNoChildren()) {
            current.removeChild(c);
        }
        return deleted;
    }

    private void collectContacts(TrieNode node, List<Contact> results) {
        if (node.isEndOfName()) {
            results.add(node.getContact());
        }
        for (TrieNode child : node.getChildren().values()) {
            collectContacts(child, results);
        }
    }
}
