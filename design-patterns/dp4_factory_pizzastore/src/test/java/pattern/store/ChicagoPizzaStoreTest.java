package pattern.store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pattern.pizza.Pizza;
import pattern.store.impl.chicago.ChicagoPizzaStore;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChicagoPizzaStoreTest {

    @Spy
    private ChicagoPizzaStore chicagoPizzaStore;

    @Test
    void orderPizza_shouldPrepareAndCookPizzaInCorrectOrder() {
        // Given
        Pizza spyPizza = spy(chicagoPizzaStore.createPizza("cheese"));
        doReturn(spyPizza).when(chicagoPizzaStore).createPizza("cheese");

        // When
        chicagoPizzaStore.orderPizza("cheese");

        // Then
        verify(spyPizza, times(1)).prepare();
        verify(spyPizza, times(1)).bake();
        verify(spyPizza, times(1)).cut();
        verify(spyPizza, times(1)).box();

        // Verify order of operations
        var inOrder = inOrder(spyPizza);
        inOrder.verify(spyPizza).prepare();
        inOrder.verify(spyPizza).bake();
        inOrder.verify(spyPizza).cut();
        inOrder.verify(spyPizza).box();
    }

    @Test
    void createPizza_shouldReturnCorrectPizzaType() {
        // When & Then
        assertNotNull(chicagoPizzaStore.createPizza("cheese"), "Cheese pizza should be created");
        assertNotNull(chicagoPizzaStore.createPizza("veggie"), "Veggie pizza should be created");
        assertNotNull(chicagoPizzaStore.createPizza("clam"), "Clam pizza should be created");
        assertNotNull(chicagoPizzaStore.createPizza("pepperoni"), "Pepperoni pizza should be created");
    }

    @Test
    void createPizza_shouldThrowException_whenTypeIsInvalid() {
        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            chicagoPizzaStore.createPizza("invalid"));
        assertEquals("Unknown Chicago pizza type: invalid", exception.getMessage());
    }

    @Test
    void chicagoStylePizza_shouldCutIntoSquares() {
        // Given
        Pizza pizza = chicagoPizzaStore.createPizza("cheese");
        
        // When & Then
        // Redirect System.out to capture output
        var outContent = new java.io.ByteArrayOutputStream();
        var originalOut = System.out;
        System.setOut(new java.io.PrintStream(outContent));

        pizza.cut();

        System.setOut(originalOut);
        assertTrue(outContent.toString().contains("square slices"), 
            "Chicago style pizza should be cut into square slices");
    }
}
