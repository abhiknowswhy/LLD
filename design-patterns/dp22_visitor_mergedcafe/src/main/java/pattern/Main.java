package pattern;

import pattern.Menu.Menu;
import pattern.Menu.MenuComponent;
import pattern.Menu.impl.Cafe.CafeMenuCreator;
import pattern.Menu.impl.Diner.DinerMenuCreator;
import pattern.Menu.impl.PancakeHouse.PancakeHouseMenuCreator;

public class Main {
    public static void main(String args[]) {
        // Create top level menu
        MenuComponent allMenus = new Menu("ALL MENUS", "All menus combined");
        
        // Add submenus using creators
        allMenus.add(PancakeHouseMenuCreator.createMenu());
        allMenus.add(DinerMenuCreator.createMenu());
        allMenus.add(CafeMenuCreator.createMenu());

        // Create waitress and print menu information
        Waitress waitress = new Waitress(allMenus);
        
        // Print regular menu
        System.out.println("\nENTIRE MENU\n----");
        waitress.printMenu();
        
        // Print vegetarian menu
        waitress.printVegetarianMenu();
        
        // Print menu statistics using visitors
        waitress.printMenuStatistics();
    }
}