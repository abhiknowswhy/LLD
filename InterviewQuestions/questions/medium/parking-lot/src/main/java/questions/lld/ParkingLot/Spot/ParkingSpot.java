package questions.lld.ParkingLot.Spot;

import questions.lld.ParkingLot.Vehicle.Vehicle;
import questions.lld.ParkingLot.Vehicle.VehicleType;

/**
 * A single parking spot that can hold one vehicle.
 */
public class ParkingSpot {
    private final int id;
    private final VehicleType spotType;
    private Vehicle parkedVehicle;

    public ParkingSpot(int id, VehicleType spotType) {
        this.id = id;
        this.spotType = spotType;
        this.parkedVehicle = null;
    }

    public int getId() { return id; }
    public VehicleType getSpotType() { return spotType; }
    public Vehicle getParkedVehicle() { return parkedVehicle; }
    public boolean isAvailable() { return parkedVehicle == null; }

    public boolean canFit(Vehicle vehicle) {
        if (!isAvailable()) return false;
        return spotType == vehicle.getType();
    }

    public void park(Vehicle vehicle) {
        if (!canFit(vehicle)) {
            throw new IllegalStateException("Cannot park " + vehicle + " in spot " + id);
        }
        this.parkedVehicle = vehicle;
    }

    public Vehicle unpark() {
        if (isAvailable()) {
            throw new IllegalStateException("Spot " + id + " is already empty");
        }
        Vehicle v = parkedVehicle;
        parkedVehicle = null;
        return v;
    }

    @Override
    public String toString() {
        return "Spot " + id + " (" + spotType + "): " + (isAvailable() ? "EMPTY" : parkedVehicle);
    }
}
