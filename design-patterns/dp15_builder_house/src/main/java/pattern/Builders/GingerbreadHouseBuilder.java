package pattern.Builders;

import pattern.House;
import pattern.HouseBuilder;
import pattern.Components.Roof;
import pattern.Components.Wall;
import pattern.Components.Window;

public class GingerbreadHouseBuilder extends HouseBuilder {
	private int numWalls = 4;
	private int numWindows = 4;
	private String windowMaterial = "Sugar";
	private String wallMaterial = "Gingerbread and icing";
	private String roofMaterial = "Twizzlers";
	public GingerbreadHouseBuilder() {
		this.builderName = "Gingerbread House Builder";
		setHouseType(HouseType.GINGERBREAD);
	}
	public HouseBuilder addWalls() {
		// add exterior walls
		for (int i = 0; i < numWalls; i++) {
			System.out.println("Adding wall made out of " + wallMaterial);
			house.addWall(new Wall(wallMaterial));
		}
		return this;
	}
	public HouseBuilder addWindows() {
		for (int i = 0; i < numWindows; i++) {
			System.out.println("Adding window made out of " + windowMaterial);
			house.addWindow(new Window(windowMaterial));
		}
		return this;
	}
	public HouseBuilder addRoof() {
		house.addRoof(new Roof(roofMaterial));
		return this;
	}
	public House build() {
		System.out.println("Stick everything together with icing");
		return house;
	}
}