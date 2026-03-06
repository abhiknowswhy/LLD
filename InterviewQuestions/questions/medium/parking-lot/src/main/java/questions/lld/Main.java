package questions.lld;

import questions.lld.ParkingLot.ParkingLot;
import questions.lld.ParkingLot.ParkingTicket;
import questions.lld.ParkingLot.Vehicle.Vehicle;
import questions.lld.ParkingLot.Vehicle.VehicleType;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Parking Lot Demo ===\n");

        ParkingLot.resetInstance();
        ParkingLot lot = ParkingLot.getInstance("Downtown Parking");
        lot.addFloor(5, 10, 3);
        lot.addFloor(5, 10, 3);

        Vehicle car1 = new Vehicle("CAR-001", VehicleType.CAR);
        Vehicle bike1 = new Vehicle("BIKE-001", VehicleType.MOTORCYCLE);
        Vehicle truck1 = new Vehicle("TRUCK-001", VehicleType.TRUCK);

        ParkingTicket t1 = lot.parkVehicle(car1);
        ParkingTicket t2 = lot.parkVehicle(bike1);
        ParkingTicket t3 = lot.parkVehicle(truck1);

        System.out.println("Parked: " + t1);
        System.out.println("Parked: " + t2);
        System.out.println("Parked: " + t3);

        lot.displayStatus();

        double fee = lot.unparkVehicle(t1.getTicketId());
        System.out.println("\nUnparked " + car1 + " — Fee: $" + String.format("%.2f", fee));
        System.out.println("Active tickets: " + lot.getActiveTicketCount());
    }
}