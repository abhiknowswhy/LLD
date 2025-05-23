package pattern;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CaffeineBeverageTest {
    @Test
    void testPrepareRecipe_CallsMethodsInOrder() {
        // Arrange
        CaffeineBeverage beverage = mock(CaffeineBeverage.class, CALLS_REAL_METHODS);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        beverage.prepareRecipe();
        String output = outContent.toString();

        // Assert
        assertTrue(output.contains("Boiling water"));
        assertTrue(output.contains("Pouring into cup"));
        InOrder inOrder = inOrder(beverage);
        inOrder.verify(beverage).brew();
        inOrder.verify(beverage).addCondiments();
    }
}
