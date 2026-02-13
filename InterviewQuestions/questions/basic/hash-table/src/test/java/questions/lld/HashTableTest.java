package questions.lld;

import questions.lld.HashTable.HashTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class HashTableTest {

    private HashTable<String, Integer> table;

    @BeforeEach
    void setUp() {
        table = new HashTable<>();
    }

    @Test
    void testPutAndGet() {
        table.put("A", 1);
        table.put("B", 2);
        assertEquals(1, table.get("A"));
        assertEquals(2, table.get("B"));
    }

    @Test
    void testUpdateExistingKey() {
        table.put("A", 1);
        Integer old = table.put("A", 10);
        assertEquals(1, old);
        assertEquals(10, table.get("A"));
        assertEquals(1, table.size());
    }

    @Test
    void testRemove() {
        table.put("A", 1);
        table.put("B", 2);
        assertEquals(1, table.remove("A"));
        assertNull(table.get("A"));
        assertEquals(1, table.size());
    }

    @Test
    void testRemoveNonExistent() {
        assertNull(table.remove("Z"));
    }

    @Test
    void testContainsKey() {
        table.put("A", 1);
        assertTrue(table.containsKey("A"));
        assertFalse(table.containsKey("B"));
    }

    @Test
    void testNullKeyThrows() {
        assertThrows(IllegalArgumentException.class, () -> table.put(null, 1));
        assertThrows(IllegalArgumentException.class, () -> table.get(null));
        assertThrows(IllegalArgumentException.class, () -> table.remove(null));
    }

    @Test
    void testResizing() {
        HashTable<Integer, String> small = new HashTable<>(4);
        for (int i = 0; i < 20; i++) {
            small.put(i, "val" + i);
        }
        assertEquals(20, small.size());
        for (int i = 0; i < 20; i++) {
            assertEquals("val" + i, small.get(i));
        }
    }

    @Test
    void testKeysAndValues() {
        table.put("X", 10);
        table.put("Y", 20);
        assertEquals(2, table.keys().size());
        assertTrue(table.keys().contains("X"));
        assertTrue(table.keys().contains("Y"));
        assertTrue(table.values().contains(10));
        assertTrue(table.values().contains(20));
    }

    @Test
    void testEmptyTable() {
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
        assertNull(table.get("anything"));
    }

    @Test
    void testCollisionHandling() {
        // Force collisions by using small capacity
        HashTable<String, Integer> tiny = new HashTable<>(2, 10.0f); // high load factor to prevent resize
        tiny.put("a", 1);
        tiny.put("b", 2);
        tiny.put("c", 3);
        assertEquals(1, tiny.get("a"));
        assertEquals(2, tiny.get("b"));
        assertEquals(3, tiny.get("c"));
    }
}
