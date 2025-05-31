package pattern.ingredients;

import pattern.ingredients.cheese.Cheese;
import pattern.ingredients.clams.Clams;
import pattern.ingredients.dough.Dough;
import pattern.ingredients.pepperoni.Pepperoni;
import pattern.ingredients.sauce.Sauce;
import pattern.ingredients.veggies.Veggies;

public interface PizzaIngredientFactory {
    Dough createDough();
    Sauce createSauce();
    Cheese createCheese();
    Veggies[] createVeggies();
    Pepperoni createPepperoni();
    Clams createClam();
}
