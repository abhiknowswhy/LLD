package questions.lld;

import questions.lld.ParkingLot.ConcurrentParkingLot;
import questions.lld.ParkingLot.ParkingTicket;
import questions.lld.ParkingLot.Vehicle.Vehicle;
import questions.lld.ParkingLot.Vehicle.VehicleType;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Multithreaded Parking Lot Demo ===\n");

        ConcurrentParkingLot lot = new ConcurrentParkingLot("Concurrent Garage");
        lot.addFloor(5, 10, 3);

        int numThreads = 20;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        AtomicInteger parked = new AtomicInteger(0);
        AtomicInteger rejected = new AtomicInteger(0);

        for (int i = 0; i < numThreads; i++) {
            final int id = i;
            executor.submit(() -> {
                try {
                    Vehicle v = new Vehicle("CAR-" + id, VehicleType.CAR);
                    ParkingTicket ticket = lot.parkVehicle(v);
                    if (ticket != null) {
                        parked.incrementAndGet();
                        System.out.println(Thread.currentThread().getName() + " parked " + v);
                    } else {
                        rejected.incrementAndGet();
                        System.out.println(Thread.currentThread().getName() + " REJECTED " + v);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        System.out.println("\nParked: " + parked.get() + ", Rejected: " + rejected.get());
        System.out.println("Active tickets: " + lot.getActiveTicketCount());
        System.out.println("Expected parked: 10 (only 10 car spots on 1 floor)");
    }
}