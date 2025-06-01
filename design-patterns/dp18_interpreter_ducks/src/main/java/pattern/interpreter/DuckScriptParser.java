package pattern.interpreter;

import java.util.StringTokenizer;

import pattern.interpreter.Expressions.Expression;
import pattern.interpreter.Expressions.Impl.CallMethodExpression;
import pattern.interpreter.Expressions.Impl.CreateDuckExpression;
import pattern.interpreter.Expressions.Impl.ListDucksExpression;
import pattern.interpreter.Expressions.Impl.SetBehaviorExpression;

public class DuckScriptParser {
    
    public Expression parseCommand(String command) {
        StringTokenizer tokenizer = new StringTokenizer(command.trim());
        
        if (!tokenizer.hasMoreTokens()) {
            return null;
        }
        
        String firstToken = tokenizer.nextToken().toUpperCase();
        switch (firstToken) {
            case "CREATE":
            return parseCreateCommand(tokenizer);
            case "SET":
            return parseSetCommand(tokenizer);
            case "CALL":
            return parseCallCommand(tokenizer);
            case "LIST":
            return parseListCommand(tokenizer);
            default:
            System.out.println("Error: Unknown command: " + firstToken);
            return null;
        }
    }
    private Expression parseCreateCommand(StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Error: CREATE command requires DUCK keyword");
            return null;
        }
        
        String duckKeyword = tokenizer.nextToken().toUpperCase();
        if (!"DUCK".equals(duckKeyword)) {
            System.out.println("Error: Expected DUCK after CREATE, got: " + duckKeyword);
            return null;
        }
        
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Error: CREATE DUCK command requires duck name");
            return null;
        }
        
        String duckName = tokenizer.nextToken();
        
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Error: CREATE DUCK command requires duck type");
            return null;
        }
        
        String duckType = tokenizer.nextToken();
        
        return new CreateDuckExpression(duckName, duckType);
    }
    private Expression parseSetCommand(StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Error: SET command requires duck name");
            return null;
        }
        
        String duckName = tokenizer.nextToken();
        
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Error: SET command requires behavior type");
            return null;
        }
        
        String behaviorType = tokenizer.nextToken();
        
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Error: SET command requires behavior value");
            return null;
        }
        
        String behaviorValue = tokenizer.nextToken();
        
        return new SetBehaviorExpression(duckName, behaviorType, behaviorValue);
    }
    private Expression parseCallCommand(StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Error: CALL command requires duck name");
            return null;
        }
        
        String duckName = tokenizer.nextToken();
        
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Error: CALL command requires method name");
            return null;
        }
        
        String method = tokenizer.nextToken();
        
        return new CallMethodExpression(duckName, method);
    }
    private Expression parseListCommand(StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Error: LIST command requires DUCKS keyword");
            return null;
        }
        
        String ducksKeyword = tokenizer.nextToken().toUpperCase();
        if (!"DUCKS".equals(ducksKeyword)) {
            System.out.println("Error: Expected DUCKS after LIST, got: " + ducksKeyword);
            return null;
        }
        
        return new ListDucksExpression();
    }
}
