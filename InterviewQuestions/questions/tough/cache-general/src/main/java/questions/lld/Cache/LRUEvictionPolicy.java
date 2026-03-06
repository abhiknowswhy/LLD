package questions.lld.Cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Least-Recently-Used eviction policy.
 * Evicts the key that has not been accessed for the longest time.
 *
 * Uses a {@link LinkedHashMap} in access-order mode to track recency.
 *
 * @param <K> the type of keys
 */
public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {

    private final LinkedHashMap<K, Boolean> accessOrder = new LinkedHashMap<>(16, 0.75f, true);

    @Override
    public void onAccess(K key) { accessOrder.put(key, Boolean.TRUE); }

    @Override
    public void onInsert(K key) { accessOrder.put(key, Boolean.TRUE); }

    @Override
    public void onRemove(K key) { accessOrder.remove(key); }

    @Override
    public K evict() {
        Map.Entry<K, Boolean> eldest = accessOrder.entrySet().iterator().next();
        K key = eldest.getKey();
        accessOrder.remove(key);
        return key;
    }
}
