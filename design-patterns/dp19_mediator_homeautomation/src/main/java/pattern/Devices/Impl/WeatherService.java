package pattern.Devices.Impl;

import pattern.Devices.Device;
import pattern.Mediator.Mediator;

public class WeatherService extends Device {
    public WeatherService(Mediator mediator) {
        super(mediator);
    }

    public void checkWeather() {
        System.out.println("WeatherService: It's sunny and 25°C");
    }
}
