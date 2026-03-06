package questions.lld.GameLeaderboard;

/**
 * Represents a player who can participate in multiple games
 * and accumulate scores on their respective leaderboards.
 */
public class Player {
    private final String id;
    private final String name;

    public Player(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Player id must not be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player name must not be null or blank");
        }
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return String.format("Player{id='%s', name='%s'}", id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }
}
