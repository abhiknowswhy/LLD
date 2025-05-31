package pattern.store.impl.ny;

import pattern.pizza.Pizza;
import pattern.store.PizzaStore;
import pattern.pizza.impl.ny.*;

public class NYPizzaStore extends PizzaStore {    @Override
    public Pizza createPizza(String item) {
        return switch (item) {
            case "cheese" -> new NYStyleCheesePizza();
            case "veggie" -> new NYStyleVeggiePizza();
            case "clam" -> new NYStyleClamPizza();
            case "pepperoni" -> new NYStylePepperoniPizza();
            default -> throw new IllegalArgumentException("Unknown NY pizza type: " + item);
        };
    }
}
