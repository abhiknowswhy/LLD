package questions.lld.RatingDashboard;

/**
 * Represents a user who can submit reviews and vote on helpfulness.
 */
public class User {
    private final String id;
    private final String name;

    public User(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("User id must not be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("User name must not be null or blank");
        }
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "User{id='%s', name='%s'}".formatted(id, name);
    }
}
