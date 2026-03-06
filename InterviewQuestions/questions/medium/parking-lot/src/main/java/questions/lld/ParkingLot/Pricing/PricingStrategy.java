package questions.lld.ParkingLot.Pricing;

import questions.lld.ParkingLot.Vehicle.VehicleType;

/**
 * Strategy pattern for parking fee calculation.
 */
public interface PricingStrategy {
    double calculateFee(VehicleType vehicleType, long hoursParked);
}
