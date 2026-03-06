package questions.lld.Airline;

public class Booking {
    private final String bookingId;
    private final Flight flight;
    private final Passenger passenger;
    private final Seat seat;
    private BookingStatus status;

    public Booking(String bookingId, Flight flight, Passenger passenger, Seat seat) {
        this.bookingId = bookingId;
        this.flight = flight;
        this.passenger = passenger;
        this.seat = seat;
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
        seat.release();
    }

    public String getBookingId() { return bookingId; }
    public Flight getFlight() { return flight; }
    public Passenger getPassenger() { return passenger; }
    public Seat getSeat() { return seat; }
    public BookingStatus getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("Booking{%s, flight=%s, passenger=%s, seat=%s, status=%s}",
                bookingId, flight.getFlightNumber(), passenger.getName(), seat.getSeatNumber(), status);
    }
}
