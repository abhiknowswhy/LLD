# Log Storage System

## Problem Statement
Design a structured logging system with multiple log levels, pluggable appenders, in-memory storage with rotation, and search/filter capabilities.

## Requirements
- Log levels: TRACE, DEBUG, INFO, WARN, ERROR, FATAL (ordered by severity)
- Structured log entries with timestamp, level, source, and message
- Multiple appenders — Console (with minimum level filter) and InMemory
- In-memory storage with capacity-based rotation (oldest entries discarded)
- Filter by level, source, time range, and keyword search
- Thread-safe logging

## Key Design Decisions
- **Strategy Pattern** — `LogAppender` interface allows pluggable log destinations (console, memory, file, etc.)
- **Logger dispatches to appenders** — each logger has a source name and fans out entries to all registered appenders
- **ConsoleAppender with minimum level** — only prints entries at or above a threshold (e.g., INFO+)
- **InMemoryAppender with LogStore** — stores entries in a bounded `LinkedList` for search/filter
- **Capacity-based rotation** — when the store reaches max capacity, oldest entries are evicted (ring buffer behavior)
- **Synchronized methods** — `LogStore` is thread-safe for concurrent logging

## Class Diagram

```mermaid
classDiagram
    class Logger {
        -String source
        -List~LogAppender~ appenders
        +addAppender(LogAppender appender) void
        +log(LogLevel level, String message) void
        +trace(String message) void
        +debug(String message) void
        +info(String message) void
        +warn(String message) void
        +error(String message) void
        +fatal(String message) void
    }

    class LogAppender {
        <<interface>>
        +append(LogEntry entry) void
    }

    class ConsoleAppender {
        -LogLevel minLevel
        +append(LogEntry entry) void
    }

    class InMemoryAppender {
        -LogStore store
        +append(LogEntry entry) void
    }

    class LogStore {
        -int maxCapacity
        -LinkedList~LogEntry~ entries
        +add(LogEntry entry) void
        +getAll() List~LogEntry~
        +filter(LogLevel minLevel) List~LogEntry~
        +filterBySource(String source) List~LogEntry~
        +filterByTimeRange(LocalDateTime from, LocalDateTime to) List~LogEntry~
        +search(String keyword) List~LogEntry~
        +size() int
        +clear() void
    }

    class LogEntry {
        -LocalDateTime timestamp
        -LogLevel level
        -String source
        -String message
    }

    class LogLevel {
        <<enumeration>>
        TRACE
        DEBUG
        INFO
        WARN
        ERROR
        FATAL
        +getSeverity() int
        +isAtLeast(LogLevel other) boolean
    }

    Logger --> LogAppender : dispatches to
    ConsoleAppender ..|> LogAppender
    InMemoryAppender ..|> LogAppender
    InMemoryAppender --> LogStore : writes to
    LogStore --> LogEntry : stores
    Logger --> LogEntry : creates
    LogEntry --> LogLevel : has
```

## Design Benefits
- ✅ **Strategy Pattern** — easily add new appenders (file, database, remote) without modifying Logger
- ✅ **Level-based filtering** — ConsoleAppender suppresses low-severity logs
- ✅ **Capacity-based rotation** — bounded memory usage with automatic oldest-entry eviction
- ✅ **Rich query API** — filter by level, source, time range, and keyword
- ✅ **Thread-safe** — synchronized LogStore methods for concurrent access

## Potential Discussion Points
- How would you add asynchronous logging (non-blocking log calls)?
- How to implement log aggregation across multiple services?
- How to add structured fields (key-value metadata) to log entries?
- How would you implement a rolling file appender?
- How to add log sampling for high-throughput systems?
