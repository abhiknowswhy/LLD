package pattern;

import pattern.ducks.MallardDuck;
import pattern.ducks.RubberDuck;

public class Main {
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.display();
        mallard.performQuack();
        mallard.performFly();

        Duck rubberDuck = new RubberDuck();
        rubberDuck.display();
        rubberDuck.performQuack();
        rubberDuck.performFly();
    }
}