package pattern;

import pattern.observable.WeatherData;
import pattern.observer.*;

public class Main {
    @SuppressWarnings("unused") // These variables are needed to keep references to displays
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
    
        // We need to keep references to displays to prevent garbage collection
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);
        HeatIndexDisplay heatIndexDisplay = new HeatIndexDisplay(weatherData);

        // Simulate new weather measurements
        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
        
        // Test removing an observer
        weatherData.removeObserver(forecastDisplay);
        weatherData.setMeasurements(62, 90, 28.1f);
    }
}