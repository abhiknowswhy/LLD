package questions.lld.Airline;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Airline Management System: manages flights, passengers, and bookings.
 */
public class AirlineSystem {
    private final String airlineName;
    private final Map<String, Flight> flights;
    private final Map<String, Passenger> passengers;
    private final Map<String, Booking> bookings;
    private final AtomicInteger bookingCounter;

    public AirlineSystem(String airlineName) {
        this.airlineName = airlineName;
        this.flights = new LinkedHashMap<>();
        this.passengers = new LinkedHashMap<>();
        this.bookings = new LinkedHashMap<>();
        this.bookingCounter = new AtomicInteger(0);
    }

    // --- Flight Management ---
    public void addFlight(Flight flight) {
        flights.put(flight.getFlightNumber(), flight);
    }

    public Flight getFlight(String flightNumber) {
        return flights.get(flightNumber);
    }

    public List<Flight> searchFlights(String origin, String destination) {
        return flights.values().stream()
                .filter(f -> f.getOrigin().equalsIgnoreCase(origin)
                          && f.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }

    // --- Passenger Management ---
    public void registerPassenger(Passenger passenger) {
        passengers.put(passenger.getPassengerId(), passenger);
    }

    public Passenger getPassenger(String passengerId) {
        return passengers.get(passengerId);
    }

    // --- Booking ---
    public Booking bookFlight(String passengerId, String flightNumber, String seatNumber) {
        Passenger passenger = passengers.get(passengerId);
        if (passenger == null) throw new IllegalArgumentException("Passenger not found: " + passengerId);

        Flight flight = flights.get(flightNumber);
        if (flight == null) throw new IllegalArgumentException("Flight not found: " + flightNumber);

        Seat seat = flight.getSeat(seatNumber);
        if (seat == null) throw new IllegalArgumentException("Seat not found: " + seatNumber);
        if (!seat.book()) throw new IllegalStateException("Seat already booked: " + seatNumber);

        String bookingId = "BK-" + bookingCounter.incrementAndGet();
        Booking booking = new Booking(bookingId, flight, passenger, seat);
        bookings.put(bookingId, booking);
        return booking;
    }

    public Booking bookFlight(String passengerId, String flightNumber, SeatClass preferredClass) {
        Flight flight = flights.get(flightNumber);
        if (flight == null) throw new IllegalArgumentException("Flight not found: " + flightNumber);

        List<Seat> available = flight.getAvailableSeats(preferredClass);
        if (available.isEmpty()) throw new IllegalStateException("No seats available in " + preferredClass);

        return bookFlight(passengerId, flightNumber, available.get(0).getSeatNumber());
    }

    public void cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) throw new IllegalArgumentException("Booking not found: " + bookingId);
        booking.cancel();
    }

    public Booking getBooking(String bookingId) {
        return bookings.get(bookingId);
    }

    public List<Booking> getPassengerBookings(String passengerId) {
        return bookings.values().stream()
                .filter(b -> b.getPassenger().getPassengerId().equals(passengerId))
                .collect(Collectors.toList());
    }

    public String getAirlineName() { return airlineName; }
    public int getTotalFlights() { return flights.size(); }
    public int getTotalPassengers() { return passengers.size(); }
    public int getTotalBookings() { return bookings.size(); }
}
