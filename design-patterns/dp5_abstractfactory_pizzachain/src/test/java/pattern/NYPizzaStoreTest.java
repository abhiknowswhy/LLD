package pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pattern.pizza.Pizza;
import pattern.pizzastore.impl.NYPizzaStore;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NYPizzaStoreTest {

    @Spy
    private NYPizzaStore pizzaStore;
    @Mock
    private Pizza mockPizza;    @BeforeEach
    void setUp() {
    }    @Test
    void orderPizza_ShouldPrepareBakeCutAndBoxPizza() {
        // Setup the stubs only in this test where they are used
        when(mockPizza.getName()).thenReturn("Mock Pizza");
        doReturn(mockPizza).when(pizzaStore).createPizza(anyString());

        Pizza pizza = pizzaStore.orderPizza("cheese");

        assertNotNull(pizza);
        assertEquals("Mock Pizza", pizza.getName());

        verify(mockPizza).prepare();
        verify(mockPizza).bake();
        verify(mockPizza).cut();
        verify(mockPizza).box();
    }

    @Test
    void createPizza_WithInvalidType_ShouldReturnNull() {
        // Create a real NYPizzaStore to test the actual createPizza method
        NYPizzaStore realStore = new NYPizzaStore();
        Pizza pizza = realStore.createPizza("invalid");
        assertNull(pizza);
    }

    @Test
    void createPizza_WithValidTypes_ShouldReturnCorrectPizzas() {
        // Create a real NYPizzaStore to test the actual createPizza method
        NYPizzaStore realStore = new NYPizzaStore();
        
        Pizza cheesePizza = realStore.createPizza("cheese");
        assertNotNull(cheesePizza);
        assertEquals("New York Style Cheese Pizza", cheesePizza.getName());

        Pizza clamPizza = realStore.createPizza("clam");
        assertNotNull(clamPizza);
        assertEquals("New York Style Clam Pizza", clamPizza.getName());

        Pizza pepperoniPizza = realStore.createPizza("pepperoni");
        assertNotNull(pepperoniPizza);
        assertEquals("New York Style Pepperoni Pizza", pepperoniPizza.getName());

        Pizza veggiePizza = realStore.createPizza("veggie");
        assertNotNull(veggiePizza);
        assertEquals("New York Style Veggie Pizza", veggiePizza.getName());
    }
}
