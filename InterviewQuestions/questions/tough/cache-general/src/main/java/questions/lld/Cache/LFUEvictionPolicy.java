package questions.lld.Cache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Least-Frequently-Used eviction policy.
 * Evicts the key with the lowest access frequency.
 * Ties are broken by evicting the oldest key at that frequency.
 *
 * @param <K> the type of keys
 */
public class LFUEvictionPolicy<K> implements EvictionPolicy<K> {

    private final Map<K, Integer> keyFreq = new HashMap<>();
    private final Map<Integer, LinkedHashSet<K>> freqKeys = new HashMap<>();
    private int minFreq = 0;

    @Override
    public void onAccess(K key) {
        if (!keyFreq.containsKey(key)) return;
        int oldFreq = keyFreq.get(key);
        int newFreq = oldFreq + 1;
        keyFreq.put(key, newFreq);

        freqKeys.get(oldFreq).remove(key);
        if (freqKeys.get(oldFreq).isEmpty()) {
            freqKeys.remove(oldFreq);
            if (minFreq == oldFreq) minFreq = newFreq;
        }
        freqKeys.computeIfAbsent(newFreq, k -> new LinkedHashSet<>()).add(key);
    }

    @Override
    public void onInsert(K key) {
        keyFreq.put(key, 1);
        freqKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        minFreq = 1;
    }

    @Override
    public void onRemove(K key) {
        if (!keyFreq.containsKey(key)) return;
        int freq = keyFreq.remove(key);
        freqKeys.get(freq).remove(key);
        if (freqKeys.get(freq).isEmpty()) {
            freqKeys.remove(freq);
        }
    }

    @Override
    public K evict() {
        LinkedHashSet<K> keys = freqKeys.get(minFreq);
        K victim = keys.iterator().next();
        keys.remove(victim);
        if (keys.isEmpty()) freqKeys.remove(minFreq);
        keyFreq.remove(victim);
        return victim;
    }
}
