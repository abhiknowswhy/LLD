package questions.lld.Game;

import questions.lld.Board.TicTacToeBoard;
import questions.lld.Game.Command.PlaceMoveCommand;
import questions.lld.Player.Player;

import java.util.List;

/**
 * Tic-Tac-Toe game — variable board size, 2 players, 3-in-a-row to win.
 */
public class TicTacToeGame extends Game {

    public TicTacToeGame(Player player1, Player player2, int boardSize) {
        super(new TicTacToeBoard(boardSize), List.of(player1, player2));
    }

    @Override
    public boolean makeMove(int row, int col) {
        if (status != GameStatus.ONGOING) {
            throw new IllegalStateException("Game is already over");
        }

        Player current = getCurrentPlayer();
        PlaceMoveCommand cmd = new PlaceMoveCommand(board, row, col, current);

        if (!board.validateMove(row, col)) return false;

        cmd.execute();
        moveHistory.push(cmd);

        if (board.checkWin(current.getSymbol())) {
            status = GameStatus.WIN;
            return true;
        }
        if (board.isFull()) {
            status = GameStatus.DRAW;
            return true;
        }

        switchPlayer();
        return true;
    }
}
