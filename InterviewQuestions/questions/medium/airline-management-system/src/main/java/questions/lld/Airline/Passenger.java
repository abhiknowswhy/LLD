package questions.lld.Airline;

public class Passenger {
    private final String passengerId;
    private final String name;
    private final String email;

    public Passenger(String passengerId, String name, String email) {
        this.passengerId = passengerId;
        this.name = name;
        this.email = email;
    }

    public String getPassengerId() { return passengerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("Passenger{%s, %s}", passengerId, name);
    }
}
