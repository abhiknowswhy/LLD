package questions.lld;

import questions.lld.LRUCache.LRUCache;

/**
 * Demonstrates an LRU Cache built from scratch using a doubly-linked list
 * and a HashMap for O(1) get/put operations.
 *
 * Features:
 * - O(1) get and put
 * - Custom doubly-linked list (no Collections dependency)
 * - Automatic eviction of least-recently-used entry
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== LRU Cache Demo ===\n");

        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");
        System.out.println("Initial cache: " + cache);

        System.out.println("Get 1: " + cache.get(1));  // "One" — moves 1 to most recent
        System.out.println("After accessing 1: " + cache);

        cache.put(4, "Four"); // capacity exceeded → evicts 2 (least recently used)
        System.out.println("After inserting 4 (evicts 2): " + cache);

        System.out.println("Get 2: " + cache.get(2));  // null — evicted
        System.out.println("Get 3: " + cache.get(3));  // "Three"

        cache.put(5, "Five"); // evicts 1
        System.out.println("After inserting 5 (evicts 1): " + cache);

        cache.put(3, "THREE-UPDATED"); // update existing — moves to most recent
        System.out.println("After updating 3: " + cache);

        cache.remove(4);
        System.out.println("After removing 4: " + cache);

        System.out.println("Size: " + cache.size());

        System.out.println("\n=== LRU Cache Demo Complete ===");
    }
}