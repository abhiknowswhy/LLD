package questions.lld.LogStorage;

/**
 * Log severity levels, ordered from least to most severe.
 */
public enum LogLevel {
    TRACE(0),
    DEBUG(1),
    INFO(2),
    WARN(3),
    ERROR(4),
    FATAL(5);

    private final int severity;

    LogLevel(int severity) { this.severity = severity; }

    public int getSeverity() { return severity; }

    public boolean isAtLeast(LogLevel other) { return this.severity >= other.severity; }
}
