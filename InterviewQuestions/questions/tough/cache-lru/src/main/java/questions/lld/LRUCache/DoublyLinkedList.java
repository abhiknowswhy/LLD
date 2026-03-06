package questions.lld.LRUCache;

/**
 * Doubly-linked list with sentinel head and tail nodes.
 * Supports O(1) insertion at tail, removal of any node, and removal from head.
 *
 * @param <K> key type
 * @param <V> value type
 */
class DoublyLinkedList<K, V> {

    private final Node<K, V> head; // sentinel
    private final Node<K, V> tail; // sentinel

    DoublyLinkedList() {
        head = new Node<>(null, null);
        tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    /** Appends a node to the tail (most-recently-used end). */
    void addToTail(Node<K, V> node) {
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
    }

    /** Removes a node from the list in O(1). */
    void remove(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
    }

    /** Removes and returns the head node (least-recently-used). Returns null if empty. */
    Node<K, V> removeHead() {
        if (head.next == tail) return null; // empty
        Node<K, V> first = head.next;
        remove(first);
        return first;
    }

    /** Moves an existing node to the tail (marks as most-recently-used). */
    void moveToTail(Node<K, V> node) {
        remove(node);
        addToTail(node);
    }

    boolean isEmpty() { return head.next == tail; }

    /** Returns a string representation of the list from head (LRU) to tail (MRU). */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<K, V> curr = head.next;
        while (curr != tail) {
            sb.append(curr);
            if (curr.next != tail) sb.append(", ");
            curr = curr.next;
        }
        return sb.append("]").toString();
    }
}
