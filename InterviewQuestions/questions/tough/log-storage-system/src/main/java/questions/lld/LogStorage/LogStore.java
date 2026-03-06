package questions.lld.LogStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * In-memory log storage with capacity-based rotation.
 * When the store reaches capacity, the oldest entries are discarded.
 *
 * Supports filtering by level, source, time range, and keyword search.
 * Thread-safe via synchronized methods.
 */
public class LogStore {

    private final int maxCapacity;
    private final LinkedList<LogEntry> entries = new LinkedList<>();

    public LogStore(int maxCapacity) {
        if (maxCapacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        this.maxCapacity = maxCapacity;
    }

    /** Adds an entry, evicting the oldest if at capacity. */
    public synchronized void add(LogEntry entry) {
        if (entries.size() >= maxCapacity) {
            entries.removeFirst();
        }
        entries.addLast(entry);
    }

    /** Returns all stored entries. */
    public synchronized List<LogEntry> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(entries));
    }

    /** Returns entries at or above the given severity level. */
    public synchronized List<LogEntry> filter(LogLevel minLevel) {
        return entries.stream()
                .filter(e -> e.getLevel().isAtLeast(minLevel))
                .collect(Collectors.toList());
    }

    /** Returns entries from the given source. */
    public synchronized List<LogEntry> filterBySource(String source) {
        return entries.stream()
                .filter(e -> e.getSource().equals(source))
                .collect(Collectors.toList());
    }

    /** Returns entries within the given time range. */
    public synchronized List<LogEntry> filterByTimeRange(LocalDateTime from, LocalDateTime to) {
        return entries.stream()
                .filter(e -> !e.getTimestamp().isBefore(from) && !e.getTimestamp().isAfter(to))
                .collect(Collectors.toList());
    }

    /** Returns entries whose message contains the keyword (case-insensitive). */
    public synchronized List<LogEntry> search(String keyword) {
        String lower = keyword.toLowerCase();
        return entries.stream()
                .filter(e -> e.getMessage().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    /** Returns the number of stored entries. */
    public synchronized int size() { return entries.size(); }

    /** Clears all entries. */
    public synchronized void clear() { entries.clear(); }
}
