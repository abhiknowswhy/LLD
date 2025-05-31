package pattern.pizza;

import org.junit.jupiter.api.Test;
import pattern.pizza.impl.ny.NYStyleCheesePizza;
import pattern.pizza.impl.chicago.ChicagoStyleCheesePizza;

import static org.junit.jupiter.api.Assertions.*;

public class PizzaTest {

    @Test
    void nyStylePizza_shouldHaveCorrectIngredients() {
        // Given
        Pizza pizza = new NYStyleCheesePizza();

        // When
        String pizzaString = pizza.toString();

        // Then
        assertTrue(pizzaString.contains("Thin Crust Dough"), "NY Pizza should have thin crust");
        assertTrue(pizzaString.contains("Marinara Sauce"), "NY Pizza should have marinara sauce");
        assertTrue(pizzaString.contains("Grated Reggiano Cheese"), "NY Pizza should have reggiano cheese");
    }

    @Test
    void chicagoStylePizza_shouldHaveCorrectIngredients() {
        // Given
        Pizza pizza = new ChicagoStyleCheesePizza();

        // When
        String pizzaString = pizza.toString();

        // Then
        assertTrue(pizzaString.contains("Extra Thick Crust Dough"), "Chicago Pizza should have thick crust");
        assertTrue(pizzaString.contains("Plum Tomato Sauce"), "Chicago Pizza should have plum tomato sauce");
        assertTrue(pizzaString.contains("Shredded Mozzarella Cheese"), "Chicago Pizza should have mozzarella cheese");
    }

    @Test
    void prepare_shouldDisplayCorrectSteps() {
        // Given
        Pizza pizza = new NYStyleCheesePizza();
        var outContent = new java.io.ByteArrayOutputStream();
        var originalOut = System.out;
        System.setOut(new java.io.PrintStream(outContent));

        // When
        pizza.prepare();

        // Then
        System.setOut(originalOut);
        String output = outContent.toString();
        assertTrue(output.contains("Tossing dough..."), "Should show dough tossing step");
        assertTrue(output.contains("Adding sauce..."), "Should show sauce adding step");
        assertTrue(output.contains("Adding toppings:"), "Should show toppings step");
    }

    @Test
    void getName_shouldReturnCorrectName() {
        // Given
        Pizza nyPizza = new NYStyleCheesePizza();
        Pizza chicagoPizza = new ChicagoStyleCheesePizza();

        // When & Then
        assertEquals("NY Style Sauce and Cheese Pizza", nyPizza.getName(), 
            "NY Pizza should have correct name");
        assertEquals("Chicago Style Deep Dish Cheese Pizza", chicagoPizza.getName(), 
            "Chicago Pizza should have correct name");
    }

    @Test
    void defaultCutting_shouldBeDiagonal() {
        // Given
        Pizza pizza = new NYStyleCheesePizza();
        var outContent = new java.io.ByteArrayOutputStream();
        var originalOut = System.out;
        System.setOut(new java.io.PrintStream(outContent));

        // When
        pizza.cut();

        // Then
        System.setOut(originalOut);
        assertTrue(outContent.toString().contains("diagonal slices"), 
            "Default cutting should be diagonal slices");
    }
}
