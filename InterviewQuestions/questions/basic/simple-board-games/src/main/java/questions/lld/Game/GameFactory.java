package questions.lld.Game;

import questions.lld.Player.Player;

/**
 * Factory pattern — creates the right game based on GameType.
 */
public class GameFactory {

    public static Game createGame(GameType type, Player player1, Player player2) {
        return switch (type) {
            case TIC_TAC_TOE -> new TicTacToeGame(player1, player2, 3);
            case CONNECT_FOUR -> new ConnectFourGame(player1, player2);
        };
    }

    /**
     * Create a TicTacToe game with custom board size.
     */
    public static Game createTicTacToe(Player player1, Player player2, int boardSize) {
        return new TicTacToeGame(player1, player2, boardSize);
    }
}
