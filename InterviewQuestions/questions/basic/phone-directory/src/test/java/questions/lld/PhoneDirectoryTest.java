package questions.lld;

import questions.lld.PhoneDirectory.Contact;
import questions.lld.PhoneDirectory.PhoneDirectory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneDirectoryTest {

    private PhoneDirectory directory;

    @BeforeEach
    void setUp() {
        directory = new PhoneDirectory();
        directory.addContact(new Contact("Alice Johnson", "555-0101", "alice@email.com"));
        directory.addContact(new Contact("Alice Smith", "555-0102"));
        directory.addContact(new Contact("Bob Brown", "555-0201"));
    }

    @Test
    void testAddAndFind() {
        Contact found = directory.findContact("Alice Johnson");
        assertNotNull(found);
        assertEquals("555-0101", found.getPhoneNumber());
        assertEquals("alice@email.com", found.getEmail());
    }

    @Test
    void testFindNonExistent() {
        assertNull(directory.findContact("Charlie"));
    }

    @Test
    void testReversePhoneLookup() {
        Contact found = directory.findByPhoneNumber("555-0201");
        assertNotNull(found);
        assertEquals("Bob Brown", found.getName());
    }

    @Test
    void testSearchByPrefix() {
        List<Contact> results = directory.searchByPrefix("Alice");
        assertEquals(2, results.size());
    }

    @Test
    void testDeleteContact() {
        assertTrue(directory.deleteContact("Alice Smith"));
        assertNull(directory.findContact("Alice Smith"));
        assertEquals(2, directory.size());
        // Sibling should still exist
        assertNotNull(directory.findContact("Alice Johnson"));
    }

    @Test
    void testDeleteNonExistent() {
        assertFalse(directory.deleteContact("Nobody"));
        assertEquals(3, directory.size());
    }

    @Test
    void testUpdateContact() {
        // Adding with same name updates the contact
        directory.addContact(new Contact("Alice Johnson", "555-9999", "newalice@email.com"));
        Contact updated = directory.findContact("Alice Johnson");
        assertEquals("555-9999", updated.getPhoneNumber());
        assertEquals(3, directory.size()); // count shouldn't change
    }

    @Test
    void testReverseIndexUpdatedOnUpdate() {
        directory.addContact(new Contact("Alice Johnson", "555-9999"));
        assertNull(directory.findByPhoneNumber("555-0101")); // old number removed
        assertNotNull(directory.findByPhoneNumber("555-9999"));
    }

    @Test
    void testAllContactsSorted() {
        List<Contact> all = directory.allContacts();
        assertEquals(3, all.size());
        assertEquals("Alice Johnson", all.get(0).getName());
        assertEquals("Alice Smith", all.get(1).getName());
        assertEquals("Bob Brown", all.get(2).getName());
    }

    @Test
    void testEmptyDirectory() {
        PhoneDirectory empty = new PhoneDirectory();
        assertTrue(empty.isEmpty());
        assertEquals(0, empty.size());
        assertNull(empty.findContact("x"));
        assertTrue(empty.searchByPrefix("a").isEmpty());
    }

    @Test
    void testCaseInsensitiveSearch() {
        List<Contact> results = directory.searchByPrefix("ALICE");
        assertEquals(2, results.size());
    }
}
