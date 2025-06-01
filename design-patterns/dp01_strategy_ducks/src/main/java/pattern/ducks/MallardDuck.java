package pattern.ducks;

import pattern.Duck;
import pattern.behaviors.impl.FlyWithWings;
import pattern.behaviors.impl.Quack;

public class MallardDuck extends Duck {
    public MallardDuck() {
        flyBehavior = new FlyWithWings();
        quackBehavior = new Quack();
    }

    public MallardDuck(String name) {
        super(name);
        flyBehavior = new FlyWithWings();
        quackBehavior = new Quack();
    }

    @Override
    public void display() {
        if (name != null) {
            System.out.println(name + " is a real Mallard duck");
        } else {
            System.out.println("I'm a real Mallard duck");
        }
    }
}
