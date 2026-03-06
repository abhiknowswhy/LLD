package questions.lld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.lld.SnakeAndLadder.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SnakeAndLadderTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(100);
        board.addSnake(99, 10);
        board.addSnake(50, 5);
        board.addLadder(3, 45);
        board.addLadder(20, 80);
    }

    @Test
    void testBoardSnake() {
        assertEquals(10, board.getFinalPosition(99));
        assertTrue(board.getEntityAt(99).isSnake());
    }

    @Test
    void testBoardLadder() {
        assertEquals(45, board.getFinalPosition(3));
        assertTrue(board.getEntityAt(3).isLadder());
    }

    @Test
    void testNormalPosition() {
        assertEquals(15, board.getFinalPosition(15));
        assertFalse(board.hasEntityAt(15));
    }

    @Test
    void testPlayerMovement() {
        Game game = new Game(board, 1, List.of("Alice", "Bob"));
        game.playTurn(4); // Alice: 0 -> 4
        assertEquals(4, game.getPlayers().get(0).getPosition());
    }

    @Test
    void testPlayerHitsLadder() {
        Game game = new Game(board, 1, List.of("Alice"));
        game.playTurn(3); // Alice: 0 -> 3 -> ladder -> 45
        assertEquals(45, game.getPlayers().get(0).getPosition());
    }

    @Test
    void testPlayerHitsSnake() {
        Game game = new Game(board, 1, List.of("Alice"));
        game.playTurn(50); // Alice: 0 -> 50 -> snake -> 5
        assertEquals(5, game.getPlayers().get(0).getPosition());
    }

    @Test
    void testCannotMoveBeyondBoard() {
        Game game = new Game(board, 1, List.of("Alice"));
        game.playTurn(95); // Alice: 0 -> 95
        assertEquals(95, game.getPlayers().get(0).getPosition());
        game.playTurn(6); // 95 + 6 = 101 > 100, stays at 95
        assertEquals(95, game.getPlayers().get(0).getPosition());
    }

    @Test
    void testWinCondition() {
        Game game = new Game(board, 1, List.of("Alice"));
        // Move to position 96 first (skip snake at 99)
        game.playTurn(96); // 0 -> 96
        game.playTurn(4);  // 96 -> 100 = WIN
        assertTrue(game.isGameOver());
        assertEquals("Alice", game.getWinner().getName());
    }

    @Test
    void testTurnOrder() {
        Game game = new Game(board, 1, List.of("Alice", "Bob"));
        assertEquals("Alice", game.getCurrentPlayer().getName());
        game.playTurn(1);
        assertEquals("Bob", game.getCurrentPlayer().getName());
        game.playTurn(1);
        assertEquals("Alice", game.getCurrentPlayer().getName());
    }

    @Test
    void testInvalidSnake() {
        assertThrows(IllegalArgumentException.class, () -> board.addSnake(10, 20)); // head < tail
    }

    @Test
    void testInvalidLadder() {
        assertThrows(IllegalArgumentException.class, () -> board.addLadder(30, 10)); // bottom > top
    }

    @Test
    void testMoveLog() {
        Game game = new Game(board, 1, List.of("Alice"));
        game.playTurn(5);
        assertEquals(1, game.getMoveLog().size());
        assertTrue(game.getMoveLog().get(0).contains("Alice"));
    }
}
