package questions.lld.ChatServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a user in the chat system.
 * Tracks status, inbox for private messages, and room memberships.
 */
public class User {

    private final String username;
    private UserStatus status;
    private final List<Message> inbox = new ArrayList<>();

    public User(String username) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username required");
        this.username = username;
        this.status = UserStatus.ONLINE;
    }

    public String getUsername() { return username; }
    public UserStatus getStatus() { return status; }

    public void setStatus(UserStatus status) {
        if (status == null) throw new IllegalArgumentException("Status must not be null");
        this.status = status;
    }

    /** Delivers a message to this user's inbox. */
    void receive(Message message) {
        inbox.add(message);
    }

    /** Returns an unmodifiable view of this user's inbox. */
    public List<Message> getInbox() { return Collections.unmodifiableList(inbox); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return username.equals(other.username);
    }

    @Override
    public int hashCode() { return username.hashCode(); }

    @Override
    public String toString() { return username + " (" + status + ")"; }
}
