package questions.lld;

import questions.lld.CircularArray.CircularArray;
import questions.lld.CircularArray.CircularQueue;

public class Main {
    public static void main(String[] args) {

        // ================================================================
        //  PART 1 — CircularArray  (pure: fixed size, no add/remove)
        // ================================================================
        System.out.println("========== CircularArray (pure) ==========\n");

        // Every slot is occupied from the start
        CircularArray<Integer> arr = new CircularArray<>(1, 2, 3, 4, 5);
        System.out.println("Initial:       " + arr);           // [1, 2, 3, 4, 5]
        System.out.println("size=" + arr.size());              // always 5

        // Random access
        System.out.println("get(0)=" + arr.get(0) + "  get(4)=" + arr.get(4));

        // Mutate a slot
        arr.set(2, 33);
        System.out.println("After set(2,33): " + arr);        // [1, 2, 33, 4, 5]

        // Rotate right by 2 — no elements move, just the head pointer
        arr.rotate(2);
        System.out.println("rotate(+2):      " + arr);        // [4, 5, 1, 2, 33]

        // Rotate left by 1
        arr.rotate(-1);
        System.out.println("rotate(-1):      " + arr);        // [5, 1, 2, 33, 4]

        // Iterate
        System.out.print("for-each:       ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();

        // KEY POINT: there is NO addFirst, addLast, removeFirst, removeLast.
        // The array is always full — you can only get, set, and rotate.

        // ================================================================
        //  PART 2 — CircularQueue  (bounded deque: size grows / shrinks)
        // ================================================================
        System.out.println("\n========== CircularQueue (bounded deque) ==========\n");

        CircularQueue<Integer> q = new CircularQueue<>(5);
        System.out.println("Empty queue:    " + q);            // []
        System.out.println("size=" + q.size() + "  capacity=" + q.capacity());

        // Add from both ends — size grows
        q.addLast(10);
        q.addLast(20);
        q.addLast(30);
        q.addFirst(5);
        System.out.println("After adds:     " + q);           // [5, 10, 20, 30]
        System.out.println("size=" + q.size());                // 4

        // Random access within occupied range
        System.out.println("get(0)=" + q.get(0) + "  get(3)=" + q.get(3));

        // Remove from both ends — size shrinks
        System.out.println("removeFirst -> " + q.removeFirst()); // 5
        System.out.println("removeLast  -> " + q.removeLast());  // 30
        System.out.println("After removes:  " + q);              // [10, 20]
        System.out.println("size=" + q.size());                   // 2

        // Peek without removing
        System.out.println("peekFirst=" + q.peekFirst() + "  peekLast=" + q.peekLast());

        // Fill to capacity
        q.addLast(30);
        q.addLast(40);
        q.addLast(50);
        System.out.println("Full queue:     " + q);              // [10, 20, 30, 40, 50]
        System.out.println("isFull=" + q.isFull());

        // Iterate
        System.out.print("for-each:       ");
        for (int v : q) System.out.print(v + " ");
        System.out.println();

        // KEY POINT: there is NO rotate.
        // The queue tracks occupied vs empty slots; rotation would
        // map logical indices onto uninitialised physical slots.

        // ================================================================
        //  Summary of differences
        // ================================================================
        System.out.println("\n========== Key Differences ==========");
        System.out.println("CircularArray: always full, supports rotate, no add/remove");
        System.out.println("CircularQueue: variable size (0..capacity), supports add/remove, no rotate");
        System.out.println("Both:          O(1) get/set, O(1) iteration via head pointer + modular indexing");
    }
}