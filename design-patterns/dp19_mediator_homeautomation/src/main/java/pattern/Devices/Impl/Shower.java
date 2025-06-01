package pattern.Devices.Impl;

import pattern.Devices.Device;
import pattern.Mediator.Mediator;

public class Shower extends Device {
    public Shower(Mediator mediator) {
        super(mediator);
    }

    public void startShower() {
        System.out.println("Shower: Starting shower...");
    }
}
