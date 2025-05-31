package pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pattern.ingredients.PizzaIngredientFactory;
import pattern.ingredients.cheese.Cheese;
import pattern.ingredients.dough.Dough;
import pattern.ingredients.sauce.Sauce;
import pattern.pizza.impl.CheesePizza;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheesePizzaTest {

    @Mock
    private PizzaIngredientFactory ingredientFactory;
    @Mock
    private Dough dough;
    @Mock
    private Sauce sauce;
    @Mock
    private Cheese cheese;

    private CheesePizza pizza;    
    
    @BeforeEach
    void setUp() {
        pizza = new CheesePizza(ingredientFactory);
        pizza.setName("Test Cheese Pizza");
    }

    @Test
    void prepare_ShouldUsePizzaIngredientFactory() {
        pizza.prepare();

        verify(ingredientFactory).createDough();
        verify(ingredientFactory).createSauce();
        verify(ingredientFactory).createCheese();
        verifyNoMoreInteractions(ingredientFactory);
    }

    @Test
    void toString_ShouldIncludeAllIngredients() {
        when(ingredientFactory.createDough()).thenReturn(dough);
        when(ingredientFactory.createSauce()).thenReturn(sauce);
        when(ingredientFactory.createCheese()).thenReturn(cheese);
        when(dough.toString()).thenReturn("Mock Dough");
        when(sauce.toString()).thenReturn("Mock Sauce");
        when(cheese.toString()).thenReturn("Mock Cheese");

        pizza.prepare();
        String result = pizza.toString();

        assertTrue(result.contains("Test Cheese Pizza"));
        assertTrue(result.contains("Mock Dough"));
        assertTrue(result.contains("Mock Sauce"));
        assertTrue(result.contains("Mock Cheese"));
    }
}
