package questions.lld.LogStorage;

/**
 * Appender that prints log entries to the console.
 * Only prints entries at or above the configured minimum level.
 */
public class ConsoleAppender implements LogAppender {

    private final LogLevel minLevel;

    public ConsoleAppender(LogLevel minLevel) {
        this.minLevel = minLevel;
    }

    @Override
    public void append(LogEntry entry) {
        if (entry.getLevel().isAtLeast(minLevel)) {
            System.out.println("  " + entry);
        }
    }
}
