package questions.lld.GameLeaderboard;

import java.util.*;

/**
 * Manages the ranked leaderboard for a single {@link Game}.
 * Internally uses a {@link TreeSet} for O(log n) insertion, removal, and rank queries.
 * A companion {@link HashMap} provides O(1) lookup of a player's current {@link PlayerScore}.
 * All mutating operations are synchronized for thread safety.
 */
public class Leaderboard {
    private final Game game;
    private final TreeSet<PlayerScore> rankings;
    private final Map<String, PlayerScore> playerScoreMap; // playerId -> PlayerScore

    public Leaderboard(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("Leaderboard game must not be null");
        }
        this.game = game;
        this.rankings = new TreeSet<>();
        this.playerScoreMap = new HashMap<>();
    }

    public Game getGame() { return game; }

    /**
     * Submits a {@link Score} for a player. If the player already has a score entry,
     * it is removed from the TreeSet, updated, and re-inserted to maintain sort order.
     * Thread-safe.
     *
     * @throws IllegalArgumentException if the score is null or for a different game
     */
    public synchronized void submitScore(Score score) {
        if (score == null) {
            throw new IllegalArgumentException("Score must not be null");
        }
        if (!score.game().equals(this.game)) {
            throw new IllegalArgumentException("Score game does not match leaderboard game");
        }

        String playerId = score.player().getId();
        PlayerScore ps = playerScoreMap.get(playerId);

        if (ps != null) {
            // Remove before mutation so TreeSet ordering is not corrupted
            rankings.remove(ps);
            ps.addScore(score);
        } else {
            ps = new PlayerScore(score.player(), game);
            ps.addScore(score);
            playerScoreMap.put(playerId, ps);
        }

        rankings.add(ps);
    }

    /**
     * Returns the top K players in this leaderboard, ordered by total points descending.
     *
     * @param k the maximum number of entries to return
     * @return an unmodifiable list of the top K {@link PlayerScore}s
     * @throws IllegalArgumentException if k is not positive
     */
    public synchronized List<PlayerScore> getTopK(int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive");
        }
        List<PlayerScore> result = new ArrayList<>();
        int count = 0;
        for (PlayerScore ps : rankings) {
            if (count >= k) break;
            result.add(ps);
            count++;
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Returns the 1-based rank of the given player, or -1 if the player is not on the board.
     */
    public synchronized int getPlayerRank(String playerId) {
        if (playerId == null || playerId.isBlank()) {
            throw new IllegalArgumentException("Player id must not be null or blank");
        }
        PlayerScore target = playerScoreMap.get(playerId);
        if (target == null) return -1;

        int rank = 1;
        for (PlayerScore ps : rankings) {
            if (ps.equals(target)) return rank;
            rank++;
        }
        return -1; // should not happen if data is consistent
    }

    /**
     * Returns the {@link PlayerScore} for the given player, or null if not found.
     */
    public synchronized PlayerScore getPlayerScore(String playerId) {
        if (playerId == null || playerId.isBlank()) {
            throw new IllegalArgumentException("Player id must not be null or blank");
        }
        return playerScoreMap.get(playerId);
    }

    /**
     * Returns the total number of players on this leaderboard.
     */
    public synchronized int size() { return rankings.size(); }

    @Override
    public String toString() {
        return String.format("Leaderboard{game='%s', players=%d}", game.getName(), rankings.size());
    }
}
