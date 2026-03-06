package questions.lld;

import questions.lld.MeetingScheduler.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Demonstrates a Meeting Room Scheduler System.
 *
 * Features:
 * - Multiple rooms with different capacities and amenities
 * - Booking with conflict detection (no double-booking)
 * - Cancel and modify bookings
 * - Find available rooms by time slot, capacity, and amenities
 * - View schedules per room or per user
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Meeting Room Scheduler Demo ===\n");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

        Scheduler scheduler = new Scheduler();

        // Add rooms
        Room roomA = new Room("Boardroom-A", 10, "projector", "whiteboard");
        Room roomB = new Room("Huddle-B", 4, "whiteboard");
        Room roomC = new Room("Conference-C", 20, "projector", "video-conference", "whiteboard");
        scheduler.addRoom(roomA);
        scheduler.addRoom(roomB);
        scheduler.addRoom(roomC);
        System.out.println("Registered " + scheduler.getRoomCount() + " rooms\n");

        // Define time slots (today)
        LocalDateTime t9 = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime t10 = t9.plusHours(1);
        LocalDateTime t11 = t9.plusHours(2);
        LocalDateTime t12 = t9.plusHours(3);
        LocalDateTime t13 = t9.plusHours(4);
        LocalDateTime t14 = t9.plusHours(5);

        // Book meetings
        System.out.println("--- Booking Meetings ---");
        Booking b1 = scheduler.bookRoom("Boardroom-A", "Alice", "Sprint Planning", t9, t10);
        Booking b2 = scheduler.bookRoom("Boardroom-A", "Bob", "Design Review", t10, t12);
        Booking b3 = scheduler.bookRoom("Huddle-B", "Carol", "1:1 Sync", t9, t10);
        Booking b4 = scheduler.bookRoom("Conference-C", "Dave", "All Hands", t11, t13);

        // Attempt conflicting booking
        System.out.println("\n--- Conflict Detection ---");
        Booking conflict = scheduler.bookRoom("Boardroom-A", "Eve", "Conflict Meeting", t9, t11);
        System.out.println("Conflicting booking result: " + (conflict == null ? "REJECTED" : "Accepted"));

        // Find available rooms
        System.out.println("\n--- Available rooms 13:00-14:00 ---");
        scheduler.findAvailableRooms(t13, t14, 1).forEach(r -> System.out.println("  " + r));

        System.out.println("\n--- Available rooms 9:00-10:00, capacity >= 8 ---");
        scheduler.findAvailableRooms(t9, t10, 8).forEach(r -> System.out.println("  " + r));

        System.out.println("\n--- Available rooms with projector, 11:00-12:00 ---");
        scheduler.findAvailableRooms(t11, t12, 1, "projector").forEach(r -> System.out.println("  " + r));

        // View schedules
        System.out.println("\n--- Boardroom-A Schedule ---");
        scheduler.getRoomSchedule("Boardroom-A").forEach(b ->
            System.out.println("  " + b.getStart().format(fmt) + "-" + b.getEnd().format(fmt) +
                    " | " + b.getTitle() + " (by " + b.getOrganizer() + ")"));

        System.out.println("\n--- Alice's Bookings ---");
        scheduler.getUserBookings("Alice").forEach(b ->
            System.out.println("  " + b.getRoomName() + " | " + b.getStart().format(fmt) + "-" +
                    b.getEnd().format(fmt) + " | " + b.getTitle()));

        // Cancel a booking
        System.out.println("\n--- Cancel Bob's Design Review ---");
        scheduler.cancelBooking(b2.getId());
        System.out.println("Boardroom-A schedule after cancellation:");
        scheduler.getRoomSchedule("Boardroom-A").forEach(b ->
            System.out.println("  " + b.getStart().format(fmt) + "-" + b.getEnd().format(fmt) +
                    " | " + b.getTitle()));

        System.out.println("\n=== Meeting Room Scheduler Demo Complete ===");
    }
}