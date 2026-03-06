package questions.lld.SnakeAndLadder;

import java.util.*;

public class Game {
    private final Board board;
    private final Dice dice;
    private final List<Player> players;
    private int currentPlayerIndex;
    private Player winner;
    private final List<String> moveLog;

    public Game(Board board, int numberOfDice, List<String> playerNames) {
        this.board = board;
        this.dice = new Dice(numberOfDice);
        this.players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
        this.currentPlayerIndex = 0;
        this.moveLog = new ArrayList<>();
    }

    public boolean isGameOver() {
        return winner != null;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Plays one turn for the current player using the dice.
     */
    public String playTurn() {
        return playTurn(dice.roll());
    }

    /**
     * Plays one turn with a fixed dice value (for testing).
     */
    public String playTurn(int diceValue) {
        if (isGameOver()) return "Game is already over. Winner: " + winner.getName();

        Player player = getCurrentPlayer();
        int oldPos = player.getPosition();
        int newPos = oldPos + diceValue;

        StringBuilder log = new StringBuilder();
        log.append(player.getName()).append(" rolled ").append(diceValue);

        if (newPos > board.getSize()) {
            // Can't move beyond the board
            log.append(" — stays at ").append(oldPos).append(" (would exceed board)");
        } else if (newPos == board.getSize()) {
            player.setPosition(newPos);
            winner = player;
            log.append(" — moves to ").append(newPos).append(" — WINS!");
        } else {
            int finalPos = board.getFinalPosition(newPos);
            player.setPosition(finalPos);
            log.append(" — moves from ").append(oldPos).append(" to ").append(newPos);
            if (finalPos != newPos) {
                BoardEntity entity = board.getEntityAt(newPos);
                log.append(" — ").append(entity).append(" — goes to ").append(finalPos);
            }
        }

        moveLog.add(log.toString());
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return log.toString();
    }

    public List<Player> getPlayers() { return Collections.unmodifiableList(players); }
    public List<String> getMoveLog() { return Collections.unmodifiableList(moveLog); }
    public Board getBoard() { return board; }
}
