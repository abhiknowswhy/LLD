package questions.lld.LogStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Logger that dispatches log entries to multiple appenders.
 * Each logger has a source name for identification.
 */
public class Logger {

    private final String source;
    private final List<LogAppender> appenders = new ArrayList<>();

    public Logger(String source) {
        if (source == null || source.isBlank()) throw new IllegalArgumentException("Source required");
        this.source = source;
    }

    public void addAppender(LogAppender appender) { appenders.add(appender); }

    public void log(LogLevel level, String message) {
        LogEntry entry = new LogEntry(level, source, message);
        for (LogAppender appender : appenders) {
            appender.append(entry);
        }
    }

    public void trace(String message) { log(LogLevel.TRACE, message); }
    public void debug(String message) { log(LogLevel.DEBUG, message); }
    public void info(String message)  { log(LogLevel.INFO, message); }
    public void warn(String message)  { log(LogLevel.WARN, message); }
    public void error(String message) { log(LogLevel.ERROR, message); }
    public void fatal(String message) { log(LogLevel.FATAL, message); }
}
