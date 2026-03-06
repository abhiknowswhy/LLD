package questions.lld;

import questions.lld.Airline.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Airline Management System Demo ===\n");

        AirlineSystem airline = new AirlineSystem("SkyWings");

        // Create flights
        Flight f1 = new Flight("SW101", "NYC", "LAX",
                LocalDateTime.of(2025, 6, 15, 8, 0),
                LocalDateTime.of(2025, 6, 15, 11, 30));
        f1.addSeats(3, SeatClass.FIRST, "F");
        f1.addSeats(5, SeatClass.BUSINESS, "B");
        f1.addSeats(10, SeatClass.ECONOMY, "E");
        airline.addFlight(f1);

        // Register passengers
        airline.registerPassenger(new Passenger("P1", "Alice", "alice@email.com"));
        airline.registerPassenger(new Passenger("P2", "Bob", "bob@email.com"));

        // Book flights
        Booking b1 = airline.bookFlight("P1", "SW101", SeatClass.BUSINESS);
        System.out.println("Booking 1: " + b1);

        Booking b2 = airline.bookFlight("P2", "SW101", "E1");
        System.out.println("Booking 2: " + b2);

        // Search
        System.out.println("\nFlights NYC -> LAX: " + airline.searchFlights("NYC", "LAX"));
        System.out.println("Available economy: " + f1.getAvailableSeats(SeatClass.ECONOMY).size());

        // Cancel
        airline.cancelBooking(b1.getBookingId());
        System.out.println("\nCancelled: " + b1);
        System.out.println("Available business: " + f1.getAvailableSeats(SeatClass.BUSINESS).size());
    }
}