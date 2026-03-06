package questions.lld.ParkingLot;

import questions.lld.ParkingLot.Pricing.HourlyPricingStrategy;
import questions.lld.ParkingLot.Pricing.PricingStrategy;
import questions.lld.ParkingLot.Spot.ParkingSpot;
import questions.lld.ParkingLot.Vehicle.Vehicle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Main Parking Lot class — Singleton.
 *
 * Features:
 * - Multi-floor parking with different spot types
 * - Ticket-based entry/exit
 * - Strategy-based pricing
 * - Track available spots per type per floor
 */
public class ParkingLot {
    private static ParkingLot instance;

    private final String name;
    private final List<ParkingFloor> floors;
    private final Map<String, ParkingTicket> activeTickets; // ticketId -> ticket
    private PricingStrategy pricingStrategy;
    private int ticketCounter;

    private ParkingLot(String name) {
        this.name = name;
        this.floors = new ArrayList<>();
        this.activeTickets = new HashMap<>();
        this.pricingStrategy = new HourlyPricingStrategy();
        this.ticketCounter = 0;
    }

    public static ParkingLot getInstance(String name) {
        if (instance == null) {
            instance = new ParkingLot(name);
        }
        return instance;
    }

    // For testing — allow reset
    public static void resetInstance() {
        instance = null;
    }

    public void addFloor(int motorcycleSpots, int carSpots, int truckSpots) {
        int floorNumber = floors.size() + 1;
        floors.add(new ParkingFloor(floorNumber, motorcycleSpots, carSpots, truckSpots));
    }

    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;
    }

    /**
     * Park a vehicle. Returns a ticket, or null if no spot available.
     */
    public ParkingTicket parkVehicle(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.findAvailableSpot(vehicle.getType());
            if (spot != null) {
                spot.park(vehicle);
                String ticketId = generateTicketId();
                ParkingTicket ticket = new ParkingTicket(ticketId, vehicle, spot.getId(), floor.getFloorNumber());
                activeTickets.put(ticketId, ticket);
                return ticket;
            }
        }
        return null; // lot full for this type
    }

    /**
     * Unpark a vehicle by ticket. Returns the fee charged.
     */
    public double unparkVehicle(String ticketId) {
        ParkingTicket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Invalid ticket: " + ticketId);
        }

        // Find and free the spot
        ParkingFloor floor = floors.get(ticket.getFloorNumber() - 1);
        ParkingSpot spot = floor.getSpotById(ticket.getSpotId());
        spot.unpark();

        ticket.setExitTime(LocalDateTime.now());
        long hours = ChronoUnit.HOURS.between(ticket.getEntryTime(), ticket.getExitTime());
        return pricingStrategy.calculateFee(ticket.getVehicle().getType(), hours);
    }

    public boolean isFull() {
        for (ParkingFloor floor : floors) {
            for (var type : questions.lld.ParkingLot.Vehicle.VehicleType.values()) {
                if (floor.getAvailableCount(type) > 0) return false;
            }
        }
        return true;
    }

    public int getActiveTicketCount() { return activeTickets.size(); }
    public String getName() { return name; }
    public int getFloorCount() { return floors.size(); }

    public void displayStatus() {
        System.out.println("=== " + name + " ===");
        for (ParkingFloor floor : floors) {
            floor.display();
        }
        System.out.println("Active tickets: " + activeTickets.size());
    }

    private String generateTicketId() {
        return "T-" + (++ticketCounter);
    }
}
