package questions.lld.ParkingLot.Vehicle;

public class Vehicle {
    private final String licensePlate;
    private final VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() { return licensePlate; }
    public VehicleType getType() { return type; }

    @Override
    public String toString() { return type + "[" + licensePlate + "]"; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle v)) return false;
        return licensePlate.equals(v.licensePlate);
    }

    @Override
    public int hashCode() { return licensePlate.hashCode(); }
}
