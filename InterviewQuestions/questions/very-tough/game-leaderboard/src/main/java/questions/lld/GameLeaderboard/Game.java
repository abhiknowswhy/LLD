package questions.lld.GameLeaderboard;

/**
 * Represents a game in the leaderboard system.
 * Each game has a unique id, a display name, and a description of its scoring rules.
 */
public class Game {
    private final String id;
    private final String name;
    private final String scoringRules;

    public Game(String id, String name, String scoringRules) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Game id must not be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Game name must not be null or blank");
        }
        if (scoringRules == null || scoringRules.isBlank()) {
            throw new IllegalArgumentException("Game scoringRules must not be null or blank");
        }
        this.id = id;
        this.name = name;
        this.scoringRules = scoringRules;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getScoringRules() { return scoringRules; }

    @Override
    public String toString() {
        return String.format("Game{id='%s', name='%s', rules='%s'}", id, name, scoringRules);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }
}
