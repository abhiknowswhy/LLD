package questions.lld;

import questions.lld.CircularArray.CircularArray;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link CircularArray} — the pure, always-full circular array.
 */
public class CircularArrayTest {

    // ── construction ────────────────────────────────────────────────────

    @Test
    void varargConstructorStoresElements() {
        CircularArray<Integer> arr = new CircularArray<>(10, 20, 30);
        assertEquals(3, arr.size());
        assertEquals(10, arr.get(0));
        assertEquals(20, arr.get(1));
        assertEquals(30, arr.get(2));
    }

    @Test
    void sizeConstructorCreatesNullFilledArray() {
        CircularArray<String> arr = new CircularArray<>(4);
        assertEquals(4, arr.size());
        assertNull(arr.get(0));
        assertNull(arr.get(3));
    }

    @Test
    void constructorRejectsEmptyAndInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new CircularArray<>(0));
        assertThrows(IllegalArgumentException.class, () -> new CircularArray<>(-1));
        assertThrows(IllegalArgumentException.class, () -> new CircularArray<>(new Integer[0]));
    }

    // ── get / set ───────────────────────────────────────────────────────

    @Test
    void getAndSetByLogicalIndex() {
        CircularArray<Integer> arr = new CircularArray<>(1, 2, 3);
        arr.set(1, 99);
        assertEquals(99, arr.get(1));
    }

    @Test
    void outOfBoundsThrows() {
        CircularArray<Integer> arr = new CircularArray<>(1, 2, 3);
        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> arr.get(-1));
    }

    // ── rotation ────────────────────────────────────────────────────────

    @Test
    void rotateRightShiftsView() {
        CircularArray<Integer> arr = new CircularArray<>(1, 2, 3, 4, 5);
        arr.rotate(2);
        // [4, 5, 1, 2, 3]
        assertEquals(4, arr.get(0));
        assertEquals(5, arr.get(1));
        assertEquals(1, arr.get(2));
        assertEquals(2, arr.get(3));
        assertEquals(3, arr.get(4));
    }

    @Test
    void rotateLeftShiftsView() {
        CircularArray<Integer> arr = new CircularArray<>(1, 2, 3, 4, 5);
        arr.rotate(-1);
        // [2, 3, 4, 5, 1]
        assertEquals(2, arr.get(0));
        assertEquals(1, arr.get(4));
    }

    @Test
    void rotateByZeroIsNoOp() {
        CircularArray<Integer> arr = new CircularArray<>(1, 2, 3);
        arr.rotate(0);
        assertEquals("[1, 2, 3]", arr.toString());
    }

    @Test
    void rotateByFullCycleIsNoOp() {
        CircularArray<Integer> arr = new CircularArray<>(1, 2, 3, 4, 5);
        arr.rotate(5);
        assertEquals("[1, 2, 3, 4, 5]", arr.toString());
    }

    @Test
    void rotateAndSetMutatesCorrectSlot() {
        CircularArray<Integer> arr = new CircularArray<>(1, 2, 3, 4, 5);
        arr.rotate(2);          // [4, 5, 1, 2, 3]
        arr.set(0, 99);         // logical 0 now points to physical slot for '4'
        assertEquals(99, arr.get(0));
        assertEquals(5, arr.get(1));
    }

    // ── iterator ────────────────────────────────────────────────────────

    @Test
    void iteratorWalksLogicalOrder() {
        CircularArray<Integer> arr = new CircularArray<>(10, 20, 30);
        List<Integer> collected = new ArrayList<>();
        for (int v : arr) collected.add(v);
        assertEquals(List.of(10, 20, 30), collected);
    }

    @Test
    void iteratorRespectsRotation() {
        CircularArray<Integer> arr = new CircularArray<>(1, 2, 3, 4, 5);
        arr.rotate(2);
        List<Integer> collected = new ArrayList<>();
        for (int v : arr) collected.add(v);
        assertEquals(List.of(4, 5, 1, 2, 3), collected);
    }

    // ── toString ────────────────────────────────────────────────────────

    @Test
    void toStringShowsLogicalOrder() {
        CircularArray<String> arr = new CircularArray<>("a", "b", "c");
        assertEquals("[a, b, c]", arr.toString());
    }
}
