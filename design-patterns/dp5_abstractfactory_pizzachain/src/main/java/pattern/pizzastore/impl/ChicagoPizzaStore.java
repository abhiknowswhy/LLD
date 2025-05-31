package pattern.pizzastore.impl;

import pattern.ingredients.PizzaIngredientFactory;
import pattern.ingredients.impl.ChicagoPizzaIngredientFactory;
import pattern.pizza.Pizza;
import pattern.pizza.impl.CheesePizza;
import pattern.pizza.impl.ClamPizza;
import pattern.pizza.impl.PepperoniPizza;
import pattern.pizza.impl.VeggiePizza;
import pattern.pizzastore.PizzaStore;

public class ChicagoPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(String item) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory = new ChicagoPizzaIngredientFactory();

        switch (item) {
            case "cheese" -> {
                pizza = new CheesePizza(ingredientFactory);
                pizza.setName("Chicago Style Cheese Pizza");
            }
            case "veggie" -> {
                pizza = new VeggiePizza(ingredientFactory);
                pizza.setName("Chicago Style Veggie Pizza");
            }
            case "clam" -> {
                pizza = new ClamPizza(ingredientFactory);
                pizza.setName("Chicago Style Clam Pizza");
            }
            case "pepperoni" -> {
                pizza = new PepperoniPizza(ingredientFactory);
                pizza.setName("Chicago Style Pepperoni Pizza");
            }
            default -> {
            }
        }
        return pizza;
    }
}
