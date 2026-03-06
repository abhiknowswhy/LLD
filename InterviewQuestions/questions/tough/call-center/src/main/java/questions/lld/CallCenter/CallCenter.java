package questions.lld.CallCenter;

import java.util.*;

/**
 * Call Center dispatcher that routes incoming calls to available employees.
 *
 * Design:
 * - Chain of Responsibility: calls start at RESPONDENT, escalate to MANAGER, then DIRECTOR
 * - Each level has a queue of available employees
 * - Overflow calls are held in a waiting queue
 * - When an employee finishes, the next queued call is dispatched
 */
public class CallCenter {

    private final Map<EmployeeLevel, List<Employee>> employeesByLevel = new EnumMap<>(EmployeeLevel.class);
    private final Queue<Call> waitingCalls = new LinkedList<>();

    public CallCenter() {
        for (EmployeeLevel level : EmployeeLevel.values()) {
            employeesByLevel.put(level, new ArrayList<>());
        }
    }

    /** Adds an employee to the call center. */
    public void addEmployee(Employee employee) {
        employeesByLevel.get(employee.getLevel()).add(employee);
    }

    /** Returns the total number of employees across all levels. */
    public int getEmployeeCount() {
        return employeesByLevel.values().stream().mapToInt(List::size).sum();
    }

    /**
     * Dispatches a call to the first available employee at or above the call's minimum level.
     * If no one is available, the call is queued.
     */
    public void dispatchCall(Call call) {
        Employee handler = findFreeEmployee(call.getMinimumLevel());
        if (handler != null) {
            handler.assignCall(call);
        } else {
            waitingCalls.add(call);
            System.out.println("  [QUEUE] " + call.getCallerName() + " — all employees busy, call queued");
        }
    }

    /** Marks a call as complete and dispatches the next waiting call if any. */
    public void completeCall(Call call) {
        Employee handler = call.getHandler();
        if (handler != null) {
            handler.completeCall();
            dispatchNextWaiting();
        }
    }

    /**
     * Escalates a call to the next employee level.
     * The current handler releases the call, and it is re-dispatched at a higher level.
     */
    public void escalateCall(Call call) {
        Employee handler = call.getHandler();
        if (handler != null) {
            handler.releaseCall();
        }
        call.escalate();
        dispatchCall(call);
        // Also try to assign a waiting call to the now-free handler
        dispatchNextWaiting();
    }

    // --- Private helpers ---

    private Employee findFreeEmployee(EmployeeLevel minimumLevel) {
        for (EmployeeLevel level : EmployeeLevel.values()) {
            if (level.getRank() < minimumLevel.getRank()) continue;
            for (Employee emp : employeesByLevel.get(level)) {
                if (emp.isFree()) return emp;
            }
        }
        return null;
    }

    private void dispatchNextWaiting() {
        if (waitingCalls.isEmpty()) return;
        Call next = waitingCalls.peek();
        Employee handler = findFreeEmployee(next.getMinimumLevel());
        if (handler != null) {
            waitingCalls.poll();
            handler.assignCall(next);
        }
    }
}
