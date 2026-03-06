package questions.lld.Airline;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Flight {
    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;
    private final Map<String, Seat> seats; // seatNumber -> Seat

    public Flight(String flightNumber, String origin, String destination,
                  LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seats = new LinkedHashMap<>();
    }

    public void addSeats(int count, SeatClass seatClass, String prefix) {
        for (int i = 1; i <= count; i++) {
            String seatNum = prefix + i;
            seats.put(seatNum, new Seat(seatNum, seatClass));
        }
    }

    public List<Seat> getAvailableSeats() {
        return seats.values().stream()
                .filter(s -> !s.isBooked())
                .collect(Collectors.toList());
    }

    public List<Seat> getAvailableSeats(SeatClass seatClass) {
        return seats.values().stream()
                .filter(s -> !s.isBooked() && s.getSeatClass() == seatClass)
                .collect(Collectors.toList());
    }

    public Seat getSeat(String seatNumber) {
        return seats.get(seatNumber);
    }

    public int getTotalSeats() { return seats.size(); }
    public int getBookedCount() { return (int) seats.values().stream().filter(Seat::isBooked).count(); }
    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }

    @Override
    public String toString() {
        return String.format("Flight %s: %s -> %s (%s)", flightNumber, origin, destination, departureTime);
    }
}
