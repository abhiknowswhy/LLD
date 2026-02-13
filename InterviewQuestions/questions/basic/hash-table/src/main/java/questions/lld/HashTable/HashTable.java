package questions.lld.HashTable;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic hash table using separate chaining for collision resolution.
 *
 * Features:
 * - O(1) average put/get/remove
 * - Automatic resizing when load factor threshold is exceeded
 * - Separate chaining with linked list buckets
 * - Supports null values (but not null keys)
 */
public class HashTable<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] buckets;
    private int size;
    private final float loadFactor;

    public HashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashTable(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Load factor must be positive");
        }
        this.buckets = new Entry[initialCapacity];
        this.loadFactor = loadFactor;
        this.size = 0;
    }

    /**
     * Put a key-value pair. Updates value if key already exists.
     * Returns the previous value, or null if key was new.
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }

        if ((float) (size + 1) / buckets.length > loadFactor) {
            resize();
        }

        int index = getBucketIndex(key);
        Entry<K, V> current = buckets[index];

        // Check if key already exists — update in place
        while (current != null) {
            if (current.getKey().equals(key)) {
                V oldValue = current.getValue();
                current.setValue(value);
                return oldValue;
            }
            current = current.getNext();
        }

        // Key not found — prepend new entry to bucket
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.setNext(buckets[index]);
        buckets[index] = newEntry;
        size++;
        return null;
    }

    /**
     * Get the value associated with a key.
     * Returns null if key not found.
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }

        int index = getBucketIndex(key);
        Entry<K, V> current = buckets[index];

        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Remove a key-value pair. Returns the removed value, or null if key not found.
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }

        int index = getBucketIndex(key);
        Entry<K, V> current = buckets[index];
        Entry<K, V> prev = null;

        while (current != null) {
            if (current.getKey().equals(key)) {
                if (prev == null) {
                    buckets[index] = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                size--;
                return current.getValue();
            }
            prev = current;
            current = current.getNext();
        }
        return null;
    }

    /**
     * Check if the hash table contains a key.
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Return all keys in the hash table.
     */
    public List<K> keys() {
        List<K> allKeys = new ArrayList<>();
        for (Entry<K, V> bucket : buckets) {
            Entry<K, V> current = bucket;
            while (current != null) {
                allKeys.add(current.getKey());
                current = current.getNext();
            }
        }
        return allKeys;
    }

    /**
     * Return all values in the hash table.
     */
    public List<V> values() {
        List<V> allValues = new ArrayList<>();
        for (Entry<K, V> bucket : buckets) {
            Entry<K, V> current = bucket;
            while (current != null) {
                allValues.add(current.getValue());
                current = current.getNext();
            }
        }
        return allValues;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
    public int capacity() { return buckets.length; }

    /**
     * Compute bucket index for a key using modulo hashing.
     */
    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    /**
     * Double the bucket array and rehash all entries.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldBuckets = buckets;
        buckets = new Entry[oldBuckets.length * 2];
        size = 0;

        for (Entry<K, V> bucket : oldBuckets) {
            Entry<K, V> current = bucket;
            while (current != null) {
                put(current.getKey(), current.getValue());
                current = current.getNext();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Entry<K, V> bucket : buckets) {
            Entry<K, V> current = bucket;
            while (current != null) {
                if (!first) sb.append(", ");
                sb.append(current.getKey()).append("=").append(current.getValue());
                first = false;
                current = current.getNext();
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
