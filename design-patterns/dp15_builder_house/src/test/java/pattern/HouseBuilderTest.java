package pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HouseBuilderTest {

	// Simple concrete subclass for testing
	static class TestHouseBuilder extends HouseBuilder {
		boolean wallsAdded = false;
		boolean roofAdded = false;
		boolean windowsAdded = false;

		@Override
		public HouseBuilder addWalls() {
			wallsAdded = true;
			return this;
		}

		@Override
		public HouseBuilder addRoof() {
			roofAdded = true;
			return this;
		}

		@Override
		public HouseBuilder addWindows() {
			windowsAdded = true;
			return this;
		}
	}

	private TestHouseBuilder builder;

	@BeforeEach
	void setUp() {
		builder = spy(new TestHouseBuilder());
	}

	@Test
	void testSetHouseType() throws Exception {
		builder.setHouseType(HouseBuilder.HouseType.CLAY);
		assertEquals(HouseBuilder.HouseType.CLAY, builder.houseType);
	}

	@Test
	void testAddWallsReturnsBuilderAndSetsFlag() {
		HouseBuilder result = builder.addWalls();
		assertTrue(builder.wallsAdded);
		assertSame(builder, result);
	}

	@Test
	void testAddRoofReturnsBuilderAndSetsFlag() {
		HouseBuilder result = builder.addRoof();
		assertTrue(builder.roofAdded);
		assertSame(builder, result);
	}

	@Test
	void testAddWindowsReturnsBuilderAndSetsFlag() {
		HouseBuilder result = builder.addWindows();
		assertTrue(builder.windowsAdded);
		assertSame(builder, result);
	}

	@Test
	void testBuildReturnsHouseInstance() {
		House house = builder.build();
		assertNotNull(house);
		assertSame(builder.house, house);
	}

	@Test
	void testFluentInterface() {
		builder.addWalls().addRoof().addWindows();
		assertTrue(builder.wallsAdded);
		assertTrue(builder.roofAdded);
		assertTrue(builder.windowsAdded);
	}

	@Test
	void testBuildPrintsMessage() {
		// Just ensure build() does not throw and returns the house
		House house = builder.build();
		assertNotNull(house);
	}
}