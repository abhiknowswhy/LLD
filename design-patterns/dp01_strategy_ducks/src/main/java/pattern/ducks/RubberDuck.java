package pattern.ducks;

import pattern.Duck;
import pattern.behaviors.impl.FlyNoWay;
import pattern.behaviors.impl.Squeak;

public class RubberDuck extends Duck {
    public RubberDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new Squeak();
    }

    @Override
    public void display() {
        System.out.println("I'm a Rubber duck");
    }
}
