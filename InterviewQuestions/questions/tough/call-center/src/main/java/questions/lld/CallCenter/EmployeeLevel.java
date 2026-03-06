package questions.lld.CallCenter;

/**
 * Represents the hierarchy levels of call center employees.
 * Calls are routed starting from the lowest level and escalated upward.
 */
public enum EmployeeLevel {
    RESPONDENT(0),
    MANAGER(1),
    DIRECTOR(2);

    private final int rank;

    EmployeeLevel(int rank) { this.rank = rank; }

    public int getRank() { return rank; }

    /** Returns the next escalation level, or null if already at highest. */
    public EmployeeLevel nextLevel() {
        return switch (this) {
            case RESPONDENT -> MANAGER;
            case MANAGER -> DIRECTOR;
            case DIRECTOR -> null;
        };
    }
}
