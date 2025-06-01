package pattern.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import pattern.Duck;
import pattern.ducks.MallardDuck;
import pattern.ducks.RubberDuck;
import pattern.ducks.DecoyDuck;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Context component
 */
@DisplayName("Context Tests")
public class ContextTest {

    private Context context;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        context = new Context();
        
        // Capture console output for testing
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    void tearDown() {
        System.setOut(originalOut);
    }

    @Nested
    @DisplayName("Duck Management Tests")
    class DuckManagementTests {

        @Test
        @DisplayName("Should add ducks successfully")
        void testAddDucks() {
            Duck mallard = new MallardDuck("Donald");
            Duck rubber = new RubberDuck("Squeaky");
            Duck decoy = new DecoyDuck("Hunter");

            context.addDuck("Donald", mallard);
            context.addDuck("Squeaky", rubber);
            context.addDuck("Hunter", decoy);

            assertTrue(context.hasDuck("Donald"));
            assertTrue(context.hasDuck("Squeaky"));
            assertTrue(context.hasDuck("Hunter"));
        }

        @Test
        @DisplayName("Should retrieve ducks correctly")
        void testGetDucks() {
            Duck mallard = new MallardDuck("Donald");
            context.addDuck("Donald", mallard);

            Duck retrieved = context.getDuck("Donald");
            assertNotNull(retrieved);
            assertSame(mallard, retrieved);
            assertEquals("Donald", retrieved.getName());
        }

        @Test
        @DisplayName("Should return null for non-existent ducks")
        void testGetNonExistentDuck() {
            Duck retrieved = context.getDuck("NonExistent");
            assertNull(retrieved);
        }

        @Test
        @DisplayName("Should check duck existence correctly")
        void testHasDuck() {
            assertFalse(context.hasDuck("Donald"));

            Duck mallard = new MallardDuck("Donald");
            context.addDuck("Donald", mallard);

            assertTrue(context.hasDuck("Donald"));
            assertFalse(context.hasDuck("Daffy"));
        }

        @Test
        @DisplayName("Should handle case sensitive duck names")
        void testCaseSensitiveDuckNames() {
            Duck mallard = new MallardDuck("Donald");
            context.addDuck("Donald", mallard);

            assertTrue(context.hasDuck("Donald"));
            assertFalse(context.hasDuck("donald"));
            assertFalse(context.hasDuck("DONALD"));
        }
    }

    @Nested
    @DisplayName("List Ducks Tests")
    class ListDucksTests {

        @Test
        @DisplayName("Should list empty duck registry")
        void testListEmptyDucks() {
            context.listDucks();
            
            String output = outputStream.toString();
            assertTrue(output.contains("Available ducks:"));
        }

        @Test
        @DisplayName("Should list single duck")
        void testListSingleDuck() {
            Duck mallard = new MallardDuck("Donald");
            context.addDuck("Donald", mallard);
            
            context.listDucks();
            
            String output = outputStream.toString();
            assertTrue(output.contains("Available ducks:"));
            assertTrue(output.contains("- Donald (MallardDuck)"));
        }

        @Test
        @DisplayName("Should list multiple ducks")
        void testListMultipleDucks() {
            Duck mallard = new MallardDuck("Donald");
            Duck rubber = new RubberDuck("Squeaky");
            Duck decoy = new DecoyDuck("Hunter");

            context.addDuck("Donald", mallard);
            context.addDuck("Squeaky", rubber);
            context.addDuck("Hunter", decoy);
            
            context.listDucks();
            
            String output = outputStream.toString();
            assertTrue(output.contains("Available ducks:"));
            assertTrue(output.contains("- Donald (MallardDuck)"));
            assertTrue(output.contains("- Squeaky (RubberDuck)"));
            assertTrue(output.contains("- Hunter (DecoyDuck)"));
        }

        @Test
        @DisplayName("Should list ducks with correct class names")
        void testListDucksWithCorrectClassNames() {
            context.addDuck("M1", new MallardDuck("M1"));
            context.addDuck("R1", new RubberDuck("R1"));
            context.addDuck("D1", new DecoyDuck("D1"));
            
            context.listDucks();
            
            String output = outputStream.toString();
            assertTrue(output.contains("(MallardDuck)"));
            assertTrue(output.contains("(RubberDuck)"));
            assertTrue(output.contains("(DecoyDuck)"));
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {        @Test
        @DisplayName("Should handle null duck names")
        void testNullDuckNames() {
            Duck mallard = new MallardDuck("Donald");
            
            // Adding with null name should not crash
            assertDoesNotThrow(() -> context.addDuck(null, mallard));
            
            // Getting with null name should return the duck we added with null key
            assertEquals(mallard, context.getDuck(null));
            
            // Checking with null name should return true since we added one
            assertTrue(context.hasDuck(null));
        }

        @Test
        @DisplayName("Should handle null ducks")
        void testNullDucks() {
            // Adding null duck should not crash
            assertDoesNotThrow(() -> context.addDuck("NullDuck", null));
            
            // Should be able to retrieve null duck
            assertNull(context.getDuck("NullDuck"));
            
            // Should report that it has the duck name
            assertTrue(context.hasDuck("NullDuck"));
        }

        @Test
        @DisplayName("Should handle duck name overwrites")
        void testDuckNameOverwrites() {
            Duck mallard1 = new MallardDuck("Donald1");
            Duck mallard2 = new MallardDuck("Donald2");

            context.addDuck("Donald", mallard1);
            assertEquals("Donald1", context.getDuck("Donald").getName());

            // Overwrite with different duck
            context.addDuck("Donald", mallard2);
            assertEquals("Donald2", context.getDuck("Donald").getName());
            assertSame(mallard2, context.getDuck("Donald"));
        }

        @Test
        @DisplayName("Should handle empty string duck names")
        void testEmptyStringDuckNames() {
            Duck mallard = new MallardDuck("EmptyName");
            
            assertDoesNotThrow(() -> context.addDuck("", mallard));
            assertTrue(context.hasDuck(""));
            assertSame(mallard, context.getDuck(""));
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should maintain duck state across operations")
        void testMaintainDuckStateAcrossOperations() {
            // Add multiple ducks
            context.addDuck("Duck1", new MallardDuck("Duck1"));
            context.addDuck("Duck2", new RubberDuck("Duck2"));
            context.addDuck("Duck3", new DecoyDuck("Duck3"));

            // Verify all exist
            assertTrue(context.hasDuck("Duck1"));
            assertTrue(context.hasDuck("Duck2"));
            assertTrue(context.hasDuck("Duck3"));

            // Retrieve and verify
            assertInstanceOf(MallardDuck.class, context.getDuck("Duck1"));
            assertInstanceOf(RubberDuck.class, context.getDuck("Duck2"));
            assertInstanceOf(DecoyDuck.class, context.getDuck("Duck3"));

            // List and verify output
            context.listDucks();
            String output = outputStream.toString();
            assertTrue(output.contains("Duck1"));
            assertTrue(output.contains("Duck2"));
            assertTrue(output.contains("Duck3"));
        }

        @Test
        @DisplayName("Should work with interpreter pattern workflow")
        void testInterpreterPatternWorkflow() {
            // Simulate interpreter pattern usage
            Duck duck = new MallardDuck("TestDuck");
            context.addDuck("TestDuck", duck);

            // Verify duck can be retrieved for command execution
            Duck retrieved = context.getDuck("TestDuck");
            assertNotNull(retrieved);
            
            // Verify duck methods can be called
            assertDoesNotThrow(() -> {
                retrieved.display();
                retrieved.performFly();
                retrieved.performQuack();
                retrieved.swim();
            });
        }
    }
}
