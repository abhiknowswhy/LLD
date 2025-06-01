package pattern.interpreter.Expressions;

import pattern.interpreter.Context;

public interface Expression {
    void interpret(Context context);
}
