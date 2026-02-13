package questions.lld.Game.Command;

import questions.lld.Board.Board;
import questions.lld.Player.Player;

/**
 * A standard board move — places a symbol at (row, col).
 * Used by TicTacToe and ConnectFour.
 */
public class PlaceMoveCommand implements MoveCommand {
    private final Board board;
    private final int row;
    private final int col;
    private final Player player;

    public PlaceMoveCommand(Board board, int row, int col, Player player) {
        this.board = board;
        this.row = row;
        this.col = col;
        this.player = player;
    }

    @Override
    public void execute() {
        board.placeMove(row, col, player.getSymbol());
    }

    @Override
    public void undo() {
        board.clearCell(row, col);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public Player getPlayer() { return player; }
}
