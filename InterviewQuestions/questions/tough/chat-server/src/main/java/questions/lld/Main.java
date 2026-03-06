package questions.lld;

import questions.lld.ChatServer.*;

/**
 * Demonstrates a Chat Server system with rooms, private messages, and user management.
 *
 * Features:
 * - User registration and status management
 * - Chat rooms with join/leave/broadcast
 * - Private (direct) messaging between users
 * - Message history per room and per user
 * - Observer pattern for message delivery
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Chat Server Demo ===\n");

        ChatServer server = new ChatServer();

        // Register users
        User alice = server.registerUser("Alice");
        User bob = server.registerUser("Bob");
        User carol = server.registerUser("Carol");
        User dave = server.registerUser("Dave");
        System.out.println("Registered " + server.getUserCount() + " users\n");

        // Create and join rooms
        ChatRoom general = server.createRoom("general");
        ChatRoom techTalk = server.createRoom("tech-talk");

        general.addMember(alice);
        general.addMember(bob);
        general.addMember(carol);
        techTalk.addMember(alice);
        techTalk.addMember(dave);

        System.out.println();

        // Broadcast in rooms
        System.out.println("--- Room Messages ---");
        general.sendMessage(alice, "Hello everyone in general!");
        general.sendMessage(bob, "Hey Alice!");
        techTalk.sendMessage(dave, "Anyone here for tech-talk?");
        techTalk.sendMessage(alice, "I'm here!");

        // Private messages
        System.out.println("\n--- Private Messages ---");
        server.sendPrivateMessage(bob, carol, "Hey Carol, want to join tech-talk?");
        server.sendPrivateMessage(carol, bob, "Sure, let me join!");

        // Carol joins tech-talk
        techTalk.addMember(carol);
        techTalk.sendMessage(carol, "Just joined tech-talk!");

        // Message history
        System.out.println("\n--- Message History ---");
        System.out.println("General room history:");
        general.getMessageHistory().forEach(m -> System.out.println("  " + m));
        System.out.println("Tech-talk room history:");
        techTalk.getMessageHistory().forEach(m -> System.out.println("  " + m));

        // User goes offline
        System.out.println("\n--- User Status ---");
        alice.setStatus(UserStatus.AWAY);
        System.out.println("Alice status: " + alice.getStatus());
        bob.setStatus(UserStatus.OFFLINE);
        System.out.println("Bob status: " + bob.getStatus());

        // Leave room
        general.removeMember(bob);

        System.out.println("\n=== Chat Server Demo Complete ===");
    }
}