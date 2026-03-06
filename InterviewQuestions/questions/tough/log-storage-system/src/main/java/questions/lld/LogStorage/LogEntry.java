package questions.lld.LogStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Immutable structured log entry.
 */
public class LogEntry {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private final LocalDateTime timestamp;
    private final LogLevel level;
    private final String source;
    private final String message;

    public LogEntry(LogLevel level, String source, String message) {
        this.timestamp = LocalDateTime.now();
        this.level = level;
        this.source = source;
        this.message = message;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public LogLevel getLevel() { return level; }
    public String getSource() { return source; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return String.format("[%s] %-5s [%s] %s", timestamp.format(FMT), level, source, message);
    }
}
