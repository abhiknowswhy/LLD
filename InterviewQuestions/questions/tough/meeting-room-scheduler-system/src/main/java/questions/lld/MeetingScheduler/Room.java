package questions.lld.MeetingScheduler;

import java.util.*;

/**
 * Represents a meeting room with a name, capacity, and set of amenities.
 */
public class Room {

    private final String name;
    private final int capacity;
    private final Set<String> amenities;

    public Room(String name, int capacity, String... amenities) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Room name required");
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        this.name = name;
        this.capacity = capacity;
        this.amenities = new LinkedHashSet<>(Arrays.asList(amenities));
    }

    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public Set<String> getAmenities() { return Collections.unmodifiableSet(amenities); }

    public boolean hasAmenity(String amenity) { return amenities.contains(amenity); }

    public boolean hasAllAmenities(String... required) {
        for (String a : required) {
            if (!amenities.contains(a)) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + " (cap=" + capacity + ", amenities=" + amenities + ")";
    }
}
