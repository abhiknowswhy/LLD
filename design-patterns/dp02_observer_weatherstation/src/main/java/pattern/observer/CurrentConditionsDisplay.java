package pattern.observer;

import pattern.observable.Subject;
import pattern.observable.WeatherData;

public class CurrentConditionsDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private Subject weatherData;
      public CurrentConditionsDisplay(WeatherData weatherData) {
        this.weatherData = (Subject)weatherData;
        this.weatherData.registerObserver(this);
        weatherData.registerObserver(this);
    }
    
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }
    
    public void display() {
        System.out.println("Current conditions: " + temperature 
            + "F degrees and " + humidity + "% humidity");
    }
}
