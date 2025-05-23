package pattern.CaffeineBeverages;

import pattern.CaffeineBeverage;

public class Coffee extends CaffeineBeverage {
	protected void brew() {
		System.out.println("Dripping Coffee through filter");
	}
	protected void addCondiments() {
		System.out.println("Adding Sugar and Milk");
	}
}
