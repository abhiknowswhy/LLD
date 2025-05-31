package pattern.observable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pattern.observer.Observer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherDataTest {
    
    private WeatherData weatherData;
    
    @Mock
    private Observer observer1;
    
    @Mock
    private Observer observer2;
    @BeforeEach
    void setUp() {
        weatherData = new WeatherData();
    }
    
    @Test
    void testRegisterObserver() {
        weatherData.registerObserver(observer1);
        weatherData.setMeasurements(80f, 65f, 30.4f);
        
        verify(observer1).update(80f, 65f, 30.4f);
    }
    
    @Test
    void testRemoveObserver() {
        weatherData.registerObserver(observer1);
        weatherData.registerObserver(observer2);
        
        weatherData.setMeasurements(80f, 65f, 30.4f);
        verify(observer1).update(80f, 65f, 30.4f);
        verify(observer2).update(80f, 65f, 30.4f);
        
        weatherData.removeObserver(observer1);
        weatherData.setMeasurements(82f, 70f, 29.2f);
        
        verify(observer1, times(1)).update(anyFloat(), anyFloat(), anyFloat()); // Should not receive second update
        verify(observer2, times(2)).update(anyFloat(), anyFloat(), anyFloat()); // Should receive both updates
    }
    
    @Test
    void testMultipleObserversNotified() {
        weatherData.registerObserver(observer1);
        weatherData.registerObserver(observer2);
        
        weatherData.setMeasurements(80f, 65f, 30.4f);
        
        verify(observer1).update(80f, 65f, 30.4f);
        verify(observer2).update(80f, 65f, 30.4f);
    }
    
    @Test
    void testSetMeasurementsUpdatesObservers() {
        weatherData.registerObserver(observer1);
        
        float temp = 80f;
        float humidity = 65f;
        float pressure = 30.4f;
        
        weatherData.setMeasurements(temp, humidity, pressure);
        verify(observer1).update(temp, humidity, pressure);
        assertThat(weatherData.getTemperature()).isCloseTo(temp, within(0.01f));
        assertThat(weatherData.getHumidity()).isCloseTo(humidity, within(0.01f));
        assertThat(weatherData.getPressure()).isCloseTo(pressure, within(0.01f));
    }
}
