package questions.lld;

import questions.lld.CircularArray.CircularArray;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Circular Array Demo ===\n");

        CircularArray<Integer> ring = new CircularArray<>(5);

        // Add elements
        ring.addLast(10);
        ring.addLast(20);
        ring.addLast(30);
        ring.addLast(40);
        ring.addLast(50);
        System.out.println("After adding [10,20,30,40,50]: " + ring);

        // Rotate right by 2
        ring.rotate(2);
        System.out.println("After rotate(2) right:         " + ring);

        // Rotate left by 1
        ring.rotate(-1);
        System.out.println("After rotate(-1) left:         " + ring);

        // Remove from front and back
        System.out.println("\nRemoved first: " + ring.removeFirst());
        System.out.println("Removed last:  " + ring.removeLast());
        System.out.println("After removals: " + ring);

        // Add to front
        ring.addFirst(99);
        System.out.println("After addFirst(99): " + ring);

        // Random access
        System.out.println("\nElement at index 0: " + ring.get(0));
        System.out.println("Element at index 2: " + ring.get(2));

        // Iterate
        System.out.println("\nIterating:");
        for (int val : ring) {
            System.out.println("  -> " + val);
        }

        System.out.println("\nSize: " + ring.size() + ", Capacity: " + ring.capacity());
    }
}