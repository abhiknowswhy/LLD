package questions.lld.CircularArray;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic circular array (ring buffer) that supports:
 * - O(1) add to front/back
 * - O(1) remove from front/back
 * - O(1) random access by index
 * - Rotation in either direction
 * - Iterable from current head position
 *
 * Uses a fixed-capacity backing array with head pointer for O(1) rotation.
 */
public class CircularArray<T> implements Iterable<T> {

    private final T[] array;
    private int head;   // logical index 0 maps to array[head]
    private int size;
    private final int capacity;

    @SuppressWarnings("unchecked")
    public CircularArray(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.array = (T[]) new Object[capacity];
        this.head = 0;
        this.size = 0;
    }

    /**
     * Rotate the array by shift positions.
     * Positive shift rotates right, negative rotates left.
     * O(1) — just moves the head pointer.
     */
    public void rotate(int shift) {
        if (size == 0) return;
        head = toPhysicalIndex(-shift); // rotating right means head moves left
    }

    /**
     * Get element at logical index.
     */
    public T get(int logicalIndex) {
        validateIndex(logicalIndex);
        return array[toPhysicalIndex(logicalIndex)];
    }

    /**
     * Set element at logical index.
     */
    public void set(int logicalIndex, T value) {
        validateIndex(logicalIndex);
        array[toPhysicalIndex(logicalIndex)] = value;
    }

    /**
     * Add element to the back (after the last element).
     */
    public void addLast(T value) {
        if (size == capacity) {
            throw new IllegalStateException("Circular array is full");
        }
        array[toPhysicalIndex(size)] = value;
        size++;
    }

    /**
     * Add element to the front (before the first element).
     */
    public void addFirst(T value) {
        if (size == capacity) {
            throw new IllegalStateException("Circular array is full");
        }
        head = mod(head - 1, capacity);
        array[head] = value;
        size++;
    }

    /**
     * Remove and return element from the front.
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Circular array is empty");
        }
        T value = array[head];
        array[head] = null;
        head = mod(head + 1, capacity);
        size--;
        return value;
    }

    /**
     * Remove and return element from the back.
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Circular array is empty");
        }
        int lastPhysical = toPhysicalIndex(size - 1);
        T value = array[lastPhysical];
        array[lastPhysical] = null;
        size--;
        return value;
    }

    /**
     * Peek at front element without removing.
     */
    public T peekFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Circular array is empty");
        }
        return array[head];
    }

    /**
     * Peek at back element without removing.
     */
    public T peekLast() {
        if (size == 0) {
            throw new NoSuchElementException("Circular array is empty");
        }
        return array[toPhysicalIndex(size - 1)];
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    /**
     * Convert logical index to physical index in backing array.
     */
    private int toPhysicalIndex(int logicalIndex) {
        return mod(head + logicalIndex, capacity);
    }

    /**
     * Always-positive modulo (Java's % can return negative).
     */
    private int mod(int a, int m) {
        return ((a % m) + m) % m;
    }

    private void validateIndex(int logicalIndex) {
        if (logicalIndex < 0 || logicalIndex >= size) {
            throw new IndexOutOfBoundsException(
                    "Index " + logicalIndex + " out of bounds for size " + size);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new CircularArrayIterator();
    }

    /**
     * Iterator that walks from head through all elements in logical order.
     */
    private class CircularArrayIterator implements Iterator<T> {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return get(current++);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(get(i));
        }
        sb.append("]");
        return sb.toString();
    }
}
