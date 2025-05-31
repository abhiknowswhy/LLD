package pattern;

import pattern.Subsystems.Amplifier;
import pattern.Subsystems.CdPlayer;
import pattern.Subsystems.PopcornPopper;
import pattern.Subsystems.Projector;
import pattern.Subsystems.Screen;
import pattern.Subsystems.StreamingPlayer;
import pattern.Subsystems.TheaterLights;
import pattern.Subsystems.Tuner;

public class Main {
	public static void main(String[] args) {
		Amplifier amp = new Amplifier("Amplifier");
		Tuner tuner = new Tuner("AM/FM Tuner", amp);
		StreamingPlayer player = new StreamingPlayer("Streaming Player", amp);
		CdPlayer cd = new CdPlayer("CD Player", amp);
		Projector projector = new Projector("Projector", player);
		TheaterLights lights = new TheaterLights("Theater Ceiling Lights");
		Screen screen = new Screen("Theater Screen");
		PopcornPopper popper = new PopcornPopper("Popcorn Popper");
		
		HomeTheaterFacade homeTheater = 
		new HomeTheaterFacade(amp, tuner, player, 
		projector, screen, lights, popper);
		
		homeTheater.watchMovie("Raiders of the Lost Ark");
		homeTheater.endMovie();

		// trigger some cd function - CD is not part of the facade
		System.out.println("\nPlaying CD - outside of facade");
		cd.on();
		cd.play("CD");
		cd.eject();
		cd.stop();
		cd.off();
	}
}
