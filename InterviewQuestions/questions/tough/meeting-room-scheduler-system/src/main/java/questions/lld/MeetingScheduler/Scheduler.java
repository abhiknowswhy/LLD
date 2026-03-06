package questions.lld.MeetingScheduler;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Meeting Room Scheduler that manages rooms, bookings, and availability.
 *
 * Design:
 * - Rooms stored by name for fast lookup
 * - Bookings stored per room for conflict detection
 * - Supports filtering by capacity, amenities, and time range
 * - Thread-safe via synchronized methods
 */
public class Scheduler {

    private final Map<String, Room> rooms = new LinkedHashMap<>();
    private final Map<String, List<Booking>> bookingsByRoom = new LinkedHashMap<>();
    private final Map<String, Booking> bookingsById = new LinkedHashMap<>();

    /** Registers a room with the scheduler. */
    public void addRoom(Room room) {
        if (rooms.containsKey(room.getName())) {
            throw new IllegalArgumentException("Room '" + room.getName() + "' already exists");
        }
        rooms.put(room.getName(), room);
        bookingsByRoom.put(room.getName(), new ArrayList<>());
    }

    public int getRoomCount() { return rooms.size(); }

    /**
     * Books a room. Returns the Booking if successful, or null if there's a conflict.
     */
    public synchronized Booking bookRoom(String roomName, String organizer, String title,
                                          LocalDateTime start, LocalDateTime end) {
        if (!rooms.containsKey(roomName)) throw new IllegalArgumentException("Room not found: " + roomName);

        List<Booking> roomBookings = bookingsByRoom.get(roomName);
        for (Booking existing : roomBookings) {
            if (existing.overlaps(start, end)) {
                System.out.println("  [CONFLICT] " + roomName + " is booked " +
                        existing.getStart() + "-" + existing.getEnd() + " by " + existing.getOrganizer());
                return null;
            }
        }

        Booking booking = new Booking(roomName, organizer, title, start, end);
        roomBookings.add(booking);
        bookingsById.put(booking.getId(), booking);
        System.out.println("  [BOOKED] " + booking);
        return booking;
    }

    /** Cancels a booking by its ID. */
    public synchronized void cancelBooking(String bookingId) {
        Booking booking = bookingsById.remove(bookingId);
        if (booking == null) throw new IllegalArgumentException("Booking not found: " + bookingId);
        bookingsByRoom.get(booking.getRoomName()).remove(booking);
        System.out.println("  [CANCELLED] " + booking);
    }

    /** Returns all bookings for a specific room, sorted by start time. */
    public List<Booking> getRoomSchedule(String roomName) {
        if (!bookingsByRoom.containsKey(roomName)) throw new IllegalArgumentException("Room not found: " + roomName);
        return bookingsByRoom.get(roomName).stream()
                .sorted(Comparator.comparing(Booking::getStart))
                .collect(Collectors.toList());
    }

    /** Returns all bookings for a specific user. */
    public List<Booking> getUserBookings(String organizer) {
        return bookingsById.values().stream()
                .filter(b -> b.getOrganizer().equals(organizer))
                .sorted(Comparator.comparing(Booking::getStart))
                .collect(Collectors.toList());
    }

    /** Finds rooms available during the given time slot with minimum capacity. */
    public List<Room> findAvailableRooms(LocalDateTime start, LocalDateTime end, int minCapacity, String... requiredAmenities) {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms.values()) {
            if (room.getCapacity() < minCapacity) continue;
            if (!room.hasAllAmenities(requiredAmenities)) continue;
            boolean conflict = bookingsByRoom.get(room.getName()).stream()
                    .anyMatch(b -> b.overlaps(start, end));
            if (!conflict) available.add(room);
        }
        return available;
    }
}
