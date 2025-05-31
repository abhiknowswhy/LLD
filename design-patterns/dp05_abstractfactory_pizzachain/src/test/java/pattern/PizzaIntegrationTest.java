package pattern;

import org.junit.jupiter.api.Test;
import pattern.pizza.Pizza;
import pattern.pizzastore.PizzaStore;
import pattern.pizzastore.impl.ChicagoPizzaStore;
import pattern.pizzastore.impl.NYPizzaStore;

import static org.junit.jupiter.api.Assertions.*;

class PizzaIntegrationTest {

    @Test
    void testNYAndChicagoPizzaStores() {
        PizzaStore nyStore = new NYPizzaStore();
        PizzaStore chicagoStore = new ChicagoPizzaStore();

        // Test NY Style Pizza
        Pizza nyPizza = nyStore.orderPizza("cheese");
        assertNotNull(nyPizza);
        assertEquals("New York Style Cheese Pizza", nyPizza.getName());
        String nyPizzaDescription = nyPizza.toString();
        assertTrue(nyPizzaDescription.contains("Thin Crust Dough"));
        assertTrue(nyPizzaDescription.contains("Marinara Sauce"));
        assertTrue(nyPizzaDescription.contains("Reggiano Cheese"));

        // Test Chicago Style Pizza
        Pizza chicagoPizza = chicagoStore.orderPizza("cheese");
        assertNotNull(chicagoPizza);
        assertEquals("Chicago Style Cheese Pizza", chicagoPizza.getName());
        String chicagoPizzaDescription = chicagoPizza.toString();
        assertTrue(chicagoPizzaDescription.contains("ThickCrust style extra thick crust dough"));
        assertTrue(chicagoPizzaDescription.contains("Tomato sauce with plum tomatoes"));
        assertTrue(chicagoPizzaDescription.contains("Shredded Mozzarella"));
    }

    @Test
    void testAllPizzaTypes() {
        PizzaStore nyStore = new NYPizzaStore();
        
        // Test all NY Style Pizza types
        String[] pizzaTypes = {"cheese", "clam", "pepperoni", "veggie"};
        
        for (String type : pizzaTypes) {
            Pizza pizza = nyStore.orderPizza(type);
            assertNotNull(pizza, "Pizza should not be null for type: " + type);
            assertTrue(pizza.getName().startsWith("New York Style"), 
                    "Pizza name should start with 'New York Style' for type: " + type);
            
            String description = pizza.toString();
            assertTrue(description.contains("Thin Crust Dough"), 
                    "NY Style pizza should have thin crust for type: " + type);
            assertTrue(description.contains("Marinara Sauce"), 
                    "NY Style pizza should have marinara sauce for type: " + type);
            assertTrue(description.contains("Reggiano Cheese"), 
                    "NY Style pizza should have reggiano cheese for type: " + type);
        }
    }
}
