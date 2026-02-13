package questions.lld;

import questions.lld.CircularArray.CircularArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class CircularArrayTest {

    private CircularArray<Integer> ring;

    @BeforeEach
    void setUp() {
        ring = new CircularArray<>(5);
    }

    @Test
    void testAddLastAndGet() {
        ring.addLast(10);
        ring.addLast(20);
        ring.addLast(30);
        assertEquals(10, ring.get(0));
        assertEquals(20, ring.get(1));
        assertEquals(30, ring.get(2));
        assertEquals(3, ring.size());
    }

    @Test
    void testAddFirstAndGet() {
        ring.addLast(10);
        ring.addLast(20);
        ring.addFirst(5);
        assertEquals(5, ring.get(0));
        assertEquals(10, ring.get(1));
        assertEquals(20, ring.get(2));
    }

    @Test
    void testRemoveFirst() {
        ring.addLast(10);
        ring.addLast(20);
        ring.addLast(30);
        assertEquals(10, ring.removeFirst());
        assertEquals(20, ring.get(0));
        assertEquals(2, ring.size());
    }

    @Test
    void testRemoveLast() {
        ring.addLast(10);
        ring.addLast(20);
        ring.addLast(30);
        assertEquals(30, ring.removeLast());
        assertEquals(2, ring.size());
    }

    @Test
    void testRotateRight() {
        ring.addLast(1);
        ring.addLast(2);
        ring.addLast(3);
        ring.addLast(4);
        ring.addLast(5);

        ring.rotate(2);
        // After rotate right by 2: [4, 5, 1, 2, 3]
        assertEquals(4, ring.get(0));
        assertEquals(5, ring.get(1));
        assertEquals(1, ring.get(2));
    }

    @Test
    void testRotateLeft() {
        ring.addLast(1);
        ring.addLast(2);
        ring.addLast(3);
        ring.addLast(4);
        ring.addLast(5);

        ring.rotate(-1);
        // After rotate left by 1: [2, 3, 4, 5, 1]
        assertEquals(2, ring.get(0));
        assertEquals(1, ring.get(4));
    }

    @Test
    void testIterator() {
        ring.addLast(10);
        ring.addLast(20);
        ring.addLast(30);

        List<Integer> result = new ArrayList<>();
        for (int val : ring) {
            result.add(val);
        }
        assertEquals(List.of(10, 20, 30), result);
    }

    @Test
    void testFullCapacity() {
        for (int i = 0; i < 5; i++) {
            ring.addLast(i);
        }
        assertTrue(ring.isFull());
        assertThrows(IllegalStateException.class, () -> ring.addLast(99));
    }

    @Test
    void testEmptyRemoveThrows() {
        assertThrows(NoSuchElementException.class, () -> ring.removeFirst());
        assertThrows(NoSuchElementException.class, () -> ring.removeLast());
    }

    @Test
    void testIndexOutOfBounds() {
        ring.addLast(1);
        assertThrows(IndexOutOfBoundsException.class, () -> ring.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> ring.get(-1));
    }

    @Test
    void testPeek() {
        ring.addLast(10);
        ring.addLast(20);
        ring.addLast(30);
        assertEquals(10, ring.peekFirst());
        assertEquals(30, ring.peekLast());
        assertEquals(3, ring.size()); // peek doesn't remove
    }

    @Test
    void testSet() {
        ring.addLast(10);
        ring.addLast(20);
        ring.set(1, 99);
        assertEquals(99, ring.get(1));
    }

    @Test
    void testWrapAroundAfterRemoveAndAdd() {
        // Fill, remove from front, add to back — forces wrap-around
        for (int i = 0; i < 5; i++) ring.addLast(i);
        ring.removeFirst(); // removes 0
        ring.removeFirst(); // removes 1
        ring.addLast(5);
        ring.addLast(6);
        assertEquals(2, ring.get(0));
        assertEquals(6, ring.get(4));
    }
}
