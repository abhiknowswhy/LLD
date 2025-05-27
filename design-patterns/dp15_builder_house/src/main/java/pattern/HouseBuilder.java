package pattern;

public abstract class HouseBuilder {
	protected String builderName;
	protected enum HouseType {
		WOOD, CLAY, GINGERBREAD, STONE 
	}
	HouseType houseType;
	protected House house = new House();
	
	public void setHouseType(HouseType houseType) {
		this.houseType = houseType;
	}
	// Each method in the Builder returns the Builder so we can use the Fluent Interface Pattern
	public abstract HouseBuilder addWalls();
	public abstract HouseBuilder addRoof();
	public abstract HouseBuilder addWindows();

	public House build() {
		System.out.println("Build the house!");
		// Very simple build -- just return the house!
		// We've added all the parts... 
		// In a real build, we'd have to nail and screw everything together of course.
		// And add wiring and so on.
		return house;
	}
}