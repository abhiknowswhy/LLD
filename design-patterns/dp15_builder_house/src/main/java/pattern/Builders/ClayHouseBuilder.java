package pattern.Builders;

import pattern.House;
import pattern.HouseBuilder;
import pattern.Components.InteriorWall;
import pattern.Components.Roof;
import pattern.Components.Wall;
import pattern.Components.Window;

public class ClayHouseBuilder extends HouseBuilder {
    private int numWalls = 4;
    private int numWindows = 6;
    private String wallMaterial = "Clay bricks";
    private String interiorWallMaterial = "Clay, 1 foot thick";
    private String windowMaterial = "Wooden frame with glass";
    private String roofMaterial = "Clay tiles";

    public ClayHouseBuilder() {
        this.builderName = "Clay House Builder";
        setHouseType(HouseType.CLAY);
    }

    public HouseBuilder addWalls() {
        // Add 3 exterior walls
        for (int i = 0; i < numWalls - 1; i++) {
            house.addWall(new Wall(wallMaterial));
        }
        // Add 1 interior wall
        house.addWall(new InteriorWall(interiorWallMaterial));
        return this;
    }

    public HouseBuilder addWindows() {
        for (int i = 0; i < numWindows; i++) {
            house.addWindow(new Window(windowMaterial));
        }
        return this;
    }

    public HouseBuilder addRoof() {
        house.addRoof(new Roof(roofMaterial));
        return this;
    }

    public House build() {
        System.out.println("Stick everything together with clay mortar");
        return house;
    }
}