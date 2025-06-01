package pattern.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import pattern.Duck;
import pattern.behaviors.impl.*;
import pattern.ducks.MallardDuck;
import pattern.ducks.RubberDuck;
import pattern.ducks.DecoyDuck;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Duck Script Interpreter Tests")
public class DuckScriptInterpreterTest {

    private DuckScriptInterpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = new DuckScriptInterpreter();
    }

    @Nested
    @DisplayName("Basic Command Tests")
    class BasicCommandTests {

        @Test
        @DisplayName("Should create ducks of all types")
        void testCreateDucksAllTypes() {
            // Act
            interpreter.interpretCommand("CREATE DUCK Donald MALLARD");
            interpreter.interpretCommand("CREATE DUCK Rubber RUBBER");
            interpreter.interpretCommand("CREATE DUCK Decoy DECOY");

            // Assert
            Context context = interpreter.getContext();
            assertTrue(context.hasDuck("Donald"));
            assertTrue(context.hasDuck("Rubber"));
            assertTrue(context.hasDuck("Decoy"));

            // Verify correct types
            assertTrue(context.getDuck("Donald") instanceof MallardDuck);
            assertTrue(context.getDuck("Rubber") instanceof RubberDuck);
            assertTrue(context.getDuck("Decoy") instanceof DecoyDuck);
        }

        @Test
        @DisplayName("Should call duck behaviors")
        void testCallDuckBehaviors() {
            // Arrange
            Duck spyDuck = spy(new MallardDuck("TestDuck"));
            interpreter.getContext().addDuck("TestDuck", spyDuck);
            
            // Act & Assert
            interpreter.interpretCommand("CALL TestDuck DISPLAY");
            verify(spyDuck).display();
            
            interpreter.interpretCommand("CALL TestDuck FLY");
            verify(spyDuck).performFly();
            
            interpreter.interpretCommand("CALL TestDuck QUACK");
            verify(spyDuck).performQuack();
        }

        @Test
        @DisplayName("Should set duck behaviors")
        void testSetDuckBehaviors() {
            // Arrange
            Duck spyDuck = spy(new MallardDuck("FlexibleDuck"));            interpreter.getContext().addDuck("FlexibleDuck", spyDuck);
            
            // Act - Change fly behavior
            interpreter.interpretCommand("SET FlexibleDuck FLY_BEHAVIOR FLY_NO_WAY");
            
            // Assert - Verify behavior was set and used
            verify(spyDuck).setFlyBehavior(any(FlyNoWay.class));
            spyDuck.performFly();
            verify(spyDuck).performFly();
            
            // Act - Change quack behavior
            interpreter.interpretCommand("SET FlexibleDuck QUACK_BEHAVIOR MUTE_QUACK");
            
            // Assert
            verify(spyDuck).setQuackBehavior(any(MuteQuack.class));
            spyDuck.performQuack();
            verify(spyDuck).performQuack();
        }
    }

    @Nested
    @DisplayName("Script Execution Tests")
    class ScriptExecutionTests {        
        
        @Test
        @DisplayName("Should handle complex script with behavior changes")
        void testComplexScriptExecution() {
            String complexScript = """
                CREATE DUCK Alpha MALLARD
                CREATE DUCK Beta RUBBER
                CALL Alpha DISPLAY
                SET Alpha FLY_BEHAVIOR FLY_NO_WAY
                CALL Alpha FLY
                LIST DUCKS
                """;

            interpreter.interpretScript(complexScript);
            
            // Assert duck creation
            Context context = interpreter.getContext();
            assertTrue(context.hasDuck("Alpha"));
            assertTrue(context.hasDuck("Beta"));
            assertTrue(context.getDuck("Alpha") instanceof MallardDuck);
            assertTrue(context.getDuck("Beta") instanceof RubberDuck);
        }
        
        @Test
        @DisplayName("Should handle script with comments")
        void testScriptWithComments() {
            String scriptWithComments = """
                // This is a comment
                CREATE DUCK CommentedDuck MALLARD
                // Another comment
                CALL CommentedDuck DISPLAY
                """;
                
            // Execute script
            interpreter.interpretScript(scriptWithComments);
            
            // Assert duck was created with correct type
            Context context = interpreter.getContext();
            assertTrue(context.hasDuck("CommentedDuck"), "Duck should be created");
            assertTrue(context.getDuck("CommentedDuck") instanceof MallardDuck, 
                "Duck should be a MallardDuck");
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should handle missing parameters gracefully")
        void testMissingParameters() {
            // Arrange
            Context spyContext = spy(interpreter.getContext());
            
            // Act
            interpreter.interpretCommand("CREATE DUCK");
            
            // Assert
            verify(spyContext, never()).addDuck(anyString(), any(Duck.class));
        }

        @Test
        @DisplayName("Should handle unknown duck types")
        void testUnknownDuckType() {
            // Act
            interpreter.interpretCommand("CREATE DUCK Broken UNKNOWN");
            
            // Assert
            Context context = interpreter.getContext();
            assertFalse(context.hasDuck("Broken"));
        }

        @Test
        @DisplayName("Should handle non-existent ducks")
        void testNonExistentDucks() {
            // Arrange
            Context spyContext = spy(interpreter.getContext());
            
            // Act
            interpreter.interpretCommand("SET NonExistent FLY_BEHAVIOR FLY_WITH_WINGS");
            
            // Assert
            verify(spyContext, never()).getDuck("NonExistent");
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should demonstrate interpreter pattern benefits")
        void testInterpreterPatternBenefits() {
            // Arrange
            Duck spyDuck = spy(new MallardDuck("FlexibleDuck"));
            interpreter.getContext().addDuck("FlexibleDuck", spyDuck);
            
            // Test runtime behavior modification
            interpreter.interpretCommand("CALL FlexibleDuck FLY");
            verify(spyDuck).performFly();
            
            // Change behavior
            interpreter.interpretCommand("SET FlexibleDuck FLY_BEHAVIOR FLY_NO_WAY");
            verify(spyDuck).setFlyBehavior(any(FlyNoWay.class));
            
            interpreter.interpretCommand("CALL FlexibleDuck FLY");
            verify(spyDuck, times(2)).performFly();
            
            // Change back
            interpreter.interpretCommand("SET FlexibleDuck FLY_BEHAVIOR FLY_WITH_WINGS");
            verify(spyDuck).setFlyBehavior(any(FlyWithWings.class));
            
            interpreter.interpretCommand("CALL FlexibleDuck FLY");
            verify(spyDuck, times(3)).performFly();
        }
    }
}
