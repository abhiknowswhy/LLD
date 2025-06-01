package pattern;

import pattern.Devices.Impl.Alarm;
import pattern.Devices.Impl.Calendar;
import pattern.Mediator.Impl.SmartHomeMediator;

public class Main {
    public static void main(String[] args) {
        SmartHomeMediator mediator = new SmartHomeMediator();
        Alarm alarm = mediator.getAlarm();
        Calendar calendar = mediator.getCalendar();

        System.out.println("=== Triggering Alarm Event ===");
        alarm.triggerAlarm();

        System.out.println();
        System.out.println("=== Checking Calendar (Weekend Event) ===");
        calendar.checkDate();

        System.out.println();
        System.out.println("=== Simulating Trash Day Event ===");
        mediator.notify(calendar, "trashDay");
    }
}