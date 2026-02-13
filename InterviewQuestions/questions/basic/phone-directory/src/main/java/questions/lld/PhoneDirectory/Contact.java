package questions.lld.PhoneDirectory;

/**
 * Represents a contact in the phone directory.
 */
public class Contact implements Comparable<Contact> {
    private final String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber) {
        this(name, phoneNumber, null);
    }

    public Contact(String name, String phoneNumber, String email) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public int compareTo(Contact other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" | ").append(phoneNumber);
        if (email != null) {
            sb.append(" | ").append(email);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact other)) return false;
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
