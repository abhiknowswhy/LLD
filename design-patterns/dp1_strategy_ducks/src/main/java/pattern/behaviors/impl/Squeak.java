package pattern.behaviors.impl;

import pattern.behaviors.QuackBehavior;

public class Squeak implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Squeak!!");
    }
}
