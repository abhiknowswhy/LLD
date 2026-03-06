package questions.lld.LogStorage;

/**
 * Strategy interface for log appenders.
 * Different appenders handle log entries differently (console, memory, file, etc.).
 */
public interface LogAppender {

    /** Appends a log entry to this appender's destination. */
    void append(LogEntry entry);
}
