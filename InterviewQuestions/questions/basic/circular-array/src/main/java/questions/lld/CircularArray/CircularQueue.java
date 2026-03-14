package questions.lld.CircularArray;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Circular queue (bounded deque) backed by a fixed-capacity ring buffer.
 * <p>
 * Unlike {@link CircularArray}, this structure tracks how many slots are
 * <em>occupied</em>.  Elements can be added/removed from both ends, and
 * size grows from 0 up to capacity.
 *
 * <h3>Complexity guarantees (all O(1))</h3>
 * <ul>
 *   <li>addFirst / addLast</li>
 *   <li>removeFirst / removeLast</li>
 *   <li>peekFirst / peekLast</li>
 *   <li>get / set (random access within occupied range)</li>
 * </ul>
 *
 * @param <T> element type
 */
public class CircularQueue<T> implements Iterable<T> {

    private final T[] buffer;
    private final int capacity;
    private int head;   // physical slot for logical index 0
    private int size;   // number of elements currently stored

    // ── construction ────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public CircularQueue(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be positive: " + capacity);

        this.capacity = capacity;
        this.buffer   = (T[]) new Object[capacity];
        this.head     = 0;
        this.size     = 0;
    }

    // ── deque operations ────────────────────────────────────────────────

    public void addFirst(T value) {
        ensureNotFull();
        head = wrap(head - 1);
        buffer[head] = value;
        size++;
    }

    public void addLast(T value) {
        ensureNotFull();
        buffer[wrap(head + size)] = value;
        size++;
    }

    public T removeFirst() {
        ensureNotEmpty();
        T removed = buffer[head];
        buffer[head] = null;           // help GC
        head = wrap(head + 1);
        size--;
        return removed;
    }

    public T removeLast() {
        ensureNotEmpty();
        int tail = wrap(head + size - 1);
        T removed = buffer[tail];
        buffer[tail] = null;
        size--;
        return removed;
    }

    // ── peek ────────────────────────────────────────────────────────────

    public T peekFirst() {
        ensureNotEmpty();
        return buffer[head];
    }

    public T peekLast() {
        ensureNotEmpty();
        return buffer[wrap(head + size - 1)];
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

    public int size()        { return size; }
    public int capacity()    { return capacity; }
    public boolean isEmpty() { return size == 0; }
    public boolean isFull()  { return size == capacity; }

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
        return ((value % capacity) + capacity) % capacity;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(
                    "Index " + index + " out of bounds for size " + size);
    }

    private void ensureNotFull() {
        if (size == capacity)
            throw new IllegalStateException("Circular queue is full");
    }

    private void ensureNotEmpty() {
        if (size == 0)
            throw new NoSuchElementException("Circular queue is empty");
    }
}
