package pattern.ducks;

import pattern.Duck;
import pattern.behaviors.impl.FlyNoWay;
import pattern.behaviors.impl.Squeak;

public class RubberDuck extends Duck {
    public RubberDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new Squeak();
    }

    public RubberDuck(String name) {
        super(name);
        flyBehavior = new FlyNoWay();
        quackBehavior = new Squeak();
    }

    @Override
    public void display() {
        if (name != null) {
            System.out.println(name + " is a rubber duck");
        } else {
            System.out.println("I'm a Rubber duck");
        }
    }
}
