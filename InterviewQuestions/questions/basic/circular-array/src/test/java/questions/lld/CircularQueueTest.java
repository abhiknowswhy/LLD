package questions.lld;

import questions.lld.CircularArray.CircularQueue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link CircularQueue} — the bounded deque with variable size.
 */
public class CircularQueueTest {

    private CircularQueue<Integer> q;

    @BeforeEach
    void setUp() {
        q = new CircularQueue<>(5);
    }

    // ── construction ────────────────────────────────────────────────────

    @Test
    void constructorRejectsNonPositiveCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new CircularQueue<>(0));
        assertThrows(IllegalArgumentException.class, () -> new CircularQueue<>(-1));
    }

    @Test
    void newQueueIsEmpty() {
        assertTrue(q.isEmpty());
        assertFalse(q.isFull());
        assertEquals(0, q.size());
        assertEquals(5, q.capacity());
    }

    // ── addLast / addFirst ──────────────────────────────────────────────

    @Test
    void addLastAppendsInOrder() {
        q.addLast(10);
        q.addLast(20);
        q.addLast(30);
        assertEquals(10, q.get(0));
        assertEquals(20, q.get(1));
        assertEquals(30, q.get(2));
        assertEquals(3, q.size());
    }

    @Test
    void addFirstPrependsBeforeHead() {
        q.addLast(10);
        q.addLast(20);
        q.addFirst(5);
        assertEquals(5, q.get(0));
        assertEquals(10, q.get(1));
        assertEquals(20, q.get(2));
    }

    @Test
    void addToFullQueueThrows() {
        for (int i = 0; i < 5; i++) q.addLast(i);
        assertTrue(q.isFull());
        assertThrows(IllegalStateException.class, () -> q.addLast(99));
        assertThrows(IllegalStateException.class, () -> q.addFirst(99));
    }

    // ── removeFirst / removeLast ────────────────────────────────────────

    @Test
    void removeFirstReturnsHead() {
        q.addLast(10);
        q.addLast(20);
        q.addLast(30);
        assertEquals(10, q.removeFirst());
        assertEquals(20, q.get(0));
        assertEquals(2, q.size());
    }

    @Test
    void removeLastReturnsTail() {
        q.addLast(10);
        q.addLast(20);
        q.addLast(30);
        assertEquals(30, q.removeLast());
        assertEquals(2, q.size());
    }

    @Test
    void removeFromEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> q.removeFirst());
        assertThrows(NoSuchElementException.class, () -> q.removeLast());
    }

    // ── get / set ───────────────────────────────────────────────────────

    @Test
    void setUpdatesValue() {
        q.addLast(10);
        q.addLast(20);
        q.set(1, 99);
        assertEquals(99, q.get(1));
    }

    @Test
    void outOfBoundsThrows() {
        q.addLast(1);
        assertThrows(IndexOutOfBoundsException.class, () -> q.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> q.get(-1));
    }

    // ── peek ────────────────────────────────────────────────────────────

    @Test
    void peekReturnsWithoutRemoving() {
        q.addLast(10);
        q.addLast(20);
        q.addLast(30);
        assertEquals(10, q.peekFirst());
        assertEquals(30, q.peekLast());
        assertEquals(3, q.size());   // unchanged
    }

    @Test
    void peekOnEmptyThrows() {
        assertThrows(NoSuchElementException.class, () -> q.peekFirst());
        assertThrows(NoSuchElementException.class, () -> q.peekLast());
    }

    // ── wrap-around ─────────────────────────────────────────────────────

    @Test
    void wrapAroundAfterRemoveAndAdd() {
        for (int i = 0; i < 5; i++) q.addLast(i);   // [0,1,2,3,4]
        q.removeFirst();  // 0
        q.removeFirst();  // 1
        q.addLast(5);
        q.addLast(6);
        // logical: [2, 3, 4, 5, 6]  — physically wraps around
        assertEquals(2, q.get(0));
        assertEquals(6, q.get(4));
    }

    @Test
    void addFirstWrapsHeadBackward() {
        q.addLast(10);
        q.addLast(20);
        q.addFirst(5);
        q.addFirst(1);
        assertEquals(List.of(1, 5, 10, 20), toList(q));
    }

    // ── iterator ────────────────────────────────────────────────────────

    @Test
    void iteratorWalksLogicalOrder() {
        q.addLast(10);
        q.addLast(20);
        q.addLast(30);
        assertEquals(List.of(10, 20, 30), toList(q));
    }

    @Test
    void iteratorOnEmptyQueue() {
        assertEquals(List.of(), toList(q));
    }

    @Test
    void iteratorAfterWrapAround() {
        for (int i = 0; i < 5; i++) q.addLast(i);
        q.removeFirst();
        q.removeFirst();
        q.addLast(5);
        q.addLast(6);
        assertEquals(List.of(2, 3, 4, 5, 6), toList(q));
    }

    // ── toString ────────────────────────────────────────────────────────

    @Test
    void toStringShowsLogicalOrder() {
        q.addLast(1);
        q.addLast(2);
        q.addLast(3);
        assertEquals("[1, 2, 3]", q.toString());
    }

    @Test
    void toStringOnEmpty() {
        assertEquals("[]", q.toString());
    }

    // ── helper ──────────────────────────────────────────────────────────

    private <E> List<E> toList(Iterable<E> iterable) {
        List<E> list = new ArrayList<>();
        for (E e : iterable) list.add(e);
        return list;
    }
}
