package questions.lld.Player;

/**
 * Represents a player in any board game.
 */
public class Player {
    private final int id;
    private final String name;
    private final String symbol;

    public Player(int id, String name, String symbol) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalArgumentException("Player symbol cannot be null or empty");
        }
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSymbol() { return symbol; }

    @Override
    public String toString() {
        return name + " (" + symbol + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player other)) return false;
        return id == other.id;
    }

    @Override
    public int hashCode() { return Integer.hashCode(id); }
}
