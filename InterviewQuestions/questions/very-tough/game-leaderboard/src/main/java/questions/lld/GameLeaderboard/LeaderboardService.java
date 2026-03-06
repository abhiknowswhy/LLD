package questions.lld.GameLeaderboard;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Orchestrator service for the game leaderboard system.
 * Manages {@link Game}s, {@link Player}s, and per-game {@link Leaderboard}s.
 * Provides thread-safe score submission, top-K queries, rank lookups, and score history retrieval.
 */
public class LeaderboardService {
    private final Map<String, Game> games;
    private final Map<String, Player> players;
    private final Map<String, Leaderboard> leaderboards; // gameId -> Leaderboard

    public LeaderboardService() {
        this.games = new ConcurrentHashMap<>();
        this.players = new ConcurrentHashMap<>();
        this.leaderboards = new ConcurrentHashMap<>();
    }

    /**
     * Registers a {@link Game} and creates its associated {@link Leaderboard}.
     *
     * @throws IllegalArgumentException if the game is null or already registered
     */
    public void registerGame(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("Game must not be null");
        }
        if (games.containsKey(game.getId())) {
            throw new IllegalArgumentException("Game already registered: " + game.getId());
        }
        games.put(game.getId(), game);
        leaderboards.put(game.getId(), new Leaderboard(game));
    }

    /**
     * Registers a {@link Player}.
     *
     * @throws IllegalArgumentException if the player is null or already registered
     */
    public void registerPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player must not be null");
        }
        if (players.containsKey(player.getId())) {
            throw new IllegalArgumentException("Player already registered: " + player.getId());
        }
        players.put(player.getId(), player);
    }

    /**
     * Submits a score for the given player in the given game.
     * Creates a {@link Score} record stamped with the current time.
     *
     * @param playerId the id of the player
     * @param gameId   the id of the game
     * @param points   the points earned
     * @return the created {@link Score}
     * @throws IllegalArgumentException if player or game is not registered, or points are negative
     */
    public Score submitScore(String playerId, String gameId, long points) {
        Player player = players.get(playerId);
        if (player == null) {
            throw new IllegalArgumentException("Player not found: " + playerId);
        }
        Game game = games.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found: " + gameId);
        }

        Score score = new Score(player, game, points, Instant.now());
        Leaderboard board = leaderboards.get(gameId);
        board.submitScore(score);
        return score;
    }

    /**
     * Returns the top K players for the given game.
     *
     * @throws IllegalArgumentException if the game is not registered
     */
    public List<PlayerScore> getTopPlayers(String gameId, int k) {
        Leaderboard board = leaderboards.get(gameId);
        if (board == null) {
            throw new IllegalArgumentException("Game not found: " + gameId);
        }
        return board.getTopK(k);
    }

    /**
     * Returns the 1-based rank of a player in the given game, or -1 if not ranked.
     *
     * @throws IllegalArgumentException if the game is not registered
     */
    public int getPlayerRank(String playerId, String gameId) {
        Leaderboard board = leaderboards.get(gameId);
        if (board == null) {
            throw new IllegalArgumentException("Game not found: " + gameId);
        }
        return board.getPlayerRank(playerId);
    }

    /**
     * Returns the full score history for a player in a given game.
     * Returns an empty list if the player has no scores in that game.
     *
     * @throws IllegalArgumentException if the game is not registered
     */
    public List<Score> getPlayerScoreHistory(String playerId, String gameId) {
        Leaderboard board = leaderboards.get(gameId);
        if (board == null) {
            throw new IllegalArgumentException("Game not found: " + gameId);
        }
        PlayerScore ps = board.getPlayerScore(playerId);
        return ps == null ? Collections.emptyList() : ps.getHistory();
    }

    /**
     * Returns the {@link Leaderboard} for the given game.
     *
     * @throws IllegalArgumentException if the game is not registered
     */
    public Leaderboard getLeaderboard(String gameId) {
        Leaderboard board = leaderboards.get(gameId);
        if (board == null) {
            throw new IllegalArgumentException("Game not found: " + gameId);
        }
        return board;
    }

    public Collection<Game> getAllGames() { return Collections.unmodifiableCollection(games.values()); }
    public Collection<Player> getAllPlayers() { return Collections.unmodifiableCollection(players.values()); }
}
