package questions.lld.FoodDeliverySystem;

/**
 * Represents a delivery agent in the food delivery system.
 * Tracks name, current geographic location (as a simple x,y coordinate),
 * and availability status.
 */
public class DeliveryAgent {
    private final String id;
    private final String name;
    private double locationX;
    private double locationY;
    private AgentStatus status;

    public DeliveryAgent(String id, String name, double locationX, double locationY) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("DeliveryAgent id must not be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("DeliveryAgent name must not be null or blank");
        }
        this.id = id;
        this.name = name;
        this.locationX = locationX;
        this.locationY = locationY;
        this.status = AgentStatus.AVAILABLE;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getLocationX() { return locationX; }
    public double getLocationY() { return locationY; }
    public AgentStatus getStatus() { return status; }

    public void setLocation(double x, double y) {
        this.locationX = x;
        this.locationY = y;
    }

    public void setStatus(AgentStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("AgentStatus must not be null");
        }
        this.status = status;
    }

    /**
     * Computes the Euclidean distance from this agent to the given coordinates.
     */
    public double distanceTo(double x, double y) {
        double dx = this.locationX - x;
        double dy = this.locationY - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return String.format("DeliveryAgent{%s, '%s', loc=(%.1f,%.1f), %s}",
                id, name, locationX, locationY, status);
    }
}
