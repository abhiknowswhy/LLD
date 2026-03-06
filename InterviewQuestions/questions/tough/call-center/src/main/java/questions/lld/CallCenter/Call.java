package questions.lld.CallCenter;

/**
 * Represents an incoming call to the call center.
 */
public class Call {

    private final String callerName;
    private final String issue;
    private CallStatus status;
    private Employee handler;
    private EmployeeLevel minimumLevel;

    public Call(String callerName, String issue) {
        if (callerName == null || callerName.isBlank()) throw new IllegalArgumentException("Caller name required");
        if (issue == null || issue.isBlank()) throw new IllegalArgumentException("Issue description required");
        this.callerName = callerName;
        this.issue = issue;
        this.status = CallStatus.WAITING;
        this.minimumLevel = EmployeeLevel.RESPONDENT;
    }

    public String getCallerName() { return callerName; }
    public String getIssue() { return issue; }
    public CallStatus getStatus() { return status; }
    public Employee getHandler() { return handler; }
    public EmployeeLevel getMinimumLevel() { return minimumLevel; }

    void setHandler(Employee handler) {
        this.handler = handler;
        this.status = CallStatus.IN_PROGRESS;
    }

    void markCompleted() { this.status = CallStatus.COMPLETED; }

    void escalate() {
        EmployeeLevel next = minimumLevel.nextLevel();
        if (next == null) throw new IllegalStateException("Cannot escalate beyond DIRECTOR");
        this.minimumLevel = next;
        this.handler = null;
        this.status = CallStatus.WAITING;
    }

    @Override
    public String toString() {
        return "Call[" + callerName + ", " + status + ", issue=\"" + issue + "\"]";
    }
}
