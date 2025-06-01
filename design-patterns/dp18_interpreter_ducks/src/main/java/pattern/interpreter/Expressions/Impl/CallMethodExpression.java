package pattern.interpreter.Expressions.Impl;

import pattern.Duck;
import pattern.interpreter.Context;
import pattern.interpreter.Expressions.Expression;

public class CallMethodExpression implements Expression {
    private String duckName;
    private String method;
    
    public CallMethodExpression(String duckName, String method) {
        this.duckName = duckName;
        this.method = method.toUpperCase();
    }
    
    @Override
    public void interpret(Context context) {        Duck duck = context.getDuck(duckName);
        
        if (duck == null) {
            System.out.println("Error: Duck '" + duckName + "' not found!");
            return;
        }
        
        switch (method) {
            case "DISPLAY":
                duck.display();
                break;
            case "FLY":
                duck.performFly();
                break;
            case "QUACK":
                duck.performQuack();
                break;
            case "SWIM":
                duck.swim();
                break;
            default:
                System.out.println("Error: Unknown method: " + method);
        }
    }
}
