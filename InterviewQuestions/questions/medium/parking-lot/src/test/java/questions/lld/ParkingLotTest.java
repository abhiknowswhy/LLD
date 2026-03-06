package questions.lld;

import questions.lld.ParkingLot.ParkingLot;
import questions.lld.ParkingLot.ParkingTicket;
import questions.lld.ParkingLot.Vehicle.Vehicle;
import questions.lld.ParkingLot.Vehicle.VehicleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    private ParkingLot lot;

    @BeforeEach
    void setUp() {
        ParkingLot.resetInstance();
        lot = ParkingLot.getInstance("Test Lot");
        lot.addFloor(2, 3, 1); // 2 motorcycle, 3 car, 1 truck
    }

    @Test
    void testParkAndUnpark() {
        Vehicle car = new Vehicle("C-1", VehicleType.CAR);
        ParkingTicket ticket = lot.parkVehicle(car);
        assertNotNull(ticket);
        assertEquals(1, lot.getActiveTicketCount());

        double fee = lot.unparkVehicle(ticket.getTicketId());
        assertTrue(fee >= 0);
        assertEquals(0, lot.getActiveTicketCount());
    }

    @Test
    void testParkMultipleTypes() {
        assertNotNull(lot.parkVehicle(new Vehicle("M-1", VehicleType.MOTORCYCLE)));
        assertNotNull(lot.parkVehicle(new Vehicle("C-1", VehicleType.CAR)));
        assertNotNull(lot.parkVehicle(new Vehicle("T-1", VehicleType.TRUCK)));
        assertEquals(3, lot.getActiveTicketCount());
    }

    @Test
    void testParkWhenFull() {
        lot.parkVehicle(new Vehicle("T-1", VehicleType.TRUCK));
        ParkingTicket t2 = lot.parkVehicle(new Vehicle("T-2", VehicleType.TRUCK));
        assertNull(t2); // only 1 truck spot
    }

    @Test
    void testInvalidTicket() {
        assertThrows(IllegalArgumentException.class, () -> lot.unparkVehicle("INVALID"));
    }

    @Test
    void testMultiFloor() {
        lot.addFloor(1, 2, 1);
        // Floor 1: 1 truck, Floor 2: 1 truck = 2 total
        assertNotNull(lot.parkVehicle(new Vehicle("T-1", VehicleType.TRUCK)));
        assertNotNull(lot.parkVehicle(new Vehicle("T-2", VehicleType.TRUCK)));
        assertNull(lot.parkVehicle(new Vehicle("T-3", VehicleType.TRUCK)));
    }

    @Test
    void testFillAllCarSpots() {
        for (int i = 1; i <= 3; i++) {
            assertNotNull(lot.parkVehicle(new Vehicle("C-" + i, VehicleType.CAR)));
        }
        assertNull(lot.parkVehicle(new Vehicle("C-4", VehicleType.CAR)));
    }

    @Test
    void testParkUnparkRepark() {
        Vehicle car = new Vehicle("C-1", VehicleType.CAR);
        ParkingTicket t = lot.parkVehicle(car);
        lot.unparkVehicle(t.getTicketId());
        ParkingTicket t2 = lot.parkVehicle(car);
        assertNotNull(t2);
    }
}
