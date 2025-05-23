package pattern;

import pattern.Drone.Drone;
import pattern.Drone.DroneAdapter;
import pattern.Drone.Quadcopter.Quadcopter;
import pattern.Duck.Duck;
import pattern.Duck.MallardDuck.MallardDuck;
import pattern.Turkey.Turkey;
import pattern.Turkey.TurkeyAdapter;
import pattern.Turkey.WildTurkey.WildTurkey;

public class DuckTestDrive {
	public static void main(String[] args) {
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
		
		// Challenge
		System.out.println("\nThe DroneAdapter says...");
		Drone drone = new Quadcopter();
		Duck droneAdapter = new DroneAdapter(drone);
		testDuck(droneAdapter);
	}

	static void testDuck(Duck duck) {
		duck.quack();
		duck.fly();
	}
}
