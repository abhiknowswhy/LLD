package questions.lld.LRUCache;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU (Least Recently Used) Cache with O(1) get and put operations.
 *
 * Implementation:
 * - HashMap for O(1) key → node lookup
 * - Doubly-linked list for O(1) order maintenance
 * - Head of list = LRU, Tail of list = MRU
 *
 * @param <K> key type
 * @param <V> value type
 */
public class LRUCache<K, V> {

    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private final DoublyLinkedList<K, V> list;

    public LRUCache(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
        this.list = new DoublyLinkedList<>();
    }

    /**
     * Returns the value for the given key, or null if not present.
     * Marks the entry as most-recently-used.
     */
    public V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) return null;
        list.moveToTail(node);
        return node.value;
    }

    /**
     * Inserts or updates a key-value pair.
     * If the cache is at capacity and a new key is inserted,
     * the least-recently-used entry is evicted.
     */
    public void put(K key, V value) {
        Node<K, V> existing = map.get(key);
        if (existing != null) {
            existing.value = value;
            list.moveToTail(existing);
            return;
        }
        if (map.size() >= capacity) {
            Node<K, V> evicted = list.removeHead();
            if (evicted != null) map.remove(evicted.key);
        }
        Node<K, V> newNode = new Node<>(key, value);
        list.addToTail(newNode);
        map.put(key, newNode);
    }

    /** Removes a key from the cache. Returns true if the key was present. */
    public boolean remove(K key) {
        Node<K, V> node = map.remove(key);
        if (node == null) return false;
        list.remove(node);
        return true;
    }

    /** Returns the number of entries in the cache. */
    public int size() { return map.size(); }

    /** Returns the maximum capacity of the cache. */
    public int capacity() { return capacity; }

    /** Returns whether the cache contains the given key. */
    public boolean containsKey(K key) { return map.containsKey(key); }

    @Override
    public String toString() {
        return "LRUCache(cap=" + capacity + ", size=" + map.size() + ") " + list;
    }
}
