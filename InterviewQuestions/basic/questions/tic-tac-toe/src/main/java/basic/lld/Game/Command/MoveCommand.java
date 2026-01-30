package basic.lld.Game.Command;

import basic.lld.Game.Game;
import basic.lld.Player.Player;

public class MoveCommand implements Command {
    private final Game game;
    private final int row;
    private final int col;
    private final Player player;

    public MoveCommand(Game game, int row, int col, Player player) {
        this.game = game;
        this.row = row;
        this.col = col;
        this.player = player;
    }

    @Override
    public void execute() {
        game.getBoard().placeSymbol(row, col, player.getSymbol());
    }

    @Override
    public void undo() {
        game.getBoard().clearCell(row, col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Player getPlayer() {
        return player;
    }
}
