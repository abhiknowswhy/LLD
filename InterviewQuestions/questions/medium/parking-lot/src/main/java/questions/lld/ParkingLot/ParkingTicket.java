package questions.lld.ParkingLot;

import questions.lld.ParkingLot.Vehicle.Vehicle;

import java.time.LocalDateTime;

/**
 * A ticket issued when a vehicle enters the parking lot.
 */
public class ParkingTicket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final int spotId;
    private final int floorNumber;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public ParkingTicket(String ticketId, Vehicle vehicle, int spotId, int floorNumber) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.spotId = spotId;
        this.floorNumber = floorNumber;
        this.entryTime = LocalDateTime.now();
    }

    public String getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public int getSpotId() { return spotId; }
    public int getFloorNumber() { return floorNumber; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }

    @Override
    public String toString() {
        return "Ticket[" + ticketId + "] " + vehicle + " Floor:" + floorNumber + " Spot:" + spotId;
    }
}
