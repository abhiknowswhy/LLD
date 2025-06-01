package pattern.Devices.Impl;

import pattern.Devices.Device;
import pattern.Mediator.Mediator;

public class TemperatureSensor extends Device {
    public TemperatureSensor(Mediator mediator) {
        super(mediator);
    }

    public void checkTemperature() {
        System.out.println("TemperatureSensor: Checking temperature...");
    }
}
