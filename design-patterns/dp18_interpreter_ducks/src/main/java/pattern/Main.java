package pattern;

import pattern.interpreter.DuckScriptInterpreter;
import pattern.interpreter.ScriptLoader;

/**
 * MergedMain - Combined implementation of Main and EnhancedMain
 * Demonstrates the Interpreter Pattern using the DuckScript language
 */
public class Main {
    public static void main(String[] args) {
        // ============= BASIC DEMONSTRATION (from Main.java) =============
        basicDemo();
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // ============= ENHANCED DEMONSTRATION (from EnhancedMain.java) =============
        enhancedDemo();
    }
    
    /**
     * Basic demonstration from original Main.java
     */
    private static void basicDemo() {
        System.out.println("=== BASIC DUCK SCRIPT DEMONSTRATION ===\n");
        
        DuckScriptInterpreter interpreter = new DuckScriptInterpreter();
        
        // Example script that creates and manipulates ducks
        String script = """
            // Create different types of ducks
            CREATE DUCK Donald MALLARD
            CREATE DUCK Rubber RUBBER
            CREATE DUCK Woody DECOY
            
            // List all created ducks
            LIST DUCKS
            
            // Test initial behaviors
            CALL Donald DISPLAY
            CALL Donald FLY
            CALL Donald QUACK
            
            CALL Rubber DISPLAY
            CALL Rubber FLY
            CALL Rubber QUACK
            
            // Change behaviors
            SET Donald FLY_BEHAVIOR FLY_NO_WAY
            SET Rubber QUACK_BEHAVIOR QUACK
            
            // Test modified behaviors
            CALL Donald FLY
            CALL Rubber QUACK
            
            // Test swimming
            CALL Donald SWIM
            CALL Woody SWIM
            """;
        
        interpreter.interpretScript(script);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Interactive mode - Enter commands (type 'exit' to quit):");
        System.out.println("Available commands:");
        System.out.println("  CREATE DUCK <name> <type>  - Create a duck (MALLARD, RUBBER, DECOY)");
        System.out.println("  SET <name> <behavior> <value> - Set behavior");
        System.out.println("  CALL <name> <method> - Call duck method (DISPLAY, FLY, QUACK, SWIM)");
        System.out.println("  LIST DUCKS - List all ducks");
        System.out.println("=".repeat(50));
        
        // Simple interactive mode simulation
        String[] interactiveCommands = {
            "CREATE DUCK Daffy MALLARD",
            "CALL Daffy DISPLAY",
            "SET Daffy QUACK_BEHAVIOR SQUEAK",
            "CALL Daffy QUACK",
            "LIST DUCKS"
        };
        
        System.out.println("\nSimulating interactive commands:");
        for (String command : interactiveCommands) {
            System.out.println("> " + command);
            interpreter.interpretCommand(command);
        }
    }
    
    /**
     * Enhanced demonstration from EnhancedMain.java
     */
    private static void enhancedDemo() {
        System.out.println("=== ENHANCED DUCK SCRIPT DEMONSTRATION ===\n");
        
        DuckScriptInterpreter interpreter = new DuckScriptInterpreter();
        
        // Run the duck farm script
        System.out.println("*** EXECUTING DUCK FARM SCRIPT ***");
        String farmScript = ScriptLoader.loadScriptFromResources("duck_farm.ducks");
        if (farmScript != null) {
            interpreter.interpretScript(farmScript);
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Create a fresh interpreter for the training script
        interpreter = new DuckScriptInterpreter();
        
        System.out.println("*** EXECUTING DUCK TRAINING SCRIPT ***");
        String trainingScript = ScriptLoader.loadScriptFromResources("duck_training.ducks");
        if (trainingScript != null) {
            interpreter.interpretScript(trainingScript);
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Demonstrate error handling
        System.out.println("*** ERROR HANDLING DEMONSTRATION ***");
        interpreter = new DuckScriptInterpreter();
        
        String[] errorCommands = {
            "CREATE DUCK", // Missing parameters
            "CREATE DUCK Broken UNKNOWN", // Unknown duck type
            "SET NonExistent FLY_BEHAVIOR FLY_WITH_WINGS", // Duck doesn't exist
            "SET", // Missing parameters
            "CALL NonExistent DISPLAY", // Duck doesn't exist
            "UNKNOWN_COMMAND", // Invalid command
            "LIST", // Missing DUCKS keyword
        };
        
        for (String command : errorCommands) {
            System.out.println("\nTrying: " + command);
            interpreter.interpretCommand(command);
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Demonstrate interpreter pattern benefits
        System.out.println("*** INTERPRETER PATTERN BENEFITS DEMO ***");
        interpreter = new DuckScriptInterpreter();
        
        // Show that complex behaviors can be scripted
        String complexScript = """
            // Demonstrate complex scripting capabilities
            CREATE DUCK Alpha MALLARD
            CREATE DUCK Beta RUBBER
            CREATE DUCK Gamma DECOY
            
            // Create a behavior transformation sequence
            CALL Alpha DISPLAY
            CALL Alpha FLY
            SET Alpha FLY_BEHAVIOR FLY_NO_WAY
            CALL Alpha FLY
            SET Alpha FLY_BEHAVIOR FLY_WITH_WINGS
            CALL Alpha FLY
            
            // Show polymorphic behavior
            CALL Alpha QUACK
            CALL Beta QUACK
            CALL Gamma QUACK
            
            // Final state
            LIST DUCKS
            """;
        
        interpreter.interpretScript(complexScript);
    }
}
