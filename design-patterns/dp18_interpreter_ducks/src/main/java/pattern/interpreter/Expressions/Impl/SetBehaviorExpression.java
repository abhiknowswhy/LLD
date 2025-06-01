package pattern.interpreter.Expressions.Impl;

import pattern.Duck;
import pattern.behaviors.FlyBehavior;
import pattern.behaviors.QuackBehavior;
import pattern.behaviors.impl.*;
import pattern.interpreter.Context;
import pattern.interpreter.Expressions.Expression;

public class SetBehaviorExpression implements Expression {
    private String duckName;
    private String behaviorType;
    private String behaviorValue;
    
    public SetBehaviorExpression(String duckName, String behaviorType, String behaviorValue) {
        this.duckName = duckName;
        this.behaviorType = behaviorType.toUpperCase();
        this.behaviorValue = behaviorValue.toUpperCase();
    }
    
    @Override
    public void interpret(Context context) {
        Duck duck = context.getDuck(duckName);
          if (duck == null) {
            System.out.println("Error: Duck '" + duckName + "' not found!");
            return;
        }
        
        switch (behaviorType) {
            case "FLY_BEHAVIOR":
                setFlyBehavior(duck);
                break;
            case "QUACK_BEHAVIOR":
                setQuackBehavior(duck);
                break;
            default:
                System.out.println("Error: Unknown behavior type: " + behaviorType);
        }
    }
    
    private void setFlyBehavior(Duck duck) {
        FlyBehavior behavior = null;
        
        switch (behaviorValue) {
            case "FLY_WITH_WINGS":
                behavior = new FlyWithWings();
                break;
            case "FLY_NO_WAY":
                behavior = new FlyNoWay();
                break;            default:
                System.out.println("Error: Unknown fly behavior: " + behaviorValue);
                return;
        }
        
        duck.setFlyBehavior(behavior);
        System.out.println("Set " + duck.getName() + "'s fly behavior to " + behaviorValue.toLowerCase());
    }
    
    private void setQuackBehavior(Duck duck) {
        QuackBehavior behavior = null;
        
        switch (behaviorValue) {
            case "QUACK":
                behavior = new Quack();
                break;
            case "SQUEAK":
                behavior = new Squeak();
                break;
            case "MUTE_QUACK":
                behavior = new MuteQuack();
                break;            default:
                System.out.println("Error: Unknown quack behavior: " + behaviorValue);
                return;
        }
        
        duck.setQuackBehavior(behavior);
        System.out.println("Set " + duck.getName() + "'s quack behavior to " + behaviorValue.toLowerCase());
    }
}
