package pattern.Devices.Impl;

import pattern.Devices.Device;
import pattern.Mediator.Mediator;

public class CoffeePot extends Device {
    public CoffeePot(Mediator mediator) {
        super(mediator);
    }

    public void brewCoffee() {
        System.out.println("CoffeePot: Brewing coffee...");
    }
}
