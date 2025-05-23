package pattern.CaffeineBeverages;

import pattern.CaffeineBeverage;

public class Tea extends CaffeineBeverage {
	protected void brew() {
		System.out.println("Steeping the tea");
	}
	protected void addCondiments() {
		System.out.println("Adding Lemon");
	}
}
