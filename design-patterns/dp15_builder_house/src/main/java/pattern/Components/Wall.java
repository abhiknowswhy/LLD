package pattern.Components;

public class Wall {
	public String name;
	String material;
	
	public Wall(String material) {
		this.name = "Wall made out of " + material;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString() {
		return this.name;
	}
}

 
 
