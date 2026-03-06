package questions.lld;

import questions.lld.LogStorage.*;

import java.time.LocalDateTime;

/**
 * Demonstrates a Log Storage System with structured logging, search, and rotation.
 *
 * Features:
 * - Log levels: TRACE, DEBUG, INFO, WARN, ERROR, FATAL
 * - Structured log entries with timestamps, source, and message
 * - In-memory log storage with capacity-based rotation
 * - Filtering by level, source, time range, and keyword
 * - Multiple appenders (Console, InMemory) with Strategy pattern
 * - Thread-safe logging
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Log Storage System Demo ===\n");

        // Create log store with max 20 entries (small for demo)
        LogStore store = new LogStore(20);

        // Create logger with console + in-memory appenders
        Logger logger = new Logger("AppService");
        logger.addAppender(new ConsoleAppender(LogLevel.INFO));  // console shows INFO+
        logger.addAppender(new InMemoryAppender(store));          // store captures everything

        // Log various messages
        logger.trace("Entering method doWork()");
        logger.debug("Cache miss for key=user:42");
        logger.info("Application started successfully");
        logger.info("Processing request #1001");
        logger.warn("Response time exceeded 500ms");
        logger.error("Failed to connect to database");
        logger.info("Request #1001 completed");
        logger.debug("Cache hit for key=user:42");
        logger.info("Processing request #1002");
        logger.error("NullPointerException in PaymentService");
        logger.fatal("Out of memory — shutting down");

        // Second logger
        Logger dbLogger = new Logger("DatabaseService");
        dbLogger.addAppender(new InMemoryAppender(store));
        dbLogger.info("Connection pool initialized with 10 connections");
        dbLogger.warn("Connection pool utilization at 90%");
        dbLogger.error("Query timeout after 30s");

        // Search and filter
        System.out.println("\n--- All stored logs (" + store.size() + " entries) ---");
        store.getAll().forEach(e -> System.out.println("  " + e));

        System.out.println("\n--- Filter: ERROR and above ---");
        store.filter(LogLevel.ERROR).forEach(e -> System.out.println("  " + e));

        System.out.println("\n--- Filter: source=DatabaseService ---");
        store.filterBySource("DatabaseService").forEach(e -> System.out.println("  " + e));

        System.out.println("\n--- Search: keyword='request' ---");
        store.search("request").forEach(e -> System.out.println("  " + e));

        System.out.println("\n--- Filter: last 2 seconds ---");
        LocalDateTime twoSecsAgo = LocalDateTime.now().minusSeconds(2);
        store.filterByTimeRange(twoSecsAgo, LocalDateTime.now()).forEach(e -> System.out.println("  " + e));

        // Log rotation demo
        System.out.println("\n--- Log rotation (adding 15 more entries to 20-cap store) ---");
        for (int i = 0; i < 15; i++) {
            logger.info("Bulk log entry #" + i);
        }
        System.out.println("Store size after rotation: " + store.size());

        System.out.println("\n=== Log Storage System Demo Complete ===");
    }
}