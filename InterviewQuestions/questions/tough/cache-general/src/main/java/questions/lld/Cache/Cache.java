package questions.lld.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A general-purpose, bounded, thread-safe cache with pluggable eviction
 * policies and optional per-entry TTL.
 *
 * Key design decisions:
 * - Strategy pattern for eviction (LRU, LFU, FIFO, etc.)
 * - ConcurrentHashMap + ReadWriteLock for thread safety
 * - Lazy TTL expiration (entries checked on access)
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class Cache<K, V> {

    private final int capacity;
    private final Map<K, CacheEntry<V>> store;
    private final EvictionPolicy<K> evictionPolicy;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Cache(int capacity, EvictionPolicy<K> evictionPolicy) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        if (evictionPolicy == null) throw new IllegalArgumentException("Eviction policy must not be null");
        this.capacity = capacity;
        this.store = new ConcurrentHashMap<>(capacity);
        this.evictionPolicy = evictionPolicy;
    }

    /** Retrieves a value by key; returns null if missing or expired. */
    public V get(K key) {
        lock.readLock().lock();
        try {
            CacheEntry<V> entry = store.get(key);
            if (entry == null) return null;
            if (entry.isExpired()) {
                // Upgrade to write lock for removal
                lock.readLock().unlock();
                lock.writeLock().lock();
                try {
                    store.remove(key);
                    evictionPolicy.onRemove(key);
                    return null;
                } finally {
                    lock.readLock().lock(); // downgrade
                    lock.writeLock().unlock();
                }
            }
            evictionPolicy.onAccess(key);
            return entry.getValue();
        } finally {
            lock.readLock().unlock();
        }
    }

    /** Inserts or updates a value with no TTL. */
    public void put(K key, V value) { put(key, value, 0); }

    /** Inserts or updates a value with a TTL in milliseconds (0 = no expiry). */
    public void put(K key, V value, long ttlMs) {
        lock.writeLock().lock();
        try {
            if (store.containsKey(key)) {
                store.put(key, new CacheEntry<>(value, ttlMs));
                evictionPolicy.onAccess(key);
                return;
            }
            if (store.size() >= capacity) {
                K victim = evictionPolicy.evict();
                if (victim != null) store.remove(victim);
            }
            store.put(key, new CacheEntry<>(value, ttlMs));
            evictionPolicy.onInsert(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /** Removes a key from the cache. Returns true if the key was present. */
    public boolean remove(K key) {
        lock.writeLock().lock();
        try {
            if (store.remove(key) != null) {
                evictionPolicy.onRemove(key);
                return true;
            }
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /** Returns the number of entries currently in the cache. */
    public int size() {
        lock.readLock().lock();
        try { return store.size(); }
        finally { lock.readLock().unlock(); }
    }

    /** Removes all entries from the cache. */
    public void clear() {
        lock.writeLock().lock();
        try {
            for (K key : store.keySet()) evictionPolicy.onRemove(key);
            store.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
