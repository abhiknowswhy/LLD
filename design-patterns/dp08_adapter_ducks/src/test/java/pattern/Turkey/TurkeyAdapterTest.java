package pattern.Turkey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


class TurkeyAdapterTest {

	Turkey turkey;
	TurkeyAdapter adapter;

	@BeforeEach
	void setUp() {
		turkey = mock(Turkey.class);
		adapter = new TurkeyAdapter(turkey);
	}

	@Test
	void testQuackCallsGobble() {
		adapter.quack();
		verify(turkey, times(1)).gobble();
	}

	@Test
	void testFlyCallsTurkeyFlyFiveTimes() {
		adapter.fly();
		verify(turkey, times(5)).fly();
	}
}
