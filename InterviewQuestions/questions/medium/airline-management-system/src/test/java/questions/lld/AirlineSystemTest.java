package questions.lld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.lld.Airline.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AirlineSystemTest {

    private AirlineSystem airline;
    private Flight flight;

    @BeforeEach
    void setUp() {
        airline = new AirlineSystem("TestAir");

        flight = new Flight("TA100", "NYC", "LAX",
                LocalDateTime.of(2025, 7, 1, 10, 0),
                LocalDateTime.of(2025, 7, 1, 13, 30));
        flight.addSeats(2, SeatClass.FIRST, "F");
        flight.addSeats(3, SeatClass.BUSINESS, "B");
        flight.addSeats(5, SeatClass.ECONOMY, "E");
        airline.addFlight(flight);

        airline.registerPassenger(new Passenger("P1", "Alice", "alice@test.com"));
        airline.registerPassenger(new Passenger("P2", "Bob", "bob@test.com"));
    }

    @Test
    void testFlightSetup() {
        assertEquals(10, flight.getTotalSeats());
        assertEquals(0, flight.getBookedCount());
    }

    @Test
    void testSearchFlights() {
        List<Flight> results = airline.searchFlights("NYC", "LAX");
        assertEquals(1, results.size());
        assertEquals("TA100", results.get(0).getFlightNumber());
    }

    @Test
    void testSearchFlightsNoResult() {
        assertTrue(airline.searchFlights("LAX", "NYC").isEmpty());
    }

    @Test
    void testBookSpecificSeat() {
        Booking booking = airline.bookFlight("P1", "TA100", "E1");
        assertNotNull(booking);
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals("E1", booking.getSeat().getSeatNumber());
        assertTrue(flight.getSeat("E1").isBooked());
    }

    @Test
    void testBookPreferredClass() {
        Booking booking = airline.bookFlight("P1", "TA100", SeatClass.FIRST);
        assertNotNull(booking);
        assertEquals(SeatClass.FIRST, booking.getSeat().getSeatClass());
    }

    @Test
    void testBookAlreadyBookedSeat() {
        airline.bookFlight("P1", "TA100", "E1");
        assertThrows(IllegalStateException.class, () -> airline.bookFlight("P2", "TA100", "E1"));
    }

    @Test
    void testCancelBooking() {
        Booking booking = airline.bookFlight("P1", "TA100", "B1");
        assertTrue(flight.getSeat("B1").isBooked());

        airline.cancelBooking(booking.getBookingId());
        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
        assertFalse(flight.getSeat("B1").isBooked());
    }

    @Test
    void testAvailableSeats() {
        airline.bookFlight("P1", "TA100", "E1");
        airline.bookFlight("P2", "TA100", "E2");
        assertEquals(3, flight.getAvailableSeats(SeatClass.ECONOMY).size());
    }

    @Test
    void testPassengerBookings() {
        airline.bookFlight("P1", "TA100", "E1");
        airline.bookFlight("P1", "TA100", "B1");
        List<Booking> bookings = airline.getPassengerBookings("P1");
        assertEquals(2, bookings.size());
    }

    @Test
    void testInvalidPassenger() {
        assertThrows(IllegalArgumentException.class,
                () -> airline.bookFlight("INVALID", "TA100", "E1"));
    }

    @Test
    void testInvalidFlight() {
        assertThrows(IllegalArgumentException.class,
                () -> airline.bookFlight("P1", "INVALID", "E1"));
    }

    @Test
    void testInvalidSeat() {
        assertThrows(IllegalArgumentException.class,
                () -> airline.bookFlight("P1", "TA100", "Z99"));
    }

    @Test
    void testNoSeatsAvailable() {
        // Book all 2 first class seats
        airline.bookFlight("P1", "TA100", "F1");
        airline.bookFlight("P2", "TA100", "F2");
        assertThrows(IllegalStateException.class,
                () -> airline.bookFlight("P1", "TA100", SeatClass.FIRST));
    }

    @Test
    void testRebookAfterCancellation() {
        Booking b = airline.bookFlight("P1", "TA100", "F1");
        airline.cancelBooking(b.getBookingId());

        // Should be able to book F1 again
        Booking b2 = airline.bookFlight("P2", "TA100", "F1");
        assertNotNull(b2);
        assertEquals(BookingStatus.CONFIRMED, b2.getStatus());
    }
}
