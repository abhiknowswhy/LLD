package pattern.ingredients.impl;

import pattern.ingredients.PizzaIngredientFactory;
import pattern.ingredients.cheese.Cheese;
import pattern.ingredients.cheese.impl.ReggianoCheese;
import pattern.ingredients.clams.Clams;
import pattern.ingredients.clams.impl.FreshClams;
import pattern.ingredients.dough.Dough;
import pattern.ingredients.dough.impl.ThinCrustDough;
import pattern.ingredients.pepperoni.Pepperoni;
import pattern.ingredients.pepperoni.impl.SlicedPepperoni;
import pattern.ingredients.sauce.Sauce;
import pattern.ingredients.sauce.impl.MarinaraSauce;
import pattern.ingredients.veggies.Veggies;
import pattern.ingredients.veggies.impl.*;

public class NYPizzaIngredientFactory implements PizzaIngredientFactory {
    public Dough createDough() {
        return new ThinCrustDough();
    }

    public Sauce createSauce() {
        return new MarinaraSauce();
    }

    public Cheese createCheese() {
        return new ReggianoCheese();
    }

    public Veggies[] createVeggies() {
        Veggies veggies[] = { new Garlic(), new Onion(), new Mushroom(), new RedPepper() };
        return veggies;
    }

    public Pepperoni createPepperoni() {
        return new SlicedPepperoni();
    }

    public Clams createClam() {
        return new FreshClams();
    }
}
