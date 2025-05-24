import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import pattern.Menu.Menu;
import pattern.Menu.MenuComponent;
import pattern.Menu.MenuItem;
import pattern.Waitress;

public class WaitressTest {

    MenuItem vegDinerItem;
    MenuItem nonVegDinerItem;
    MenuItem veganPancakeItem;
    MenuItem dessertCafeItem;
    MenuItem vegCafeItem;
    MenuItem dinerDessertItem;
    Menu dinerMenu;
    Menu dinerDessertMenu;
    Menu pancakeHouseMenu;
    Menu cafeMenu;
    Menu topLevelMenu;
    Waitress waitress;

    ByteArrayOutputStream outContent;
    PrintStream originalOut;

    @BeforeEach
    void setUp() {
        // Create leaf menu items as spies
        vegDinerItem = spy(new MenuItem("Vegetarian BLT", "Fakin’ Bacon with lettuce & tomato on whole wheat", true, 2.99));
        nonVegDinerItem = spy(new MenuItem("BLT", "Bacon with lettuce & tomato on whole wheat", false, 2.99));
        veganPancakeItem = spy(new MenuItem("Vegan Pancakes", "Pancakes made with almond milk", true, 3.99));
        dessertCafeItem = spy(new MenuItem("Cheesecake", "Creamy New York cheesecake", false, 4.99));
        vegCafeItem = spy(new MenuItem("Veggie Wrap", "Grilled veggies in a wrap", true, 5.99));
        dinerDessertItem = spy(new MenuItem("Apple Pie", "Apple pie with a flakey crust, topped with vanilla icecream", true, 1.59));

        // Diner Dessert Menu as spy
        dinerDessertMenu = spy(new Menu("DINER DESSERT MENU", "Dessert of the day"));
        dinerDessertMenu.add(dinerDessertItem);

        // Diner Menu as spy
        dinerMenu = spy(new Menu("DINER MENU", "Lunch"));
        dinerMenu.add(vegDinerItem);
        dinerMenu.add(nonVegDinerItem);
        dinerMenu.add(dinerDessertMenu);

        // Pancake House Menu as spy
        pancakeHouseMenu = spy(new Menu("PANCAKE HOUSE MENU", "Breakfast"));
        pancakeHouseMenu.add(veganPancakeItem);

        // Cafe Menu as spy
        cafeMenu = spy(new Menu("CAFE MENU", "Dinner"));
        cafeMenu.add(dessertCafeItem);
        cafeMenu.add(vegCafeItem);

        // Top Level Menu as spy
        topLevelMenu = spy(new Menu("ALL MENUS", "All menus combined"));
        topLevelMenu.add(dinerMenu);
        topLevelMenu.add(pancakeHouseMenu);
        topLevelMenu.add(cafeMenu);

        waitress = new Waitress(topLevelMenu);

        // Setup output stream for print order verification
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testCreateIterator_IteratesOverAllMenuComponentsInOrder() {
        // Get iterator from topLevelMenu
        Iterator<MenuComponent> iterator = topLevelMenu.createIterator();

        // The expected order is: dinerMenu, vegDinerItem, nonVegDinerItem, dinerDessertMenu, dinerDessertItem,
        // pancakeHouseMenu, veganPancakeItem, cafeMenu, dessertCafeItem, vegCafeItem
        MenuComponent[] expectedOrder = new MenuComponent[] {
            dinerMenu,
            vegDinerItem,
            nonVegDinerItem,
            dinerDessertMenu,
            dinerDessertItem,
            pancakeHouseMenu,
            veganPancakeItem,
            cafeMenu,
            dessertCafeItem,
            vegCafeItem
        };

        for (MenuComponent expected : expectedOrder) {
            assertTrue(iterator.hasNext(), "Iterator should have next element");
            MenuComponent actual = iterator.next();
            assertSame(expected, actual, "Iterator returned unexpected MenuComponent");
        }
        assertFalse(iterator.hasNext(), "Iterator should be exhausted");
    }

    @Test
    void testPrintMenu_PrintsAllMenusAndItemsInOrder() {
        waitress.printMenu();
        // Collect all MenuComponent spies in expected print order
        InOrder inOrder = inOrder(
            topLevelMenu, // root
            dinerMenu,    // child 1
            vegDinerItem, // dinerMenu child 1
            nonVegDinerItem, // dinerMenu child 2
            dinerDessertMenu, // dinerMenu child 3
            dinerDessertItem, // dinerDessertMenu child 1
            pancakeHouseMenu, // child 2
            veganPancakeItem, // pancakeHouseMenu child 1
            cafeMenu,         // child 3
            dessertCafeItem,  // cafeMenu child 1
            vegCafeItem       // cafeMenu child 2
        );

        // Verify print called in order on all MenuComponent objects
        inOrder.verify(topLevelMenu, atLeastOnce()).print();
        inOrder.verify(dinerMenu, atLeastOnce()).print();
        inOrder.verify(vegDinerItem, atLeastOnce()).print();
        inOrder.verify(nonVegDinerItem, atLeastOnce()).print();
        inOrder.verify(dinerDessertMenu, atLeastOnce()).print();
        inOrder.verify(dinerDessertItem, atLeastOnce()).print();
        inOrder.verify(pancakeHouseMenu, atLeastOnce()).print();
        inOrder.verify(veganPancakeItem, atLeastOnce()).print();
        inOrder.verify(cafeMenu, atLeastOnce()).print();
        inOrder.verify(dessertCafeItem, atLeastOnce()).print();
        inOrder.verify(vegCafeItem, atLeastOnce()).print();
    }

    @Test
    void testPrintVegetarianMenu_PrintsOnlyVegetarianItems() {
        waitress.printVegetarianMenu();

        // Verify isVegetarian() called on all items, in order
        InOrder vegOrder = inOrder(
            vegDinerItem, nonVegDinerItem, dinerDessertItem, veganPancakeItem, dessertCafeItem, vegCafeItem
        );
        vegOrder.verify(vegDinerItem, atLeastOnce()).isVegetarian();
        vegOrder.verify(nonVegDinerItem, atLeastOnce()).isVegetarian();
        vegOrder.verify(dinerDessertItem, atLeastOnce()).isVegetarian();
        vegOrder.verify(veganPancakeItem, atLeastOnce()).isVegetarian();
        vegOrder.verify(dessertCafeItem, atLeastOnce()).isVegetarian();
        vegOrder.verify(vegCafeItem, atLeastOnce()).isVegetarian();

        // Verify print() called on vegetarian items, in order
        InOrder printOrder = inOrder(vegDinerItem, veganPancakeItem, vegCafeItem, dinerDessertItem);
        printOrder.verify(vegDinerItem, atLeastOnce()).print();
        printOrder.verify(dinerDessertItem, atLeastOnce()).print();
        printOrder.verify(veganPancakeItem, atLeastOnce()).print();
        printOrder.verify(vegCafeItem, atLeastOnce()).print();
    }
}
