package questions.lld;

import questions.lld.SearchAutocomplete.AutocompleteSystem;
import questions.lld.SearchAutocomplete.Suggestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AutocompleteTest {

    private AutocompleteSystem system;

    @BeforeEach
    void setUp() {
        system = new AutocompleteSystem(3);
        system.loadHistory(
            new String[]{"i love you", "island", "iroman", "i love leetcode"},
            new int[]{5, 3, 2, 2}
        );
    }

    @Test
    void testSearchByPrefix() {
        List<Suggestion> results = system.search("i");
        assertFalse(results.isEmpty());
        assertTrue(results.size() <= 3);
        // "i love you" should be first (highest frequency)
        assertEquals("i love you", results.get(0).getSentence());
    }

    @Test
    void testSearchNoMatch() {
        List<Suggestion> results = system.search("xyz");
        assertTrue(results.isEmpty());
    }

    @Test
    void testSearchRankedByFrequency() {
        List<Suggestion> results = system.search("i");
        // i love you(5) > island(3) > i love leetcode(2) or iroman(2)
        assertEquals(5, results.get(0).getFrequency());
        assertTrue(results.get(0).getFrequency() >= results.get(1).getFrequency());
    }

    @Test
    void testInteractiveInput() {
        List<Suggestion> r1 = system.input('i');
        assertFalse(r1.isEmpty());

        List<Suggestion> r2 = system.input(' ');
        // Should narrow to "i love you", "i love leetcode"
        for (Suggestion s : r2) {
            assertTrue(s.getSentence().startsWith("i "));
        }
    }

    @Test
    void testInputHashRecordsSearch() {
        system.input('n');
        system.input('e');
        system.input('w');
        system.input('#'); // records "new"

        List<Suggestion> results = system.search("new");
        assertEquals(1, results.size());
        assertEquals("new", results.get(0).getSentence());
        assertEquals(1, results.get(0).getFrequency());
    }

    @Test
    void testFrequencyIncrementsOnRepeatSearch() {
        system.recordSearch("island");
        List<Suggestion> results = system.search("island");
        assertEquals(4, results.get(0).getFrequency()); // was 3, now 4
    }

    @Test
    void testMaxSuggestionsLimit() {
        system.loadHistory(
            new String[]{"alpha", "always", "also", "almond", "all"},
            new int[]{10, 9, 8, 7, 6}
        );
        List<Suggestion> results = system.search("al");
        assertTrue(results.size() <= 3); // max is 3
    }

    @Test
    void testResetInput() {
        system.input('i');
        system.resetInput();
        // After reset, typing 'i' should work fresh
        List<Suggestion> results = system.input('i');
        assertFalse(results.isEmpty());
    }

    @Test
    void testCaseInsensitive() {
        List<Suggestion> lower = system.search("i");
        List<Suggestion> upper = system.search("I");
        assertEquals(lower.size(), upper.size());
    }

    @Test
    void testTiesBrokenAlphabetically() {
        // "i love leetcode" and "iroman" both have frequency 2
        List<Suggestion> results = system.search("i");
        // Check that among equal-frequency items, alphabetical order is maintained
        for (int i = 0; i < results.size() - 1; i++) {
            Suggestion a = results.get(i);
            Suggestion b = results.get(i + 1);
            if (a.getFrequency() == b.getFrequency()) {
                assertTrue(a.getSentence().compareTo(b.getSentence()) <= 0);
            }
        }
    }
}
