package questions.lld.Game;

import java.util.*;

import questions.lld.Board.Board;
import questions.lld.Game.Command.MoveCommand;
import questions.lld.Player.Player;

public class Game {
    private final Board board;
    private final Player[] players;
    private int currentPlayerIndex;
    private GameStatus status;
    private final Stack<MoveCommand> moveHistory;

    public Game(Player player1, Player player2, int boardSize) {
        if (player1.getSymbol() == player2.getSymbol()) {
            throw new IllegalArgumentException("Players must have different symbols");
        }
        this.board = new Board(boardSize);
        this.players = new Player[]{player1, player2};
        this.currentPlayerIndex = 0;
        this.status = GameStatus.ONGOING;
        this.moveHistory = new Stack<>();
    }

    public boolean makeMove(int row, int col) {
        if (status != GameStatus.ONGOING) {
            throw new IllegalStateException("Game is already over. Status: " + status);
        }

        if (!board.isCellEmpty(row, col)) {
            return false;
        }

        Player currentPlayer = getCurrentPlayer();
        MoveCommand command = new MoveCommand(this, row, col, currentPlayer);
        command.execute();
        moveHistory.push(command);

        // Check for win
        if (board.checkWin(currentPlayer.getSymbol())) {
            status = GameStatus.WIN;
            return true;
        }

        // Check for draw
        if (board.isFull()) {
            status = GameStatus.DRAW;
            return true;
        }

        switchPlayer();
        return true;
    }

    public void undoMove() {
        if (moveHistory.isEmpty()) {
            throw new IllegalStateException("No moves to undo");
        }

        MoveCommand command = moveHistory.pop();
        command.undo();

        // If this was the winning move, revert status
        if (status == GameStatus.WIN || status == GameStatus.DRAW) {
            status = GameStatus.ONGOING;
        }

        switchPlayer();
    }

    public void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    public Board getBoard() {
        return board;
    }

    public GameStatus getStatus() {
        return status;
    }

    public boolean isGameOver() {
        return status != GameStatus.ONGOING;
    }

    public Player getWinner() {
        if (status != GameStatus.WIN) {
            return null;
        }
        // The current player (who just made the winning move) is the winner
        return getCurrentPlayer();
    }

    public void reset() {
        board.reset();
        currentPlayerIndex = 0;
        status = GameStatus.ONGOING;
        moveHistory.clear();
    }

    public void displayGameState() {
        board.display();
        System.out.println("Current Player: " + getCurrentPlayer());
        System.out.println("Game Status: " + status);
    }
}
