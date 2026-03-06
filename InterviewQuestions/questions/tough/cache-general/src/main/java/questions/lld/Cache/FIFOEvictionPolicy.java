package questions.lld.Cache;

import java.util.LinkedList;
import java.util.Queue;

/**
 * First-In-First-Out eviction policy.
 * Evicts the key that was inserted earliest, regardless of access pattern.
 *
 * @param <K> the type of keys
 */
public class FIFOEvictionPolicy<K> implements EvictionPolicy<K> {

    private final Queue<K> insertionOrder = new LinkedList<>();

    @Override
    public void onAccess(K key) { /* FIFO ignores access */ }

    @Override
    public void onInsert(K key) { insertionOrder.add(key); }

    @Override
    public void onRemove(K key) { insertionOrder.remove(key); }

    @Override
    public K evict() { return insertionOrder.poll(); }
}
