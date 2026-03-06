package questions.lld.Airline;

public class Seat {
    private final String seatNumber;
    private final SeatClass seatClass;
    private boolean booked;

    public Seat(String seatNumber, SeatClass seatClass) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.booked = false;
    }

    public String getSeatNumber() { return seatNumber; }
    public SeatClass getSeatClass() { return seatClass; }
    public boolean isBooked() { return booked; }

    public boolean book() {
        if (booked) return false;
        booked = true;
        return true;
    }

    public void release() {
        booked = false;
    }

    @Override
    public String toString() {
        return String.format("Seat %s (%s) %s", seatNumber, seatClass, booked ? "BOOKED" : "AVAILABLE");
    }
}
