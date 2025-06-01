package pattern.Mediator.Impl;

import pattern.Devices.Device;
import pattern.Devices.Impl.Alarm;
import pattern.Devices.Impl.Calendar;
import pattern.Devices.Impl.CoffeePot;
import pattern.Devices.Impl.Shower;
import pattern.Devices.Impl.Sprinkler;
import pattern.Devices.Impl.TemperatureSensor;
import pattern.Devices.Impl.WeatherService;
import pattern.Mediator.Mediator;

public class SmartHomeMediator implements Mediator {
    private Alarm alarm;
    private Calendar calendar;
    private CoffeePot coffeePot;
    private Sprinkler sprinkler;
    private Shower shower;
    private TemperatureSensor temperatureSensor;
    private WeatherService weatherService;

    public SmartHomeMediator() {
        this.alarm = new Alarm(this);
        this.calendar = new Calendar(this);
        this.coffeePot = new CoffeePot(this);
        this.sprinkler = new Sprinkler(this);
        this.shower = new Shower(this);
        this.temperatureSensor = new TemperatureSensor(this);
        this.weatherService = new WeatherService(this);
    }

    public void setAlarm(Alarm alarm) { this.alarm = alarm; }
    public void setCalendar(Calendar calendar) { this.calendar = calendar; }
    public void setCoffeePot(CoffeePot coffeePot) { this.coffeePot = coffeePot; }
    public void setSprinkler(Sprinkler sprinkler) { this.sprinkler = sprinkler; }
    public void setShower(Shower shower) { this.shower = shower; }
    public void setTemperatureSensor(TemperatureSensor temperatureSensor) { this.temperatureSensor = temperatureSensor; }
    public void setWeatherService(WeatherService weatherService) { this.weatherService = weatherService; }

    public Alarm getAlarm() { return alarm; }
    public Calendar getCalendar() { return calendar; }
    public CoffeePot getCoffeePot() { return coffeePot; }
    public Sprinkler getSprinkler() { return sprinkler; }
    public Shower getShower() { return shower; }
    public TemperatureSensor getTemperatureSensor() { return temperatureSensor; }
    public WeatherService getWeatherService() { return weatherService; }

    @Override
    public void notify(Device sender, String event) {
        if (sender == alarm && "alarmOn".equals(event)) {
            System.out.println("Mediator: Alarm went off, starting morning routine...");
            calendar.checkDate();
            shower.startShower();
            temperatureSensor.checkTemperature();
            coffeePot.brewCoffee();
            sprinkler.sprinklerOn();
        } else if (sender == calendar && "weekend".equals(event)) {
            System.out.println("Mediator: Weekend detected, checking weather...");
            weatherService.checkWeather();
        } else if (sender == calendar && "trashDay".equals(event)) {
            System.out.println("Mediator: Trash day detected, resetting alarm...");
            alarm.reset();
        }
    }
}
