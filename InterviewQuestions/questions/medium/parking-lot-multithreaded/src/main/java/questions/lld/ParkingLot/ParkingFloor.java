package questions.lld.ParkingLot;

import questions.lld.ParkingLot.Spot.ParkingSpot;
import questions.lld.ParkingLot.Vehicle.Vehicle;
import questions.lld.ParkingLot.Vehicle.VehicleType;

import java.util.List;
import java.util.ArrayList;

public class ParkingFloor {
    private final int floorNumber;
    private final List<ParkingSpot> spots;

    public ParkingFloor(int floorNumber, int motorcycleSpots, int carSpots, int truckSpots) {
        this.floorNumber = floorNumber;
        this.spots = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < motorcycleSpots; i++) spots.add(new ParkingSpot(id++, VehicleType.MOTORCYCLE));
        for (int i = 0; i < carSpots; i++) spots.add(new ParkingSpot(id++, VehicleType.CAR));
        for (int i = 0; i < truckSpots; i++) spots.add(new ParkingSpot(id++, VehicleType.TRUCK));
    }

    public int getFloorNumber() { return floorNumber; }

    /**
     * Try to park via CAS — thread-safe without explicit locks.
     */
    public ParkingSpot tryPark(Vehicle vehicle) {
        for (ParkingSpot spot : spots) {
            if (spot.tryPark(vehicle)) {
                return spot;
            }
        }
        return null;
    }

    public ParkingSpot getSpotById(int spotId) {
        for (ParkingSpot spot : spots) {
            if (spot.getId() == spotId) return spot;
        }
        return null;
    }
}
