package pattern;

import pattern.Devices.CeilingFan.CeilingFan;
import pattern.Devices.CeilingFan.CeilingFanHighCommand;
import pattern.Devices.CeilingFan.CeilingFanMediumCommand;
import pattern.Devices.CeilingFan.CeilingFanOffCommand;
import pattern.Devices.Light.Light;
import pattern.Devices.Light.LightOffCommand;
import pattern.Devices.Light.LightOnCommand;

public class Main {
	
	public static void main(String[] args) {
		RemoteControl remoteControl = new RemoteControl();
		
		Light livingRoomLight = new Light("Living Room");
		
		LightOnCommand livingRoomLightOn = 
		new LightOnCommand(livingRoomLight);
		LightOffCommand livingRoomLightOff = 
		new LightOffCommand(livingRoomLight);
		
		remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
		
		remoteControl.onButtonWasPushed(0);
		remoteControl.offButtonWasPushed(0);
		System.out.println(remoteControl);
		remoteControl.undoButtonWasPushed();
		remoteControl.offButtonWasPushed(0);
		remoteControl.onButtonWasPushed(0);
		System.out.println(remoteControl);
		remoteControl.undoButtonWasPushed();
		
		CeilingFan ceilingFan = new CeilingFan("Living Room");
		
		CeilingFanMediumCommand ceilingFanMedium = 
		new CeilingFanMediumCommand(ceilingFan);
		CeilingFanHighCommand ceilingFanHigh = 
		new CeilingFanHighCommand(ceilingFan);
		CeilingFanOffCommand ceilingFanOff = 
		new CeilingFanOffCommand(ceilingFan);
		
		remoteControl.setCommand(0, ceilingFanMedium, ceilingFanOff);
		remoteControl.setCommand(1, ceilingFanHigh, ceilingFanOff);
		
		remoteControl.onButtonWasPushed(0);
		remoteControl.offButtonWasPushed(0);
		System.out.println(remoteControl);
		remoteControl.undoButtonWasPushed();
		
		remoteControl.onButtonWasPushed(1);
		System.out.println(remoteControl);
		remoteControl.undoButtonWasPushed();
	}
}
