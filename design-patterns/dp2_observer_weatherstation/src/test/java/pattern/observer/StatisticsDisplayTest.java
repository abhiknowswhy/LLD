package pattern.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pattern.observable.WeatherData;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(MockitoExtension.class)
class StatisticsDisplayTest {
    
    private StatisticsDisplay display;
    private ByteArrayOutputStream outputStream;
    
    @Mock
    private WeatherData weatherData;
    
    @BeforeEach
    void setUp() {
        display = new StatisticsDisplay(weatherData);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }
    
    @Test
    void testUpdateStatistics() {
        // When
        display.update(80.0f, 65.0f, 30.4f);
        display.update(82.0f, 70.0f, 29.2f);
        display.update(78.0f, 90.0f, 29.2f);
        
        // Then
        String output = outputStream.toString();
        String lastLine = output.lines().reduce((first, second) -> second).orElse("");
        
        assertTrue(lastLine.contains("Avg/Max/Min temperature = 80.0/82.0/78.0"));
    }
    
    @Test
    void testInitialUpdate() {
        // When
        display.update(75.0f, 60.0f, 30.0f);
        
        // Then
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Avg/Max/Min temperature = 75.0/75.0/75.0"));
    }
}
