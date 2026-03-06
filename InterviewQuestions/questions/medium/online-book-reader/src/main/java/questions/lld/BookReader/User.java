package questions.lld.BookReader;

public class User {
    private final String userId;
    private final String name;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return String.format("User{%s, %s}", userId, name);
    }
}
