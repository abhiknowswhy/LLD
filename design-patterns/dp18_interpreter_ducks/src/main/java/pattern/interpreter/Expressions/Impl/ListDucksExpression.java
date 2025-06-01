package pattern.interpreter.Expressions.Impl;

import pattern.interpreter.Context;
import pattern.interpreter.Expressions.Expression;

public class ListDucksExpression implements Expression {
    
    @Override
    public void interpret(Context context) {
        context.listDucks();
    }
}
