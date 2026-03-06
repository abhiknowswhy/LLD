package questions.lld.ChatServer;

import java.util.*;

/**
 * Represents a chat room that users can join, leave, and send messages to.
 * Uses Observer pattern — all members are notified of new messages.
 */
public class ChatRoom {

    private final String name;
    private final Set<User> members = new LinkedHashSet<>();
    private final List<Message> messageHistory = new ArrayList<>();

    public ChatRoom(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Room name required");
        this.name = name;
    }

    public String getName() { return name; }
    public Set<User> getMembers() { return Collections.unmodifiableSet(members); }
    public List<Message> getMessageHistory() { return Collections.unmodifiableList(messageHistory); }

    /** Adds a user to this room. */
    public void addMember(User user) {
        if (members.add(user)) {
            System.out.println("  [JOIN] " + user.getUsername() + " joined #" + name);
        }
    }

    /** Removes a user from this room. */
    public void removeMember(User user) {
        if (members.remove(user)) {
            System.out.println("  [LEAVE] " + user.getUsername() + " left #" + name);
        }
    }

    /** Sends a message from a member to all other members in the room. */
    public void sendMessage(User sender, String content) {
        if (!members.contains(sender)) {
            throw new IllegalStateException(sender.getUsername() + " is not a member of #" + name);
        }
        Message msg = new Message(sender.getUsername(), content, MessageType.ROOM);
        messageHistory.add(msg);
        System.out.println("  [#" + name + "] " + sender.getUsername() + ": " + content);
        for (User member : members) {
            if (!member.equals(sender)) {
                member.receive(msg);
            }
        }
    }

    @Override
    public String toString() { return "#" + name + " (" + members.size() + " members)"; }
}
