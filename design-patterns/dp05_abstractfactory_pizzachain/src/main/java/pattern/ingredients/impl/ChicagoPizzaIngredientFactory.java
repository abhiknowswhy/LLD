package pattern.ingredients.impl;

import pattern.ingredients.PizzaIngredientFactory;
import pattern.ingredients.cheese.Cheese;
import pattern.ingredients.cheese.impl.MozzarellaCheese;
import pattern.ingredients.clams.Clams;
import pattern.ingredients.clams.impl.FrozenClams;
import pattern.ingredients.dough.Dough;
import pattern.ingredients.dough.impl.ThickCrustDough;
import pattern.ingredients.pepperoni.Pepperoni;
import pattern.ingredients.pepperoni.impl.SlicedPepperoni;
import pattern.ingredients.sauce.Sauce;
import pattern.ingredients.sauce.impl.PlumTomatoSauce;
import pattern.ingredients.veggies.Veggies;
import pattern.ingredients.veggies.impl.*;

public class ChicagoPizzaIngredientFactory implements PizzaIngredientFactory {
    public Dough createDough() {
        return new ThickCrustDough();
    }

    public Sauce createSauce() {
        return new PlumTomatoSauce();
    }

    public Cheese createCheese() {
        return new MozzarellaCheese();
    }

    public Veggies[] createVeggies() {
        Veggies veggies[] = { new BlackOlives(), new Spinach(), new Eggplant() };
        return veggies;
    }

    public Pepperoni createPepperoni() {
        return new SlicedPepperoni();
    }

    public Clams createClam() {
        return new FrozenClams();
    }
}
