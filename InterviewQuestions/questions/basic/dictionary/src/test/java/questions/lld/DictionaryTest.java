package questions.lld;

import questions.lld.Dictionary.Dictionary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {

    private Dictionary dict;

    @BeforeEach
    void setUp() {
        dict = new Dictionary();
        dict.addWord("apple", "A fruit");
        dict.addWord("application", "A program");
        dict.addWord("apply", "To request");
        dict.addWord("banana", "A yellow fruit");
    }

    @Test
    void testLookupExistingWord() {
        assertEquals("A fruit", dict.lookup("apple"));
        assertEquals("A program", dict.lookup("application"));
    }

    @Test
    void testLookupNonExistent() {
        assertNull(dict.lookup("cherry"));
        assertNull(dict.lookup("app")); // prefix only, not a word
    }

    @Test
    void testContains() {
        assertTrue(dict.contains("apple"));
        assertFalse(dict.contains("app"));
        assertFalse(dict.contains("xyz"));
    }

    @Test
    void testStartsWith() {
        assertTrue(dict.startsWith("app"));
        assertTrue(dict.startsWith("ban"));
        assertFalse(dict.startsWith("xyz"));
    }

    @Test
    void testWordsWithPrefix() {
        List<String> appWords = dict.wordsWithPrefix("app");
        assertEquals(3, appWords.size());
        assertTrue(appWords.contains("apple"));
        assertTrue(appWords.contains("application"));
        assertTrue(appWords.contains("apply"));
    }

    @Test
    void testDelete() {
        assertTrue(dict.delete("apply"));
        assertFalse(dict.contains("apply"));
        assertTrue(dict.contains("apple")); // sibling should still exist
        assertTrue(dict.contains("application"));
        assertEquals(3, dict.size());
    }

    @Test
    void testDeleteNonExistent() {
        assertFalse(dict.delete("cherry"));
        assertEquals(4, dict.size());
    }

    @Test
    void testDeletePrefix() {
        // deleting "app" which is not a word should return false
        assertFalse(dict.delete("app"));
        assertEquals(4, dict.size());
    }

    @Test
    void testUpdateMeaning() {
        dict.addWord("apple", "Updated meaning");
        assertEquals("Updated meaning", dict.lookup("apple"));
        assertEquals(4, dict.size()); // size shouldn't change
    }

    @Test
    void testCaseInsensitive() {
        assertTrue(dict.contains("APPLE"));
        assertTrue(dict.contains("Apple"));
        assertEquals("A fruit", dict.lookup("APPLE"));
    }

    @Test
    void testEmptyDictionary() {
        Dictionary empty = new Dictionary();
        assertTrue(empty.isEmpty());
        assertEquals(0, empty.size());
        assertNull(empty.lookup("anything"));
        assertTrue(empty.allWords().isEmpty());
    }

    @Test
    void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> dict.addWord(null, "x"));
        assertThrows(IllegalArgumentException.class, () -> dict.addWord("", "x"));
        assertThrows(IllegalArgumentException.class, () -> dict.lookup(null));
    }

    @Test
    void testAllWords() {
        List<String> all = dict.allWords();
        assertEquals(4, all.size());
        assertTrue(all.contains("apple"));
        assertTrue(all.contains("banana"));
    }
}
