package questions.lld.GameLeaderboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregated score for a single player within a single game.
 * Tracks total points and the full history of individual {@link Score} submissions.
 * Implements {@link Comparable} so it can be ordered by total points (descending),
 * breaking ties by player id (ascending) for deterministic ordering.
 */
public class PlayerScore implements Comparable<PlayerScore> {
    private final Player player;
    private final Game game;
    private long totalPoints;
    private final List<Score> history;

    public PlayerScore(Player player, Game game) {
        if (player == null) {
            throw new IllegalArgumentException("PlayerScore player must not be null");
        }
        if (game == null) {
            throw new IllegalArgumentException("PlayerScore game must not be null");
        }
        this.player = player;
        this.game = game;
        this.totalPoints = 0;
        this.history = new ArrayList<>();
    }

    public Player getPlayer() { return player; }
    public Game getGame() { return game; }
    public long getTotalPoints() { return totalPoints; }
    public List<Score> getHistory() { return Collections.unmodifiableList(history); }

    /**
     * Adds a {@link Score} to this player's aggregated total and history.
     *
     * @throws IllegalArgumentException if the score is null
     */
    public void addScore(Score score) {
        if (score == null) {
            throw new IllegalArgumentException("Score must not be null");
        }
        this.totalPoints += score.points();
        this.history.add(score);
    }

    /**
     * Orders by total points descending, then by player id ascending for deterministic ties.
     */
    @Override
    public int compareTo(PlayerScore other) {
        int cmp = Long.compare(other.totalPoints, this.totalPoints); // descending
        if (cmp != 0) return cmp;
        return this.player.getId().compareTo(other.player.getId()); // ascending tie-break
    }

    @Override
    public String toString() {
        return String.format("PlayerScore{player='%s', game='%s', total=%d, submissions=%d}",
                player.getName(), game.getName(), totalPoints, history.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerScore other)) return false;
        return player.equals(other.player) && game.equals(other.game);
    }

    @Override
    public int hashCode() { return player.hashCode() * 31 + game.hashCode(); }
}
