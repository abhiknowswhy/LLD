package questions.lld.ParkingLot;

import questions.lld.ParkingLot.Spot.ParkingSpot;
import questions.lld.ParkingLot.Vehicle.Vehicle;
import questions.lld.ParkingLot.Vehicle.VehicleType;

import java.util.ArrayList;
import java.util.List;

/**
 * A single floor in a multi-story parking lot.
 */
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

    public ParkingSpot findAvailableSpot(VehicleType type) {
        for (ParkingSpot spot : spots) {
            if (spot.getSpotType() == type && spot.isAvailable()) {
                return spot;
            }
        }
        return null;
    }

    public int getAvailableCount(VehicleType type) {
        int count = 0;
        for (ParkingSpot spot : spots) {
            if (spot.getSpotType() == type && spot.isAvailable()) count++;
        }
        return count;
    }

    public ParkingSpot getSpotById(int spotId) {
        for (ParkingSpot spot : spots) {
            if (spot.getId() == spotId) return spot;
        }
        return null;
    }

    public void display() {
        System.out.println("  Floor " + floorNumber + ":");
        for (ParkingSpot spot : spots) {
            System.out.println("    " + spot);
        }
    }
}
