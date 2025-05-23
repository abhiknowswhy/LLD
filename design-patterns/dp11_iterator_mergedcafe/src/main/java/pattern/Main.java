package pattern;

import pattern.Menus.Menu;
import pattern.Menus.Cafe.CafeMenu;
import pattern.Menus.Diner.DinerMenu;
import pattern.Menus.PancakeHouse.PancakeHouseMenu;

import java.util.ArrayList;

public class Main {
	public static void main(String args[]) {

		PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
		DinerMenu dinerMenu = new DinerMenu();
		CafeMenu cafeMenu = new CafeMenu();
		
		ArrayList<Menu> menus = new ArrayList<Menu>();
		menus.add(pancakeHouseMenu);
		menus.add(dinerMenu);
		menus.add(cafeMenu);

		Waitress waitress = new Waitress(menus);
		waitress.printMenu();

		
	}
}
