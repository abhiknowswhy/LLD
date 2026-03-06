package questions.lld.ParkingLot;

import questions.lld.ParkingLot.Spot.ParkingSpot;
import questions.lld.ParkingLot.Vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread-safe Parking Lot.
 *
 * Concurrency approach:
 * - ParkingSpot uses AtomicReference + CAS for lock-free park/unpark
 * - Active tickets stored in ConcurrentHashMap
 * - Ticket counter is AtomicInteger
 * - No global lock needed — each spot independently handles contention
 */
public class ConcurrentParkingLot {

    private final String name;
    private final List<ParkingFloor> floors;
    private final ConcurrentHashMap<String, ParkingTicket> activeTickets;
    private final AtomicInteger ticketCounter;

    public ConcurrentParkingLot(String name) {
        this.name = name;
        this.floors = Collections.synchronizedList(new ArrayList<>());
        this.activeTickets = new ConcurrentHashMap<>();
        this.ticketCounter = new AtomicInteger(0);
    }

    public void addFloor(int motorcycleSpots, int carSpots, int truckSpots) {
        int floorNum = floors.size() + 1;
        floors.add(new ParkingFloor(floorNum, motorcycleSpots, carSpots, truckSpots));
    }

    /**
     * Thread-safe parking using CAS on individual spots.
     * Multiple threads can concurrently try to park without blocking each other
     * (they'll just CAS-fail and try the next spot).
     */
    public ParkingTicket parkVehicle(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.tryPark(vehicle);
            if (spot != null) {
                String ticketId = "T-" + ticketCounter.incrementAndGet();
                ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, spot.getId(), floor.getFloorNumber());
                activeTickets.put(ticketId, ticket);
                return ticket;
            }
        }
        return null;
    }

    /**
     * Thread-safe unparking.
     */
    public double unparkVehicle(String ticketId) {
        ParkingTicket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid ticket: " + ticketId);
        }

        ParkingFloor floor = floors.get(ticket.getFloorNumber() - 1);
        ParkingSpot spot = floor.getSpotById(ticket.getSpotId());
        spot.unpark();

        ticket.setExitTime(LocalDateTime.now());
        // Simple flat fee for demo
        return switch (ticket.getVehicle().getType()) {
            case MOTORCYCLE -> 1.0;
            case CAR -> 2.0;
            case TRUCK -> 4.0;
        };
    }

    public int getActiveTicketCount() { return activeTickets.size(); }
    public String getName() { return name; }
    public int getFloorCount() { return floors.size(); }
}
