package pattern;

import pattern.CaffeineBeverages.Coffee;
import pattern.CaffeineBeverages.Tea;
import pattern.CaffieneBeveragesWithHook.CoffeeWithHook;
import pattern.CaffieneBeveragesWithHook.TeaWithHook;

public class Main {
	public static void main(String[] args) {
		
		Tea tea = new Tea();
		Coffee coffee = new Coffee();
		
		System.out.println("\nMaking tea...");
		tea.prepareRecipe();
		
		System.out.println("\nMaking coffee...");
		coffee.prepareRecipe();
		
		
		TeaWithHook teaHook = new TeaWithHook();
		CoffeeWithHook coffeeHook = new CoffeeWithHook();
		
		System.out.println("\nMaking tea...");
		teaHook.prepareRecipe();
		
		System.out.println("\nMaking coffee...");
		coffeeHook.prepareRecipe();
	}
}
