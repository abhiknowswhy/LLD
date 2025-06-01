package pattern.interpreter.Expressions.Impl;

import pattern.Duck;
import pattern.ducks.MallardDuck;
import pattern.ducks.RubberDuck;
import pattern.interpreter.Context;
import pattern.interpreter.Expressions.Expression;
import pattern.ducks.DecoyDuck;

public class CreateDuckExpression implements Expression {
    private String duckName;
    private String duckType;
    
    public CreateDuckExpression(String duckName, String duckType) {
        this.duckName = duckName;
        this.duckType = duckType.toUpperCase();
    }
    
    @Override
    public void interpret(Context context) {
        Duck duck = null;
          switch (duckType) {
            case "MALLARD":
                duck = new MallardDuck(duckName);
                break;
            case "RUBBER":
                duck = new RubberDuck(duckName);
                break;
            case "DECOY":
                duck = new DecoyDuck(duckName);
                break;
            default:
                System.out.println("Error: Unknown duck type: " + duckType);
                return;
        }
        
        context.addDuck(duckName, duck);
        System.out.println("Created " + duckType.toLowerCase() + " duck named '" + duckName + "'");
    }
}
