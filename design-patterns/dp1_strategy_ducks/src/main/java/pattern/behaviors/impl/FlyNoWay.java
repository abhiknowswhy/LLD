package pattern.behaviors.impl;

import pattern.behaviors.FlyBehavior;

public class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("I cannot fly!!");
    }
}
