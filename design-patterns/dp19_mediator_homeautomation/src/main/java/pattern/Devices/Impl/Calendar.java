package pattern.Devices.Impl;

import pattern.Devices.Device;
import pattern.Mediator.Mediator;

public class Calendar extends Device {
    public Calendar(Mediator mediator) {
        super(mediator);
    }

    public void checkDate() {
        System.out.println("Calendar: Checking date...");
        java.time.LocalDate date = java.time.LocalDate.now();
        java.time.DayOfWeek dow = date.getDayOfWeek();
        boolean isWeekend = (dow == java.time.DayOfWeek.SATURDAY || dow == java.time.DayOfWeek.SUNDAY);
        boolean isTrashDay = (dow == java.time.DayOfWeek.WEDNESDAY);
        if (isWeekend) mediator.notify(this, "weekend");
        if (isTrashDay) mediator.notify(this, "trashDay");
    }
}
