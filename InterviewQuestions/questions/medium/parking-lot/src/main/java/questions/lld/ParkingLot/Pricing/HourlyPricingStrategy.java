package questions.lld.ParkingLot.Pricing;

import questions.lld.ParkingLot.Vehicle.VehicleType;

/**
 * Hourly rate pricing: different rate per vehicle type, charged per hour.
 */
public class HourlyPricingStrategy implements PricingStrategy {
    private static final double MOTORCYCLE_RATE = 1.0;
    private static final double CAR_RATE = 2.0;
    private static final double TRUCK_RATE = 4.0;

    @Override
    public double calculateFee(VehicleType vehicleType, long hoursParked) {
        long billableHours = Math.max(1, hoursParked); // minimum 1 hour
        double rate = switch (vehicleType) {
            case MOTORCYCLE -> MOTORCYCLE_RATE;
            case CAR -> CAR_RATE;
            case TRUCK -> TRUCK_RATE;
        };
        return rate * billableHours;
    }
}
