package pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;

import pattern.Subsystems.*;

class HomeTheaterFacadeTest {
    private Amplifier amp;
    private Tuner tuner;
    private StreamingPlayer player;
    private Projector projector;
    private Screen screen;
    private TheaterLights lights;
    private PopcornPopper popper;
    private HomeTheaterFacade facade;

    @BeforeEach
    void setUp() {
        amp = mock(Amplifier.class);
        tuner = mock(Tuner.class);
        player = mock(StreamingPlayer.class);
        projector = mock(Projector.class);
        screen = mock(Screen.class);
        lights = mock(TheaterLights.class);
        popper = mock(PopcornPopper.class);
        facade = new HomeTheaterFacade(amp, tuner, player, projector, screen, lights, popper);
    }

    @Test
    void testWatchMovie() {
        String movie = "Inception";
        facade.watchMovie(movie);
        InOrder inOrder = inOrder(popper, lights, screen, projector, amp, player);
        inOrder.verify(popper).on();
        inOrder.verify(popper).pop();
        inOrder.verify(lights).dim(10);
        inOrder.verify(screen).down();
        inOrder.verify(projector).on();
        inOrder.verify(projector).wideScreenMode();
        inOrder.verify(amp).on();
        inOrder.verify(amp).setStreamingPlayer(player);
        inOrder.verify(amp).setSurroundSound();
        inOrder.verify(amp).setVolume(5);
        inOrder.verify(player).on();
        inOrder.verify(player).play(movie);
    }

    @Test
    void testEndMovie() {
        facade.endMovie();
        InOrder inOrder = inOrder(popper, lights, screen, projector, amp, player);
        inOrder.verify(popper).off();
        inOrder.verify(lights).on();
        inOrder.verify(screen).up();
        inOrder.verify(projector).off();
        inOrder.verify(amp).off();
        inOrder.verify(player).stop();
        inOrder.verify(player).off();
    }

    @Test
    void testListenToRadio() {
        double frequency = 101.1;
        facade.listenToRadio(frequency);
        InOrder inOrder = inOrder(tuner, amp);
        inOrder.verify(tuner).on();
        inOrder.verify(tuner).setFrequency(frequency);
        inOrder.verify(amp).on();
        inOrder.verify(amp).setVolume(5);
        inOrder.verify(amp).setTuner(tuner);
    }

    @Test
    void testEndRadio() {
        facade.endRadio();
        InOrder inOrder = inOrder(tuner, amp);
        inOrder.verify(tuner).off();
        inOrder.verify(amp).off();
    }
}
