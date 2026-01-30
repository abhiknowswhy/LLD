package basic.lld;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import basic.lld.Game.Game;
import basic.lld.Player.Player;
import basic.lld.Player.Symbol;

public class GameDemoTest {
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        player1 = new Player(1, "Alice", Symbol.X);
        player2 = new Player(2, "Bob", Symbol.O);
    }

    @Test
    public void testWinDiagonal() {
        System.out.println("=== Test: Win Diagonal ===\n");
        Game game = new Game(player1, player2, 3);

        playMove(game, 0, 0);  // Alice X
        playMove(game, 1, 0);  // Bob O
        playMove(game, 1, 1);  // Alice X
        playMove(game, 2, 0);  // Bob O
        playMove(game, 2, 2);  // Alice X - Alice wins!

        game.displayGameState();
        
        assertTrue(game.isGameOver(), "Game should be over after winning move");
        assertNotNull(game.getWinner(), "Winner should not be null");
        assertEquals(player1, game.getWinner(), "Alice should be the winner");
        
        System.out.println("Winner: " + game.getWinner());
        System.out.println("Test passed!\n");
    }

    @Test
    public void testUndoMove() {
        System.out.println("=== Test: Undo Move ===\n");
        Game game = new Game(player1, player2, 3);

        playMove(game, 0, 0);  // Alice X
        playMove(game, 1, 0);  // Bob O
        playMove(game, 1, 1);  // Alice X
        playMove(game, 2, 0);  // Bob O
        playMove(game, 2, 2);  // Alice X - Alice wins!

        assertTrue(game.isGameOver(), "Game should be over");

        game.undoMove();
        
        assertFalse(game.isGameOver(), "Game should be ongoing after undo");
        assertNull(game.getWinner(), "Winner should be null after undo");
        
        game.displayGameState();
        System.out.println("Test passed!\n");
    }

    @Test
    public void testDrawGame() {
        System.out.println("=== Test: Draw Game ===\n");
        Game game = new Game(player1, player2, 3);

        // Create a draw scenario:
        // X O X
        // X O X
        // O X O
        playMove(game, 0, 0);  // Alice X
        playMove(game, 0, 1);  // Bob O
        playMove(game, 0, 2);  // Alice X
        playMove(game, 1, 1);  // Bob O
        playMove(game, 1, 0);  // Alice X
        playMove(game, 2, 0);  // Bob O
        playMove(game, 1, 2);  // Alice X
        playMove(game, 2, 2);  // Bob O
        playMove(game, 2, 1);  // Alice X

        game.displayGameState();
        
        assertTrue(game.isGameOver(), "Game should be over");
        assertNull(game.getWinner(), "No winner in a draw");
        
        System.out.println("Test passed!\n");
    }

    @Test
    public void testInvalidMove() {
        System.out.println("=== Test: Invalid Move (Cell Already Occupied) ===\n");
        Game game = new Game(player1, player2, 3);

        playMove(game, 0, 0);  // Alice X
        
        boolean secondAttempt = game.makeMove(0, 0);  // Try to place again
        
        assertFalse(secondAttempt, "Move should fail when cell is occupied");
        
        System.out.println("Test passed!\n");
    }

    @Test
    public void testGameStateAfterGameOver() {
        System.out.println("=== Test: Game Over Exception ===\n");
        Game game = new Game(player1, player2, 3);

        playMove(game, 0, 0);  // Alice X
        playMove(game, 1, 0);  // Bob O
        playMove(game, 1, 1);  // Alice X
        playMove(game, 2, 0);  // Bob O
        playMove(game, 2, 2);  // Alice X - Alice wins!

        assertTrue(game.isGameOver(), "Game should be over");
        
        assertThrows(IllegalStateException.class, () -> {
            game.makeMove(0, 2);  // Try to play after game over
        }, "Should throw exception when trying to play after game over");
        
        System.out.println("Test passed!\n");
    }

    private static void playMove(Game game, int row, int col) {
        if (game.isGameOver()) {
            System.out.println("Game is already over. Cannot make move at (" + row + ", " + col + ")");
            return;
        }
        
        boolean success = game.makeMove(row, col);
        if (success) {
            System.out.println("Move completed at (" + row + ", " + col + ")");
            System.out.println("Current Player: " + game.getCurrentPlayer());
        } else {
            System.out.println("Invalid move at (" + row + ", " + col + ")");
        }
    }
}
