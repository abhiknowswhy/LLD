package questions.lld.LRUCache;

/**
 * A node in the doubly-linked list backing the LRU cache.
 *
 * @param <K> key type
 * @param <V> value type
 */
class Node<K, V> {

    final K key;
    V value;
    Node<K, V> prev;
    Node<K, V> next;

    Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() { return key + "=" + value; }
}
