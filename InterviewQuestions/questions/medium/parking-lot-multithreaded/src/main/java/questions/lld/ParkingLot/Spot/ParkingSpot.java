package questions.lld.ParkingLot.Spot;

import questions.lld.ParkingLot.Vehicle.Vehicle;
import questions.lld.ParkingLot.Vehicle.VehicleType;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Thread-safe parking spot using AtomicReference for lock-free CAS operations.
 */
public class ParkingSpot {
    private final int id;
    private final VehicleType spotType;
    private final AtomicReference<Vehicle> parkedVehicle;

    public ParkingSpot(int id, VehicleType spotType) {
        this.id = id;
        this.spotType = spotType;
        this.parkedVehicle = new AtomicReference<>(null);
    }

    public int getId() { return id; }
    public VehicleType getSpotType() { return spotType; }
    public boolean isAvailable() { return parkedVehicle.get() == null; }
    public Vehicle getParkedVehicle() { return parkedVehicle.get(); }

    /**
     * Atomically try to park a vehicle (CAS: null → vehicle).
     * Returns true if successfully parked.
     */
    public boolean tryPark(Vehicle vehicle) {
        if (spotType != vehicle.getType()) return false;
        return parkedVehicle.compareAndSet(null, vehicle);
    }

    /**
     * Atomically unpark (CAS: vehicle → null).
     */
    public Vehicle unpark() {
        return parkedVehicle.getAndSet(null);
    }

    @Override
    public String toString() {
        Vehicle v = parkedVehicle.get();
        return "Spot " + id + "(" + spotType + "): " + (v == null ? "EMPTY" : v);
    }
}
