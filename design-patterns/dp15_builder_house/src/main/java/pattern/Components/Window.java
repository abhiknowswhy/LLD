package pattern.Components;

public class Window {
	public String name;
	String material;
	
	public Window(String material) {
		this.name = "Window made out of " + material;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString() {
		return this.name;
	}
}

 
 
