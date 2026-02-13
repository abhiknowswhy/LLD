package questions.lld;

import questions.lld.Game.*;
import questions.lld.Player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class BoardGameTest {

    private Player p1;
    private Player p2;

    @BeforeEach
    void setUp() {
        p1 = new Player(1, "Alice", "X");
        p2 = new Player(2, "Bob", "O");
    }

    // --- TicTacToe Tests ---

    @Test
    void testTicTacToeWinRow() {
        Game game = GameFactory.createGame(GameType.TIC_TAC_TOE, p1, p2);
        game.makeMove(0, 0); // X
        game.makeMove(1, 0); // O
        game.makeMove(0, 1); // X
        game.makeMove(1, 1); // O
        game.makeMove(0, 2); // X — wins top row
        assertEquals(GameStatus.WIN, game.getStatus());
        assertEquals(p1, game.getWinner());
    }

    @Test
    void testTicTacToeDraw() {
        Game game = GameFactory.createGame(GameType.TIC_TAC_TOE, p1, p2);
        // X O X
        // X X O
        // O X O
        game.makeMove(0, 0); // X
        game.makeMove(0, 1); // O
        game.makeMove(0, 2); // X
        game.makeMove(1, 2); // O
        game.makeMove(1, 0); // X
        game.makeMove(2, 0); // O
        game.makeMove(1, 1); // X
        game.makeMove(2, 2); // O
        game.makeMove(2, 1); // X
        assertEquals(GameStatus.DRAW, game.getStatus());
    }

    @Test
    void testTicTacToeInvalidMove() {
        Game game = GameFactory.createGame(GameType.TIC_TAC_TOE, p1, p2);
        game.makeMove(0, 0); // X occupies (0,0)
        assertFalse(game.makeMove(0, 0)); // O tries same cell
    }

    @Test
    void testTicTacToeUndo() {
        Game game = GameFactory.createGame(GameType.TIC_TAC_TOE, p1, p2);
        game.makeMove(0, 0); // X
        game.makeMove(1, 1); // O
        game.makeMove(0, 1); // X

        game.undoMove(); // undo X at (0,1)
        assertTrue(game.getBoard().getCell(0, 1).isEmpty());
        assertEquals(p1, game.getCurrentPlayer()); // should be X's turn again
    }

    @Test
    void testTicTacToeUndoEmptyThrows() {
        Game game = GameFactory.createGame(GameType.TIC_TAC_TOE, p1, p2);
        assertThrows(IllegalStateException.class, game::undoMove);
    }

    @Test
    void testTicTacToeMoveAfterGameOverThrows() {
        Game game = GameFactory.createGame(GameType.TIC_TAC_TOE, p1, p2);
        game.makeMove(0, 0);
        game.makeMove(1, 0);
        game.makeMove(0, 1);
        game.makeMove(1, 1);
        game.makeMove(0, 2); // X wins
        assertThrows(IllegalStateException.class, () -> game.makeMove(2, 2));
    }

    @Test
    void testGameReset() {
        Game game = GameFactory.createGame(GameType.TIC_TAC_TOE, p1, p2);
        game.makeMove(0, 0);
        game.makeMove(1, 1);
        game.resetGame();
        assertEquals(GameStatus.ONGOING, game.getStatus());
        assertTrue(game.getBoard().getCell(0, 0).isEmpty());
        assertEquals(p1, game.getCurrentPlayer());
    }

    // --- ConnectFour Tests ---

    @Test
    void testConnectFourVerticalWin() {
        Player red = new Player(1, "Red", "R");
        Player yellow = new Player(2, "Yellow", "Y");
        Game game = GameFactory.createGame(GameType.CONNECT_FOUR, red, yellow);

        game.makeMove(0, 0); // R
        game.makeMove(0, 1); // Y
        game.makeMove(0, 0); // R
        game.makeMove(0, 1); // Y
        game.makeMove(0, 0); // R
        game.makeMove(0, 1); // Y
        game.makeMove(0, 0); // R — 4 in column 0

        assertEquals(GameStatus.WIN, game.getStatus());
        assertEquals(red, game.getWinner());
    }

    @Test
    void testConnectFourHorizontalWin() {
        Player red = new Player(1, "Red", "R");
        Player yellow = new Player(2, "Yellow", "Y");
        Game game = GameFactory.createGame(GameType.CONNECT_FOUR, red, yellow);

        game.makeMove(0, 0); // R col 0
        game.makeMove(0, 6); // Y col 6
        game.makeMove(0, 1); // R col 1
        game.makeMove(0, 6); // Y col 6
        game.makeMove(0, 2); // R col 2
        game.makeMove(0, 6); // Y col 6
        game.makeMove(0, 3); // R col 3 — 4 in bottom row

        assertEquals(GameStatus.WIN, game.getStatus());
    }

    @Test
    void testConnectFourFullColumn() {
        Player red = new Player(1, "Red", "R");
        Player yellow = new Player(2, "Yellow", "Y");
        Game game = GameFactory.createGame(GameType.CONNECT_FOUR, red, yellow);

        // Fill column 0 (6 rows)
        for (int i = 0; i < 6; i++) {
            game.makeMove(0, 0); // alternating R/Y
            if (game.isGameOver()) return; // might win
            if (i < 5) game.makeMove(0, 1); // put other player elsewhere
        }
    }

    // --- Factory Tests ---

    @Test
    void testFactoryCreatesTicTacToe() {
        Game game = GameFactory.createGame(GameType.TIC_TAC_TOE, p1, p2);
        assertNotNull(game);
        assertEquals(3, game.getBoard().getRows());
    }

    @Test
    void testFactoryCreatesConnectFour() {
        Game game = GameFactory.createGame(GameType.CONNECT_FOUR, p1, p2);
        assertNotNull(game);
        assertEquals(6, game.getBoard().getRows());
        assertEquals(7, game.getBoard().getCols());
    }

    @Test
    void testCustomBoardSize() {
        Game game = GameFactory.createTicTacToe(p1, p2, 5);
        assertEquals(5, game.getBoard().getRows());
    }
}
