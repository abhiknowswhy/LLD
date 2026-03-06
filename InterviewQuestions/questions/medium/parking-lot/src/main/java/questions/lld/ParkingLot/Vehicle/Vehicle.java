package questions.lld.ParkingLot.Vehicle;

/**
 * Represents a vehicle that can park in the lot.
 */
public class Vehicle {
    private final String licensePlate;
    private final VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        if (licensePlate == null || licensePlate.isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() { return licensePlate; }
    public VehicleType getType() { return type; }

    @Override
    public String toString() {
        return type + " [" + licensePlate + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle other)) return false;
        return licensePlate.equals(other.licensePlate);
    }

    @Override
    public int hashCode() { return licensePlate.hashCode(); }
}
