package questions.lld.CircularArray;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Pure circular array — a fixed-size collection where every slot is always
 * occupied.  A movable {@code head} pointer defines where logical index 0
 * lives in the physical backing array.
 * <p>
 * There is <em>no</em> concept of empty vs full — you construct it with
 * initial values (or defaults), then {@code get}, {@code set}, and
 * {@code rotate}.  All operations are O(1).
 *
 * @param <T> element type
 */
public class CircularArray<T> implements Iterable<T> {

    private final T[] buffer;
    private final int size;
    private int head;   // physical slot for logical index 0

    // ── construction ────────────────────────────────────────────────────

    /**
     * Create a circular array from the given elements.
     * The array is immediately full — every slot is occupied.
     */
    @SafeVarargs
    public CircularArray(T... elements) {
        if (elements == null || elements.length == 0)
            throw new IllegalArgumentException("Must supply at least one element");

        this.size = elements.length;
        this.buffer = elements.clone();
        this.head = 0;
    }

    /**
     * Create a circular array of the given size, filled with {@code null}.
     * Useful when you plan to {@code set()} values individually.
     */
    @SuppressWarnings("unchecked")
    public CircularArray(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Size must be positive: " + size);

        this.size = size;
        this.buffer = (T[]) new Object[size];
        this.head = 0;
    }

    // ── rotation ────────────────────────────────────────────────────────

    /**
     * Rotate the logical view by {@code shift} positions.
     * Positive = right (last elements become first).
     * Negative = left (first elements become last).
     * O(1) — only the head pointer moves.
     */
    public void rotate(int shift) {
        head = wrap(head - shift);
    }

    // ── positional access ───────────────────────────────────────────────

    public T get(int index) {
        checkIndex(index);
        return buffer[wrap(head + index)];
    }

    public void set(int index, T value) {
        checkIndex(index);
        buffer[wrap(head + index)] = value;
    }

    // ── queries ─────────────────────────────────────────────────────────

    /** Always equals the capacity — every slot is occupied. */
    public int size() { return size; }

    // ── Iterable ────────────────────────────────────────────────────────

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int cursor = 0;

            @Override public boolean hasNext() { return cursor < size; }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                return get(cursor++);
            }
        };
    }

    // ── Object overrides ────────────────────────────────────────────────

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(get(i));
        }
        return sb.append(']').toString();
    }

    // ── internal helpers ────────────────────────────────────────────────

    private int wrap(int value) {
        return ((value % size) + size) % size;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(
                    "Index " + index + " out of bounds for size " + size);
    }
}
