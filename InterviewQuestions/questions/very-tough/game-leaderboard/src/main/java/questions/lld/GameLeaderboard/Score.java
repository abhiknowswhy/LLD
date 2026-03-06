package questions.lld.GameLeaderboard;

import java.time.Instant;

/**
 * Immutable record representing a single score submission.
 * Binds a {@link Player} and {@link Game} to a point value at a specific timestamp.
 */
public record Score(Player player, Game game, long points, Instant timestamp) {

    /**
     * Compact constructor that validates all fields.
     */
    public Score {
        if (player == null) {
            throw new IllegalArgumentException("Score player must not be null");
        }
        if (game == null) {
            throw new IllegalArgumentException("Score game must not be null");
        }
        if (points < 0) {
            throw new IllegalArgumentException("Score points must not be negative");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Score timestamp must not be null");
        }
    }

    @Override
    public String toString() {
        return String.format("Score{player='%s', game='%s', points=%d, time=%s}",
                player.getName(), game.getName(), points, timestamp);
    }
}
