package pattern;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CaffeineBeverageWithHookTest {
    @Test
    void testPrepareRecipeWithHook_CallsMethodsInOrder_CondimentsTrue() {
        // Arrange
        CaffeineBeverageWithHook beverage = mock(CaffeineBeverageWithHook.class, CALLS_REAL_METHODS);
        when(beverage.customerWantsCondiments()).thenReturn(true);
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

    @Test
    void testPrepareRecipeWithHook_CallsMethodsInOrder_CondimentsFalse() {
        // Arrange
        CaffeineBeverageWithHook beverage = mock(CaffeineBeverageWithHook.class, CALLS_REAL_METHODS);
        when(beverage.customerWantsCondiments()).thenReturn(false);
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
        inOrder.verify(beverage, never()).addCondiments();
    }
}
