package pattern;

import pattern.behaviors.FlyBehavior;
import pattern.behaviors.QuackBehavior;

public abstract class Duck {
    protected FlyBehavior flyBehavior;
    protected QuackBehavior quackBehavior;
    protected String name;

    public Duck() {}

    public Duck(String name) {
        this.name = name;
    }

    public abstract void display();

    public void performFly() {
        flyBehavior.fly();
    }

    public void performQuack() {
        quackBehavior.quack();
    }

    public void swim() {
        if (name != null) {
            System.out.println(name + " is swimming. All ducks float, even decoys!");
        } else {
            System.out.println("All ducks float, even decoys!");
        }
    }

    public void setFlyBehavior(FlyBehavior fb) {
        flyBehavior = fb;
    }

    public void setQuackBehavior(QuackBehavior qb) {
        quackBehavior = qb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
