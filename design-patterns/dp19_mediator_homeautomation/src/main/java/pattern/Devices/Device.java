package pattern.Devices;

import pattern.Mediator.Mediator;

public abstract class Device {
    protected Mediator mediator;

    public Device(Mediator mediator) {
        this.mediator = mediator;
    }
}
