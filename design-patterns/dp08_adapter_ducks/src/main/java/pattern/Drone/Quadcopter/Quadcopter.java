package pattern.Drone.Quadcopter;

import pattern.Drone.Drone;

public class Quadcopter implements Drone {
	public void beep() {
		System.out.println("Beep beep beep");
	}
	public void spin_rotors() {
		System.out.println("Rotors are spinning");
	}
	public void take_off() {
		System.out.println("Taking off");
	}
}
