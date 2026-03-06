package questions.lld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.lld.ParkingLot.ConcurrentParkingLot;
import questions.lld.ParkingLot.ParkingTicket;
import questions.lld.ParkingLot.Vehicle.Vehicle;
import questions.lld.ParkingLot.Vehicle.VehicleType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrentParkingLotTest {

    private ConcurrentParkingLot lot;

    @BeforeEach
    void setUp() {
        lot = new ConcurrentParkingLot("Test Garage");
        lot.addFloor(2, 3, 1); // 2 motorcycle, 3 car, 1 truck spots
    }

    @Test
    void testSingleParkAndUnpark() {
        Vehicle car = new Vehicle("CAR-1", VehicleType.CAR);
        ParkingTicket ticket = lot.parkVehicle(car);
        assertNotNull(ticket);
        assertEquals(1, lot.getActiveTicketCount());

        double fee = lot.unparkVehicle(ticket.getTicketId());
        assertTrue(fee > 0);
        assertEquals(0, lot.getActiveTicketCount());
    }

    @Test
    void testParkBeyondCapacity() {
        for (int i = 0; i < 3; i++) {
            assertNotNull(lot.parkVehicle(new Vehicle("CAR-" + i, VehicleType.CAR)));
        }
        // 4th car should be rejected
        assertNull(lot.parkVehicle(new Vehicle("CAR-3", VehicleType.CAR)));
    }

    @Test
    void testConcurrentParkingNoDoubleBooking() throws InterruptedException {
        int numThreads = 20;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(numThreads);
        AtomicInteger parked = new AtomicInteger(0);

        for (int i = 0; i < numThreads; i++) {
            final int id = i;
            executor.submit(() -> {
                try {
                    start.await(); // all threads start at once
                    Vehicle v = new Vehicle("CAR-" + id, VehicleType.CAR);
                    ParkingTicket ticket = lot.parkVehicle(v);
                    if (ticket != null) {
                        parked.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }

        start.countDown(); // release all threads
        done.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        // Only 3 car spots exist — exactly 3 should park
        assertEquals(3, parked.get());
        assertEquals(3, lot.getActiveTicketCount());
    }

    @Test
    void testConcurrentParkAndUnpark() throws InterruptedException {
        int numCars = 3;
        List<ParkingTicket> tickets = Collections.synchronizedList(new ArrayList<>());

        // Park 3 cars
        for (int i = 0; i < numCars; i++) {
            ParkingTicket ticket = lot.parkVehicle(new Vehicle("CAR-" + i, VehicleType.CAR));
            assertNotNull(ticket);
            tickets.add(ticket);
        }
        assertEquals(3, lot.getActiveTicketCount());

        // Unpark all 3 concurrently
        ExecutorService executor = Executors.newFixedThreadPool(numCars);
        CountDownLatch done = new CountDownLatch(numCars);
        AtomicInteger unparked = new AtomicInteger(0);

        for (ParkingTicket ticket : tickets) {
            executor.submit(() -> {
                try {
                    double fee = lot.unparkVehicle(ticket.getTicketId());
                    if (fee >= 0) unparked.incrementAndGet();
                } finally {
                    done.countDown();
                }
            });
        }

        done.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        assertEquals(3, unparked.get());
        assertEquals(0, lot.getActiveTicketCount());
    }

    @Test
    void testDifferentVehicleTypes() {
        assertNotNull(lot.parkVehicle(new Vehicle("M-1", VehicleType.MOTORCYCLE)));
        assertNotNull(lot.parkVehicle(new Vehicle("M-2", VehicleType.MOTORCYCLE)));
        assertNull(lot.parkVehicle(new Vehicle("M-3", VehicleType.MOTORCYCLE))); // only 2 spots

        assertNotNull(lot.parkVehicle(new Vehicle("T-1", VehicleType.TRUCK)));
        assertNull(lot.parkVehicle(new Vehicle("T-2", VehicleType.TRUCK))); // only 1 spot
    }

    @Test
    void testUnparkInvalidTicket() {
        assertThrows(IllegalArgumentException.class, () -> lot.unparkVehicle("INVALID-TICKET"));
    }

    @Test
    void testMultipleFloors() {
        lot.addFloor(0, 2, 0); // floor 2 with 2 more car spots
        for (int i = 0; i < 5; i++) {
            assertNotNull(lot.parkVehicle(new Vehicle("CAR-" + i, VehicleType.CAR)));
        }
        // 6th car should be rejected (3 + 2 = 5 total car spots)
        assertNull(lot.parkVehicle(new Vehicle("CAR-5", VehicleType.CAR)));
    }
}
