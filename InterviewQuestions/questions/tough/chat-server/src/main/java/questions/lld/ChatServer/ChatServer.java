package questions.lld.ChatServer;

import java.util.*;

/**
 * Central chat server that manages users, rooms, and private messaging.
 *
 * Design:
 * - Mediator pattern: all communication goes through the server
 * - Observer pattern: room members are notified of messages
 * - Users can join rooms and send private messages
 */
public class ChatServer {

    private final Map<String, User> users = new LinkedHashMap<>();
    private final Map<String, ChatRoom> rooms = new LinkedHashMap<>();

    /** Registers a new user with the given username. */
    public User registerUser(String username) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Username '" + username + "' already taken");
        }
        User user = new User(username);
        users.put(username, user);
        System.out.println("  [REGISTER] " + username);
        return user;
    }

    /** Looks up a user by username. */
    public User getUser(String username) { return users.get(username); }

    /** Returns the total number of registered users. */
    public int getUserCount() { return users.size(); }

    /** Creates a new chat room. */
    public ChatRoom createRoom(String roomName) {
        if (rooms.containsKey(roomName)) {
            throw new IllegalArgumentException("Room '" + roomName + "' already exists");
        }
        ChatRoom room = new ChatRoom(roomName);
        rooms.put(roomName, room);
        System.out.println("  [ROOM] Created #" + roomName);
        return room;
    }

    /** Looks up a room by name. */
    public ChatRoom getRoom(String roomName) { return rooms.get(roomName); }

    /** Sends a private message from one user to another. */
    public void sendPrivateMessage(User sender, User recipient, String content) {
        if (sender == null || recipient == null) throw new IllegalArgumentException("Sender and recipient required");
        Message msg = new Message(sender.getUsername(), content, MessageType.PRIVATE);
        recipient.receive(msg);
        sender.receive(msg); // sender also keeps a copy
        System.out.println("  [DM] " + sender.getUsername() + " → " + recipient.getUsername() + ": " + content);
    }

    /** Returns all registered usernames. */
    public Collection<String> listUsers() { return Collections.unmodifiableSet(users.keySet()); }

    /** Returns all room names. */
    public Collection<String> listRooms() { return Collections.unmodifiableSet(rooms.keySet()); }
}
