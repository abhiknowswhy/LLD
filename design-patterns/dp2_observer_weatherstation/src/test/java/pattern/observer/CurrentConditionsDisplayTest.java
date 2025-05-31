package pattern.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pattern.observable.WeatherData;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(MockitoExtension.class)
class CurrentConditionsDisplayTest {
    
    private CurrentConditionsDisplay display;
    private ByteArrayOutputStream outputStream;
    
    @Mock
    private WeatherData weatherData;
    
    @BeforeEach
    void setUp() {
        display = new CurrentConditionsDisplay(weatherData);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }
    
    @Test
    void testUpdate() {
        // When
        display.update(75.5f, 60.0f, 30.0f);
        
        // Then
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Current conditions: 75.5F degrees and 60.0% humidity"));
    }
    
    @Test
    void testDisplay() {
        // Given
        display.update(80.0f, 65.0f, 30.4f);
        outputStream.reset();
        
        // When
        display.display();
        
        // Then
        String output = outputStream.toString().trim();
        assertEquals("Current conditions: 80.0F degrees and 65.0% humidity", output);
    }
}
