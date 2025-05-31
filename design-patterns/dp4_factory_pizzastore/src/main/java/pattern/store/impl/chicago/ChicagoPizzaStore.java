package pattern.store.impl.chicago;

import pattern.pizza.Pizza;
import pattern.store.PizzaStore;
import pattern.pizza.impl.chicago.*;

public class ChicagoPizzaStore extends PizzaStore {    @Override
    public Pizza createPizza(String item) {
        return switch (item) {
            case "cheese" -> new ChicagoStyleCheesePizza();
            case "veggie" -> new ChicagoStyleVeggiePizza();
            case "clam" -> new ChicagoStyleClamPizza();
            case "pepperoni" -> new ChicagoStylePepperoniPizza();
            default -> throw new IllegalArgumentException("Unknown Chicago pizza type: " + item);
        };
    }
}
