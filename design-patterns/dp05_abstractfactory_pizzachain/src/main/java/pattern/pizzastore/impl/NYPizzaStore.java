package pattern.pizzastore.impl;

import pattern.ingredients.PizzaIngredientFactory;
import pattern.ingredients.impl.NYPizzaIngredientFactory;
import pattern.pizza.Pizza;
import pattern.pizza.impl.CheesePizza;
import pattern.pizza.impl.ClamPizza;
import pattern.pizza.impl.PepperoniPizza;
import pattern.pizza.impl.VeggiePizza;
import pattern.pizzastore.PizzaStore;

public class NYPizzaStore extends PizzaStore {
    @Override
    public Pizza createPizza(String item) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();

        switch (item) {
            case "cheese" -> {
                pizza = new CheesePizza(ingredientFactory);
                pizza.setName("New York Style Cheese Pizza");
            }
            case "veggie" -> {
                pizza = new VeggiePizza(ingredientFactory);
                pizza.setName("New York Style Veggie Pizza");
            }
            case "clam" -> {
                pizza = new ClamPizza(ingredientFactory);
                pizza.setName("New York Style Clam Pizza");
            }
            case "pepperoni" -> {
                pizza = new PepperoniPizza(ingredientFactory);
                pizza.setName("New York Style Pepperoni Pizza");
            }
            default -> {
            }
        }
        return pizza;
    }
}
