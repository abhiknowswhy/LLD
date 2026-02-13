package questions.lld.Game;

import questions.lld.Board.ConnectFourBoard;
import questions.lld.Game.Command.PlaceMoveCommand;
import questions.lld.Player.Player;

import java.util.List;

/**
 * Connect Four game — fixed 6x7 board, 2 players, gravity-based drops, 4-in-a-row to win.
 * For makeMove, only col matters (row is ignored; piece drops to lowest empty row).
 */
public class ConnectFourGame extends Game {

    public ConnectFourGame(Player player1, Player player2) {
        super(new ConnectFourBoard(), List.of(player1, player2));
    }

    /**
     * Drop a piece into the specified column.
     * The row parameter is ignored (gravity determines it).
     */
    @Override
    public boolean makeMove(int ignoredRow, int col) {
        if (status != GameStatus.ONGOING) {
            throw new IllegalStateException("Game is already over");
        }

        ConnectFourBoard cfBoard = (ConnectFourBoard) board;
        Player current = getCurrentPlayer();

        int actualRow = cfBoard.dropPiece(col, current.getSymbol());
        if (actualRow == -1) return false; // column full

        // Record the command for undo
        PlaceMoveCommand cmd = new PlaceMoveCommand(board, actualRow, col, current) {
            @Override
            public void execute() {
                // Already executed via dropPiece
            }
        };
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
