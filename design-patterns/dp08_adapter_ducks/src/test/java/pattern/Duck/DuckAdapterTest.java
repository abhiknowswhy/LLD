package pattern.Duck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class DuckAdapterTest {

	Duck duck;
	DuckAdapter adapter;

	@BeforeEach
	void setUp() {
		duck = mock(Duck.class);
		adapter = new DuckAdapter(duck);
	}

	@Test
	void testGobbleCallsDuckQuack() {
		adapter.gobble();
		verify(duck, times(1)).quack();
	}

	@Test
	void testFlyCallsDuckFlyAtLeastOnce() {
		// Since fly() is random, call it many times and check at least one call
		for (int i = 0; i < 100; i++) {
			adapter.fly();
		}
		verify(duck, atLeastOnce()).fly();
	}
}

