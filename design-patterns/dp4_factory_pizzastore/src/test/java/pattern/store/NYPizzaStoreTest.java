package pattern.store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pattern.pizza.Pizza;
import pattern.store.impl.ny.NYPizzaStore;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NYPizzaStoreTest {

    @Spy
    private NYPizzaStore nyPizzaStore;

    @Test
    void orderPizza_shouldPrepareAndCookPizzaInCorrectOrder() {
        // Given
        Pizza spyPizza = spy(nyPizzaStore.createPizza("cheese"));
        doReturn(spyPizza).when(nyPizzaStore).createPizza("cheese");

        // When
        nyPizzaStore.orderPizza("cheese");

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
        assertNotNull(nyPizzaStore.createPizza("cheese"), "Cheese pizza should be created");
        assertNotNull(nyPizzaStore.createPizza("veggie"), "Veggie pizza should be created");
        assertNotNull(nyPizzaStore.createPizza("clam"), "Clam pizza should be created");
        assertNotNull(nyPizzaStore.createPizza("pepperoni"), "Pepperoni pizza should be created");
    }

    @Test
    void createPizza_shouldThrowException_whenTypeIsInvalid() {
        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            nyPizzaStore.createPizza("invalid"));
        assertEquals("Unknown NY pizza type: invalid", exception.getMessage());
    }
}
