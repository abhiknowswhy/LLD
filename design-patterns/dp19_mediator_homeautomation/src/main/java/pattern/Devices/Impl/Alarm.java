package pattern.Devices.Impl;

import pattern.Devices.Device;
import pattern.Mediator.Mediator;

public class Alarm extends Device {
    public Alarm(Mediator mediator) {
        super(mediator);
    }

    public void triggerAlarm() {
        System.out.println("Alarm: Ring ring ring!");
        mediator.notify(this, "alarmOn");
    }

    public void reset() {
        System.out.println("Alarm: Resetting alarm.");
    }
}
