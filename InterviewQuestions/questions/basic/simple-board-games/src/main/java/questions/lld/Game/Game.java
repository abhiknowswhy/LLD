package questions.lld.Game;

import questions.lld.Board.Board;
import questions.lld.Game.Command.MoveCommand;
import questions.lld.Player.Player;

import java.util.List;
import java.util.Stack;

/**
 * Abstract base class for all games.
 * Handles player management, turn switching, undo (via Command pattern), game lifecycle.
 * Concrete games implement makeMove() with game-specific logic.
 */
public abstract class Game {
    protected final Board board;
    protected final List<Player> players;
    protected int currentPlayerIndex;
    protected GameStatus status;
    protected final Stack<MoveCommand> moveHistory;

    protected Game(Board board, List<Player> players) {
        if (players == null || players.size() < 1) {
            throw new IllegalArgumentException("At least one player required");
        }
        this.board = board;
        this.players = players;
        this.currentPlayerIndex = 0;
        this.status = GameStatus.ONGOING;
        this.moveHistory = new Stack<>();
    }

    /**
     * Make a move. The interpretation of row/col depends on the game type.
     * Returns true if the move was successfully made.
     */
    public abstract boolean makeMove(int row, int col);

    /**
     * Undo the last move.
     */
    public void undoMove() {
        if (moveHistory.isEmpty()) {
            throw new IllegalStateException("No moves to undo");
        }
        MoveCommand command = moveHistory.pop();
        command.undo();

        if (status != GameStatus.ONGOING) {
            status = GameStatus.ONGOING;
        }
        switchPlayer();
    }

    public void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Board getBoard() { return board; }
    public GameStatus getStatus() { return status; }
    public boolean isGameOver() { return status != GameStatus.ONGOING; }

    public Player getWinner() {
        if (status != GameStatus.WIN) return null;
        return getCurrentPlayer();
    }

    public void resetGame() {
        board.resetBoard();
        currentPlayerIndex = 0;
        status = GameStatus.ONGOING;
        moveHistory.clear();
    }

    public void displayGameState() {
        board.displayBoard();
        if (status == GameStatus.ONGOING) {
            System.out.println("Current player: " + getCurrentPlayer());
        }
        System.out.println("Status: " + status);
    }
}
