package pattern.Drone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class DroneAdapterTest {

	Drone drone;
	DroneAdapter adapter;

	@BeforeEach
	void setUp() {
		drone = mock(Drone.class);
		adapter = new DroneAdapter(drone);
	}

	@Test
	void testQuackCallsDroneBeep() {
		adapter.quack();
		verify(drone, times(1)).beep();
	}

	@Test
	void testFlyCallsSpinRotorsAndTakeOff() {
		adapter.fly();
		verify(drone, times(1)).spin_rotors();
		verify(drone, times(1)).take_off();
	}
}