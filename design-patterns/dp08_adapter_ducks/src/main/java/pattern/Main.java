package pattern;

import pattern.Drone.Drone;
import pattern.Drone.DroneAdapter;
import pattern.Drone.Quadcopter.Quadcopter;
import pattern.Duck.Duck;
import pattern.Duck.DuckAdapter;
import pattern.Duck.MallardDuck.MallardDuck;
import pattern.Turkey.Turkey;
import pattern.Turkey.TurkeyAdapter;
import pattern.Turkey.WildTurkey.WildTurkey;

public class Main {
	public static void main(String[] args) {
		// Adapter : Turkey to Duck
		Duck duck = new MallardDuck();
		Turkey turkey = new WildTurkey();
		Duck turkeyAdapter = new TurkeyAdapter(turkey);

		System.out.println("The Turkey says...");
		turkey.gobble();
		turkey.fly();

		System.out.println("\nThe Duck says...");
		testDuck(duck);

		System.out.println("\nThe TurkeyAdapter says...");
		testDuck(turkeyAdapter);
		
        // Adapter : Drone to Duck
		System.out.println("\nThe DroneAdapter says...");
		Drone drone = new Quadcopter();
		Duck droneAdapter = new DroneAdapter(drone);
		testDuck(droneAdapter);

		// Adapter : Duck to Turkey 
		MallardDuck duck2 = new MallardDuck();
		Turkey duckAdapter = new DuckAdapter(duck2);

		for(int i=0;i<10;i++) {
			System.out.println("The DuckAdapter says...");
			duckAdapter.gobble();
			duckAdapter.fly();
		}
	}

	static void testDuck(Duck duck) {
		duck.quack();
		duck.fly();
	}
}
