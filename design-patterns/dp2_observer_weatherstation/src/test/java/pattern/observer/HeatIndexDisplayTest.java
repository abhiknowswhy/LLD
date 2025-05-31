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
class HeatIndexDisplayTest {
    
    private HeatIndexDisplay display;
    private ByteArrayOutputStream outputStream;
    
    @Mock
    private WeatherData weatherData;
    
    @BeforeEach
    void setUp() {
        display = new HeatIndexDisplay(weatherData);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }
    
    @Test
    void testHeatIndexCalculation() {
        // When
        display.update(80.0f, 65.0f, 30.4f);
        
        // Then
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Heat index is"));
        
        // Reset output stream
        outputStream.reset();
        
        // Test with different values
        display.update(82.0f, 70.0f, 29.2f);
        output = outputStream.toString().trim();
        assertTrue(output.contains("Heat index is"));
    }
    
    @Test
    void testExtremeCaseHeatIndex() {
        // Test with extreme temperature and humidity
        display.update(100.0f, 90.0f, 30.4f);
        
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Heat index is"));
    }
}
