package pattern.interpreter;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import pattern.interpreter.Expressions.Expression;

public class DuckScriptInterpreter {
    private DuckScriptParser parser;
    private Context context;
    
    public DuckScriptInterpreter() {
        this.parser = new DuckScriptParser();
        this.context = new Context();
    }
    
    public void interpretScript(String script) {
        System.out.println("=== Executing Duck Script ===");
        
        BufferedReader reader = new BufferedReader(new StringReader(script));
        List<String> lines = new ArrayList<>();
        
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("//")) {
                    lines.add(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading script: " + e.getMessage());
            return;
        }
        
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            System.out.println("\nLine " + (i + 1) + ": " + line);
            
            Expression expression = parser.parseCommand(line);
            if (expression != null) {
                expression.interpret(context);
            }
        }
        
        System.out.println("\n=== Script Execution Complete ===");
    }
    
    public void interpretCommand(String command) {
        Expression expression = parser.parseCommand(command);
        if (expression != null) {
            expression.interpret(context);
        }
    }
    
    public Context getContext() {
        return context;
    }
}
