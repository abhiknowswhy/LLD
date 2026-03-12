# Chat Server

## Problem Statement
Design a chat server system supporting user registration, chat rooms with join/leave/broadcast, private messaging, and message history.

## Requirements
- User registration with unique usernames and status management (Online, Away, Busy, Offline)
- Chat rooms that users can join, leave, and send messages to
- Private (direct) messaging between users
- Message history per room
- User inbox for received messages

## Key Design Decisions
- **Mediator Pattern** — `ChatServer` acts as a central coordinator for all communication
- **Observer Pattern** — room members are notified when messages are sent to the room
- **Immutable messages** — `Message` objects are value objects with timestamps
- **Message types** — `ROOM` vs `PRIVATE` differentiate broadcast from direct messages
- **LinkedHashSet for members** — preserves join order while preventing duplicates

## Class Diagram

```mermaid
classDiagram
    class ChatServer {
        -Map~String, User~ users
        -Map~String, ChatRoom~ rooms
        +registerUser(String username) User
        +createRoom(String roomName) ChatRoom
        +sendPrivateMessage(User sender, User recipient, String content) void
        +getUser(String username) User
        +getRoom(String roomName) ChatRoom
        +getUserCount() int
    }

    class ChatRoom {
        -String name
        -Set~User~ members
        -List~Message~ messageHistory
        +addMember(User user) void
        +removeMember(User user) void
        +sendMessage(User sender, String content) void
        +getMessageHistory() List~Message~
    }

    class User {
        -String username
        -UserStatus status
        -List~Message~ inbox
        +setStatus(UserStatus status) void
        +receive(Message message) void
        +getInbox() List~Message~
    }

    class Message {
        -String senderName
        -String content
        -LocalDateTime timestamp
        -MessageType type
    }

    class MessageType {
        <<enumeration>>
        ROOM
        PRIVATE
    }

    class UserStatus {
        <<enumeration>>
        ONLINE
        AWAY
        BUSY
        OFFLINE
    }

    ChatServer --> User : manages
    ChatServer --> ChatRoom : manages
    ChatRoom --> User : has members
    ChatRoom --> Message : stores history
    User --> Message : has inbox
    User --> UserStatus : has
    Message --> MessageType : has
```

## Design Benefits
- ✅ **Mediator Pattern** — centralized communication reduces coupling between users and rooms
- ✅ **Observer Pattern** — room members automatically notified of new messages
- ✅ **Clean separation** — users, rooms, and messages are independent entities
- ✅ **Message history** — both rooms and users maintain their own message records
- ✅ **Status management** — users can indicate availability

## Potential Discussion Points
- How would you add real-time WebSocket-based message delivery?
- How to implement message encryption for private messages?
- How to handle offline message delivery (store-and-forward)?
- How to add admin roles and moderation (mute, kick, ban)?
- How would you scale this to millions of concurrent users?
