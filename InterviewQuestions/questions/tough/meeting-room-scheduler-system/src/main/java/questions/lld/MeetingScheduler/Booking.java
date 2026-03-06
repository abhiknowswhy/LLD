package questions.lld.MeetingScheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Represents a single room booking with a unique ID, organizer, title, and time slot.
 */
public class Booking {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String id;
    private final String roomName;
    private final String organizer;
    private final String title;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Booking(String roomName, String organizer, String title, LocalDateTime start, LocalDateTime end) {
        if (roomName == null || roomName.isBlank()) throw new IllegalArgumentException("Room name required");
        if (organizer == null || organizer.isBlank()) throw new IllegalArgumentException("Organizer required");
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title required");
        if (start == null || end == null) throw new IllegalArgumentException("Start and end times required");
        if (!end.isAfter(start)) throw new IllegalArgumentException("End must be after start");
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.roomName = roomName;
        this.organizer = organizer;
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public String getId() { return id; }
    public String getRoomName() { return roomName; }
    public String getOrganizer() { return organizer; }
    public String getTitle() { return title; }
    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }

    /** Returns true if this booking overlaps with the given time range. */
    public boolean overlaps(LocalDateTime otherStart, LocalDateTime otherEnd) {
        return start.isBefore(otherEnd) && end.isAfter(otherStart);
    }

    @Override
    public String toString() {
        return "[" + id + "] " + roomName + " | " + start.format(FMT) + " - " + end.format(FMT) +
                " | " + title + " (by " + organizer + ")";
    }
}
