package questions.lld.Cache;

/**
 * Wrapper for a cached value with optional TTL (time-to-live).
 *
 * @param <V> the type of the cached value
 */
class CacheEntry<V> {

    private final V value;
    private final long expiryTimeMs; // 0 means no expiry

    CacheEntry(V value, long ttlMs) {
        this.value = value;
        this.expiryTimeMs = (ttlMs > 0) ? System.currentTimeMillis() + ttlMs : 0;
    }

    V getValue() { return value; }

    boolean isExpired() {
        return expiryTimeMs > 0 && System.currentTimeMillis() > expiryTimeMs;
    }
}
