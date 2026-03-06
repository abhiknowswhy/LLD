package questions.lld.ChatServer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a message sent in the chat system.
 * Immutable value object.
 */
public class Message {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final String senderName;
    private final String content;
    private final LocalDateTime timestamp;
    private final MessageType type;

    public Message(String senderName, String content, MessageType type) {
        if (senderName == null || senderName.isBlank()) throw new IllegalArgumentException("Sender required");
        if (content == null || content.isBlank()) throw new IllegalArgumentException("Content required");
        this.senderName = senderName;
        this.content = content;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public String getSenderName() { return senderName; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public MessageType getType() { return type; }

    @Override
    public String toString() {
        return "[" + timestamp.format(FMT) + "] " + senderName + ": " + content;
    }
}
