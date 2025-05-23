package pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pattern.Menus.Menu;
import pattern.Menus.MenuItem;
import pattern.Menus.PancakeHouse.PancakeHouseMenu;
import pattern.Menus.Diner.DinerMenu;
import pattern.Menus.Cafe.CafeMenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;

class WaitressTest {
    private ArrayList<Menu> menus;
    private Waitress waitress;

    @BeforeEach
    void setUp() {
        menus = new ArrayList<>();
        menus.add(new PancakeHouseMenu());
        menus.add(new DinerMenu());
        menus.add(new CafeMenu());
        waitress = new Waitress(menus);
    }

    @Test
    void testPrintMenuDoesNotThrow() {
        // Just ensure no exceptions are thrown
        assertDoesNotThrow(() -> waitress.printMenu());
    }

    @Test
    void testMenusContainExpectedItems() {
        // Check PancakeHouseMenu
        PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
        Iterator<MenuItem> pancakeIterator = pancakeHouseMenu.createIterator();
        assertTrue(pancakeIterator.hasNext());
        MenuItem firstPancake = pancakeIterator.next();
        assertEquals("K&B's Pancake Breakfast", firstPancake.getName());

        // Check DinerMenu
        DinerMenu dinerMenu = new DinerMenu();
        Iterator<MenuItem> dinerIterator = dinerMenu.createIterator();
        assertTrue(dinerIterator.hasNext());
        MenuItem firstDiner = dinerIterator.next();
        assertEquals("Vegetarian BLT", firstDiner.getName());
    }

    @Test
    void testWaitressIteratesOverMenusUsingIterator() {
        // Arrange
        Menu menu1 = mock(Menu.class);
        Menu menu2 = mock(Menu.class);
        Menu menu3 = mock(Menu.class);

        @SuppressWarnings("unchecked")
        Iterator<MenuItem> iterator1 = mock(Iterator.class);
        @SuppressWarnings("unchecked")
        Iterator<MenuItem> iterator2 = mock(Iterator.class);
        @SuppressWarnings("unchecked")
        Iterator<MenuItem> iterator3 = mock(Iterator.class);

        when(menu1.createIterator()).thenReturn(iterator1);
        when(menu2.createIterator()).thenReturn(iterator2);
        when(menu3.createIterator()).thenReturn(iterator3);

        ArrayList<Menu> mockMenus = new ArrayList<>(Arrays.asList(menu1, menu2, menu3));
        Waitress waitress = new Waitress(mockMenus);

        // Act
        waitress.printMenu();

        // Assert
        verify(menu1, times(1)).createIterator();
        verify(menu2, times(1)).createIterator();
        verify(menu3, times(1)).createIterator();
    }

    /**
     * This test checks that CafeMenu contains all expected items.
     * CafeMenu is implemented using a HashMap, which does not guarantee order of iteration.
     * By collecting all item names into a set, we can verify the presence of expected items
     * regardless of their order in the map. This separate test ensures that the menu's
     * contents are correct even if the underlying data structure changes the iteration order.
     */
    @Test
    void testCafeMenuContainsExpectedItems() {
        // Check CafeMenu
        CafeMenu cafeMenu = new CafeMenu();
        Iterator<MenuItem> cafeIterator = cafeMenu.createIterator();
        assertTrue(cafeIterator.hasNext());

        // Collect all item names in a set
        Set<String> cafeItemNames = new HashSet<>();
        while (cafeIterator.hasNext()) {
            MenuItem item = cafeIterator.next();
            cafeItemNames.add(item.getName());
        }
        // Assert all expected items are present, regardless of order
        assertTrue(cafeItemNames.contains("Veggie Burger and Air Fries"));
        assertTrue(cafeItemNames.contains("Soup of the day"));
        assertTrue(cafeItemNames.contains("Burrito"));
    }
}
