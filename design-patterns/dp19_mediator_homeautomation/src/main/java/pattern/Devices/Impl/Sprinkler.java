package pattern.Devices.Impl;

import pattern.Devices.Device;
import pattern.Mediator.Mediator;

public class Sprinkler extends Device {
    public Sprinkler(Mediator mediator) {
        super(mediator);
    }

    public void sprinklerOn() {
        System.out.println("Sprinkler: Sprinkling water...");
    }
}
