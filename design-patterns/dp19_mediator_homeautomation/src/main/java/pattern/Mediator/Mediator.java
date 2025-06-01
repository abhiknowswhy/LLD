package pattern.Mediator;

import pattern.Devices.Device;

public interface Mediator {
    void notify(Device sender, String event);
}
