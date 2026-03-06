package questions.lld;

import questions.lld.Cache.*;

/**
 * Demonstrates a general-purpose cache system with pluggable eviction policies.
 *
 * Features:
 * - Pluggable eviction: LRU, LFU, FIFO
 * - TTL (time-to-live) support per entry
 * - Bounded capacity with automatic eviction
 * - Thread-safe operations
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== General Cache System Demo ===\n");

        // --- LRU Cache ---
        System.out.println("--- LRU Eviction Policy ---");
        Cache<String, String> lruCache = new Cache<>(3, new LRUEvictionPolicy<>());
        lruCache.put("A", "Apple");
        lruCache.put("B", "Banana");
        lruCache.put("C", "Cherry");
        System.out.println("Get A: " + lruCache.get("A"));   // "Apple" — makes A recently used
        lruCache.put("D", "Date");                             // evicts B (least recently used)
        System.out.println("Get B (evicted): " + lruCache.get("B")); // null
        System.out.println("Get C: " + lruCache.get("C"));
        System.out.println("Get D: " + lruCache.get("D"));
        System.out.println("Size: " + lruCache.size());

        // --- LFU Cache ---
        System.out.println("\n--- LFU Eviction Policy ---");
        Cache<String, String> lfuCache = new Cache<>(3, new LFUEvictionPolicy<>());
        lfuCache.put("X", "Xylophone");
        lfuCache.put("Y", "Yak");
        lfuCache.put("Z", "Zebra");
        lfuCache.get("X");  // freq 2
        lfuCache.get("X");  // freq 3
        lfuCache.get("Y");  // freq 2
        lfuCache.put("W", "Walrus"); // evicts Z (least frequently used, freq 1)
        System.out.println("Get Z (evicted): " + lfuCache.get("Z")); // null
        System.out.println("Get X: " + lfuCache.get("X"));
        System.out.println("Get Y: " + lfuCache.get("Y"));
        System.out.println("Get W: " + lfuCache.get("W"));

        // --- FIFO Cache ---
        System.out.println("\n--- FIFO Eviction Policy ---");
        Cache<String, String> fifoCache = new Cache<>(3, new FIFOEvictionPolicy<>());
        fifoCache.put("1", "One");
        fifoCache.put("2", "Two");
        fifoCache.put("3", "Three");
        fifoCache.get("1"); // does NOT affect eviction order in FIFO
        fifoCache.put("4", "Four"); // evicts "1" (first in)
        System.out.println("Get 1 (evicted): " + fifoCache.get("1")); // null
        System.out.println("Get 2: " + fifoCache.get("2"));
        System.out.println("Get 4: " + fifoCache.get("4"));

        // --- TTL Demo ---
        System.out.println("\n--- TTL Support ---");
        Cache<String, String> ttlCache = new Cache<>(5, new LRUEvictionPolicy<>());
        ttlCache.put("temp", "I expire soon", 500); // 500ms TTL
        ttlCache.put("perm", "I stay");
        System.out.println("Before expiry — temp: " + ttlCache.get("temp"));
        Thread.sleep(600);
        System.out.println("After expiry  — temp: " + ttlCache.get("temp")); // null
        System.out.println("After expiry  — perm: " + ttlCache.get("perm"));

        System.out.println("\n=== Cache System Demo Complete ===");
    }
}