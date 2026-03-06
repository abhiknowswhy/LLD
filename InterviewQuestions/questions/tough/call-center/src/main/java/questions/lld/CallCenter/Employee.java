package questions.lld.CallCenter;

/**
 * Represents a call center employee who can handle one call at a time.
 */
public class Employee {

    private final String name;
    private final EmployeeLevel level;
    private Call currentCall;

    public Employee(String name, EmployeeLevel level) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name must not be blank");
        if (level == null) throw new IllegalArgumentException("Level must not be null");
        this.name = name;
        this.level = level;
    }

    public String getName() { return name; }
    public EmployeeLevel getLevel() { return level; }
    public boolean isFree() { return currentCall == null; }

    /** Assigns a call to this employee. */
    void assignCall(Call call) {
        if (!isFree()) throw new IllegalStateException(name + " is already on a call");
        this.currentCall = call;
        call.setHandler(this);
        System.out.println("  [ASSIGN] " + call.getCallerName() + " → " + name + " (" + level + ")");
    }

    /** Completes the current call, freeing this employee. */
    Call completeCall() {
        if (currentCall == null) throw new IllegalStateException(name + " has no active call");
        Call completed = currentCall;
        completed.markCompleted();
        this.currentCall = null;
        System.out.println("  [COMPLETE] " + completed.getCallerName() + " handled by " + name);
        return completed;
    }

    /** Releases the current call for escalation without marking it complete. */
    Call releaseCall() {
        if (currentCall == null) throw new IllegalStateException(name + " has no active call");
        Call released = currentCall;
        this.currentCall = null;
        System.out.println("  [ESCALATE] " + released.getCallerName() + " released by " + name);
        return released;
    }

    @Override
    public String toString() { return name + " (" + level + ", " + (isFree() ? "free" : "busy") + ")"; }
}
