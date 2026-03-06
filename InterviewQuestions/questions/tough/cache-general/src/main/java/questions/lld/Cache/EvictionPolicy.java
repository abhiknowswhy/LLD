package questions.lld.Cache;

/**
 * Strategy interface for cache eviction policies.
 * Implementations decide which key to evict when the cache is full.
 *
 * @param <K> the type of keys maintained by the cache
 */
public interface EvictionPolicy<K> {

    /** Called when a key is accessed (get or put). */
    void onAccess(K key);

    /** Called when a new key is inserted. */
    void onInsert(K key);

    /** Called when a key is removed or evicted. */
    void onRemove(K key);

    /** Returns the key that should be evicted next. */
    K evict();
}
