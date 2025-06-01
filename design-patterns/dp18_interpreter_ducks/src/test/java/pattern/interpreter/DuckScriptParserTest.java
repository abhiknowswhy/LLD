package pattern.interpreter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

import pattern.interpreter.Expressions.Expression;
import pattern.interpreter.Expressions.Impl.CreateDuckExpression;
import pattern.interpreter.Expressions.Impl.CallMethodExpression;
import pattern.interpreter.Expressions.Impl.SetBehaviorExpression;
import pattern.interpreter.Expressions.Impl.ListDucksExpression;

/**
 * Unit tests for the DuckScriptParser component
 */
@DisplayName("Duck Script Parser Tests")
public class DuckScriptParserTest {

    private DuckScriptParser parser;

    @BeforeEach
    void setUp() {
        parser = new DuckScriptParser();
    }

    @Nested
    @DisplayName("Command Parsing Tests")
    class CommandParsingTests {

        @Test
        @DisplayName("Should parse CREATE DUCK commands correctly")
        void testParseCreateDuckCommand() {
            Expression expr = parser.parseCommand("CREATE DUCK Donald MALLARD");
            assertNotNull(expr);
            assertInstanceOf(CreateDuckExpression.class, expr);
        }

        @Test
        @DisplayName("Should parse SET behavior commands correctly")
        void testParseSetBehaviorCommand() {
            Expression expr = parser.parseCommand("SET Donald FLY_BEHAVIOR FLY_NO_WAY");
            assertNotNull(expr);
            assertInstanceOf(SetBehaviorExpression.class, expr);
        }

        @Test
        @DisplayName("Should parse CALL method commands correctly")
        void testParseCallMethodCommand() {
            Expression expr = parser.parseCommand("CALL Donald DISPLAY");
            assertNotNull(expr);
            assertInstanceOf(CallMethodExpression.class, expr);
        }

        @Test
        @DisplayName("Should parse LIST DUCKS commands correctly")
        void testParseListDucksCommand() {
            Expression expr = parser.parseCommand("LIST DUCKS");
            assertNotNull(expr);
            assertInstanceOf(ListDucksExpression.class, expr);
        }

        @Test
        @DisplayName("Should handle case insensitive commands")
        void testCaseInsensitiveCommands() {
            Expression expr1 = parser.parseCommand("create duck Donald MALLARD");
            Expression expr2 = parser.parseCommand("CREATE DUCK Donald MALLARD");
            
            assertNotNull(expr1);
            assertNotNull(expr2);
            assertEquals(expr1.getClass(), expr2.getClass());
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should return null for empty commands")
        void testEmptyCommand() {
            assertNull(parser.parseCommand(""));
            assertNull(parser.parseCommand("   "));
        }

        @Test
        @DisplayName("Should return null for unknown commands")
        void testUnknownCommand() {
            Expression expr = parser.parseCommand("UNKNOWN_COMMAND test");
            assertNull(expr);
        }

        @Test
        @DisplayName("Should return null for incomplete CREATE commands")
        void testIncompleteCreateCommand() {
            assertNull(parser.parseCommand("CREATE"));
            assertNull(parser.parseCommand("CREATE DUCK"));
            assertNull(parser.parseCommand("CREATE DUCK Donald"));
        }

        @Test
        @DisplayName("Should return null for incomplete SET commands")
        void testIncompleteSetCommand() {
            assertNull(parser.parseCommand("SET"));
            assertNull(parser.parseCommand("SET Donald"));
            assertNull(parser.parseCommand("SET Donald FLY_BEHAVIOR"));
        }

        @Test
        @DisplayName("Should return null for incomplete CALL commands")
        void testIncompleteCallCommand() {
            assertNull(parser.parseCommand("CALL"));
            assertNull(parser.parseCommand("CALL Donald"));
        }

        @Test
        @DisplayName("Should return null for incomplete LIST commands")
        void testIncompleteListCommand() {
            assertNull(parser.parseCommand("LIST"));
            assertNull(parser.parseCommand("LIST WRONG"));
        }
    }

    @Nested
    @DisplayName("Complex Command Tests")
    class ComplexCommandTests {

        @Test
        @DisplayName("Should handle commands with extra whitespace")
        void testCommandsWithWhitespace() {
            Expression expr1 = parser.parseCommand("  CREATE   DUCK   Donald   MALLARD  ");
            Expression expr2 = parser.parseCommand("CREATE DUCK Donald MALLARD");
            
            assertNotNull(expr1);
            assertNotNull(expr2);
            assertEquals(expr1.getClass(), expr2.getClass());
        }

        @Test
        @DisplayName("Should parse all duck types correctly")
        void testAllDuckTypes() {
            assertNotNull(parser.parseCommand("CREATE DUCK Duck1 MALLARD"));
            assertNotNull(parser.parseCommand("CREATE DUCK Duck2 RUBBER"));
            assertNotNull(parser.parseCommand("CREATE DUCK Duck3 DECOY"));
        }

        @Test
        @DisplayName("Should parse all behavior types correctly")
        void testAllBehaviorTypes() {
            assertNotNull(parser.parseCommand("SET Duck FLY_BEHAVIOR FLY_WITH_WINGS"));
            assertNotNull(parser.parseCommand("SET Duck FLY_BEHAVIOR FLY_NO_WAY"));
            assertNotNull(parser.parseCommand("SET Duck QUACK_BEHAVIOR QUACK"));
            assertNotNull(parser.parseCommand("SET Duck QUACK_BEHAVIOR SQUEAK"));
            assertNotNull(parser.parseCommand("SET Duck QUACK_BEHAVIOR MUTE_QUACK"));
        }

        @Test
        @DisplayName("Should parse all method calls correctly")
        void testAllMethodCalls() {
            assertNotNull(parser.parseCommand("CALL Duck DISPLAY"));
            assertNotNull(parser.parseCommand("CALL Duck FLY"));
            assertNotNull(parser.parseCommand("CALL Duck QUACK"));
            assertNotNull(parser.parseCommand("CALL Duck SWIM"));
        }
    }
}
