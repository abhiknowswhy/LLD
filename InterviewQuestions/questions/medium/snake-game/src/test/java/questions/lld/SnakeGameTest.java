package questions.lld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.lld.SnakeGame.*;

import static org.junit.jupiter.api.Assertions.*;

class SnakeGameTest {

    private GameBoard game;

    @BeforeEach
    void setUp() {
        game = new GameBoard(10, 10);
    }

    @Test
    void testInitialState() {
        assertFalse(game.isGameOver());
        assertEquals(0, game.getScore());
        assertEquals(1, game.getSnake().getLength());
        assertEquals(new Position(5, 5), game.getSnake().getHead());
    }

    @Test
    void testMoveRight() {
        game.setDirection(Direction.RIGHT);
        game.tick();
        assertEquals(new Position(5, 6), game.getSnake().getHead());
    }

    @Test
    void testMoveMultipleDirections() {
        game.setDirection(Direction.RIGHT);
        game.tick();
        game.setDirection(Direction.DOWN);
        game.tick();
        assertEquals(new Position(6, 6), game.getSnake().getHead());
    }

    @Test
    void testEatFood() {
        // Place food right in front of the snake
        game.setFood(new Position(5, 6));
        game.setDirection(Direction.RIGHT);
        game.tick();
        assertEquals(1, game.getScore());
        assertEquals(2, game.getSnake().getLength());
    }

    @Test
    void testWallCollisionRight() {
        game.setDirection(Direction.RIGHT);
        // Start at (5,5), move right 4 times → (5,9)
        for (int i = 0; i < 4; i++) {
            assertTrue(game.tick());
        }
        assertEquals(new Position(5, 9), game.getSnake().getHead());
        assertFalse(game.isGameOver());

        // Next tick hits wall (col 10 = out of bounds)
        assertFalse(game.tick());
        assertTrue(game.isGameOver());
    }

    @Test
    void testWallCollisionTop() {
        game.setDirection(Direction.UP);
        for (int i = 0; i < 5; i++) game.tick(); // row 5 -> row 0
        assertFalse(game.isGameOver());
        game.tick(); // row -1 = wall
        assertTrue(game.isGameOver());
    }

    @Test
    void testPrevent180Turn() {
        // Grow the snake first (180-turn prevention only applies when length > 1)
        game.setFood(new Position(5, 6));
        game.setDirection(Direction.RIGHT);
        game.tick(); // eat food at 5,6 → length 2, head at (5,6)

        // Try to go left (opposite) — should be ignored, continues RIGHT
        game.setDirection(Direction.LEFT);
        game.tick();
        assertEquals(new Position(5, 7), game.getSnake().getHead());
    }

    @Test
    void testSelfCollision() {
        // Grow the snake to length 5 so it can collide with itself
        game.setFood(new Position(5, 6));
        game.setDirection(Direction.RIGHT);
        game.tick(); // eat at 5,6 — length 2

        game.setFood(new Position(5, 7));
        game.tick(); // eat at 5,7 — length 3

        game.setFood(new Position(5, 8));
        game.tick(); // eat at 5,8 — length 4

        game.setFood(new Position(5, 9));
        game.tick(); // eat at 5,9 — length 5

        // Body: [5,9], [5,8], [5,7], [5,6], [5,5]
        game.setDirection(Direction.DOWN);
        game.tick(); // head at 6,9, body: [6,9],[5,9],[5,8],[5,7],[5,6]
        game.setDirection(Direction.LEFT);
        game.tick(); // head at 6,8, body: [6,8],[6,9],[5,9],[5,8],[5,7]
        game.setDirection(Direction.UP);
        game.tick(); // head at 5,8 — collides with body segment at [5,8]!
        assertTrue(game.isGameOver());
    }

    @Test
    void testRender() {
        String rendered = game.render();
        assertNotNull(rendered);
        assertTrue(rendered.contains("@")); // head
        assertTrue(rendered.contains("*")); // food
    }

    @Test
    void testScoreIncrements() {
        game.setFood(new Position(5, 6));
        game.setDirection(Direction.RIGHT);
        game.tick();
        assertEquals(1, game.getScore());

        game.setFood(new Position(5, 7));
        game.tick();
        assertEquals(2, game.getScore());
    }

    @Test
    void testPositionEquality() {
        Position p1 = new Position(3, 4);
        Position p2 = new Position(3, 4);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testDirectionOpposite() {
        assertEquals(Direction.DOWN, Direction.UP.opposite());
        assertEquals(Direction.UP, Direction.DOWN.opposite());
        assertEquals(Direction.RIGHT, Direction.LEFT.opposite());
        assertEquals(Direction.LEFT, Direction.RIGHT.opposite());
    }
}
