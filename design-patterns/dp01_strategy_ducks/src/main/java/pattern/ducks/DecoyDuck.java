package pattern.ducks;

import pattern.Duck;
import pattern.behaviors.impl.FlyNoWay;
import pattern.behaviors.impl.MuteQuack;

public class DecoyDuck extends Duck {
    public DecoyDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new MuteQuack();
    }

    public DecoyDuck(String name) {
        super(name);
        flyBehavior = new FlyNoWay();
        quackBehavior = new MuteQuack();
    }

    @Override
    public void display() {
        if (name != null) {
            System.out.println(name + " is a decoy duck");
        } else {
            System.out.println("I'm a decoy duck");
        }
    }
}
