package pattern.Devices.Light;

import pattern.Command;

public class DimmerLightOffCommand implements Command {
	Light light;
	int prevLevel;
	
	public DimmerLightOffCommand(Light light) {
		this.light = light;
		prevLevel = 100;
	}
	
	public void execute() {
		prevLevel = light.getLevel();
		light.off();
	}
	
	public void undo() {
		light.dim(prevLevel);
	}
}
