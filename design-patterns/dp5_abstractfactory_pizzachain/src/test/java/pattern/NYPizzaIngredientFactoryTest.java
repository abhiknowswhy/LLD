package pattern;

import org.junit.jupiter.api.Test;
import pattern.ingredients.PizzaIngredientFactory;
import pattern.ingredients.cheese.Cheese;
import pattern.ingredients.clams.Clams;
import pattern.ingredients.dough.Dough;
import pattern.ingredients.impl.NYPizzaIngredientFactory;
import pattern.ingredients.pepperoni.Pepperoni;
import pattern.ingredients.sauce.Sauce;
import pattern.ingredients.veggies.Veggies;
import static org.junit.jupiter.api.Assertions.*;

class NYPizzaIngredientFactoryTest {
    
    private final PizzaIngredientFactory factory = new NYPizzaIngredientFactory();

    @Test
    void createDough_ShouldReturnThinCrustDough() {
        Dough dough = factory.createDough();
        assertNotNull(dough);
        assertEquals("Thin Crust Dough", dough.toString());
    }

    @Test
    void createSauce_ShouldReturnMarinaraSauce() {
        Sauce sauce = factory.createSauce();
        assertNotNull(sauce);
        assertEquals("Marinara Sauce", sauce.toString());
    }

    @Test
    void createCheese_ShouldReturnReggianoCheese() {
        Cheese cheese = factory.createCheese();
        assertNotNull(cheese);
        assertEquals("Reggiano Cheese", cheese.toString());
    }

    @Test
    void createVeggies_ShouldReturnNYStyleVeggies() {
        Veggies[] veggies = factory.createVeggies();
        assertNotNull(veggies);
        assertEquals(4, veggies.length);
        assertEquals("Garlic", veggies[0].toString());
        assertEquals("Onion", veggies[1].toString());
        assertEquals("Mushroom", veggies[2].toString());
        assertEquals("Red Pepper", veggies[3].toString());
    }

    @Test
    void createPepperoni_ShouldReturnSlicedPepperoni() {
        Pepperoni pepperoni = factory.createPepperoni();
        assertNotNull(pepperoni);
        assertEquals("Sliced Pepperoni", pepperoni.toString());
    }

    @Test
    void createClam_ShouldReturnFreshClams() {
        Clams clams = factory.createClam();
        assertNotNull(clams);
        assertEquals("Fresh Clams from Long Island Sound", clams.toString());
    }
}
