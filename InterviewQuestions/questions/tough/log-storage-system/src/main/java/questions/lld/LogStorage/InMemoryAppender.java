package questions.lld.LogStorage;

/**
 * Appender that stores log entries in an in-memory LogStore.
 */
public class InMemoryAppender implements LogAppender {

    private final LogStore store;

    public InMemoryAppender(LogStore store) {
        this.store = store;
    }

    @Override
    public void append(LogEntry entry) {
        store.add(entry);
    }
}
