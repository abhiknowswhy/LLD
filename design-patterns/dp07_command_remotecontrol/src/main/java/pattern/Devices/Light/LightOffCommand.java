package pattern.Devices.Light;

import pattern.Command;

public class LightOffCommand implements Command {
	Light light;
	int level;
	public LightOffCommand(Light light) {
		this.light = light;
	}
	
	public void execute() {
		level = light.getLevel();
		light.off();
	}
	
	public void undo() {
		light.dim(level);
	}
}
