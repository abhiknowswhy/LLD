package pattern.Components;

public class Roof {
	public String name;
	String material;
	
	public Roof(String material) {
		this.name = "Roof made out of " + material;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString() {
		return this.name;
	}
}

 
 
