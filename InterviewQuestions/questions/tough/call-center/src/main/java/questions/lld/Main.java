package questions.lld;

import questions.lld.CallCenter.*;

/**
 * Demonstrates a Call Center system with multi-level employee routing.
 *
 * Features:
 * - Three employee levels: Respondent, Manager, Director
 * - Automatic call escalation when lower levels are busy
 * - Call queue when all employees are busy
 * - Chain of Responsibility pattern for dispatching
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Call Center Demo ===\n");

        CallCenter center = new CallCenter();

        // Add employees
        center.addEmployee(new Employee("Alice", EmployeeLevel.RESPONDENT));
        center.addEmployee(new Employee("Bob", EmployeeLevel.RESPONDENT));
        center.addEmployee(new Employee("Carol", EmployeeLevel.MANAGER));
        center.addEmployee(new Employee("Dave", EmployeeLevel.DIRECTOR));

        System.out.println("Staff: " + center.getEmployeeCount() + " employees\n");

        // Dispatch calls
        Call call1 = new Call("Customer-1", "Billing question");
        Call call2 = new Call("Customer-2", "Account issue");
        Call call3 = new Call("Customer-3", "Technical problem");
        Call call4 = new Call("Customer-4", "Complaint");
        Call call5 = new Call("Customer-5", "Urgent VIP issue");

        System.out.println("--- Dispatching calls ---");
        center.dispatchCall(call1); // → Alice (respondent)
        center.dispatchCall(call2); // → Bob (respondent)
        center.dispatchCall(call3); // → Carol (manager, respondents busy)
        center.dispatchCall(call4); // → Dave (director, others busy)
        center.dispatchCall(call5); // → queued (all busy)

        System.out.println("\n--- Completing call 1 ---");
        center.completeCall(call1); // frees Alice, dequeues call5

        System.out.println("\n--- Escalating call 2 ---");
        center.escalateCall(call2); // Bob freed, call2 → Carol? (busy) → Dave? (busy) → queued

        System.out.println("\n--- Completing remaining calls ---");
        center.completeCall(call3);
        center.completeCall(call4);
        center.completeCall(call5);
        center.completeCall(call2);

        System.out.println("\n=== Call Center Demo Complete ===");
    }
}