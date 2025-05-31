package pattern.boiler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Abstract test class providing common test cases for all ChocolateBoiler implementations.
 */
public abstract class AbstractChocolateBoilerTest {
    
    protected abstract ChocolateBoiler createBoiler();
    
    @BeforeEach
    void setUp() {
        ChocolateBoiler boiler = createBoiler();
        // Run through a full cycle to ensure we're in a known state
        boiler.fill();   // Fill it
        boiler.boil();   // Boil it
        boiler.drain();  // Drain it
        // Now the boiler should be empty and boiled state is false
    }
    
    @Test
    void testInitialState() {
        ChocolateBoiler boiler = createBoiler();
        assertTrue(boiler.isEmpty(), "Boiler should be empty initially");
        assertFalse(boiler.isBoiled(), "Contents should not be boiled initially");
    }
    
    @Test
    void testFillEmptyBoiler() {
        ChocolateBoiler boiler = createBoiler();
        boiler.fill();
        assertFalse(boiler.isEmpty(), "Boiler should not be empty after filling");
        assertFalse(boiler.isBoiled(), "Contents should not be boiled after filling");
    }
    
    @Test
    void testBoilFilledContents() {
        ChocolateBoiler boiler = createBoiler();
        boiler.fill();
        boiler.boil();
        assertFalse(boiler.isEmpty(), "Boiler should not be empty after boiling");
        assertTrue(boiler.isBoiled(), "Contents should be boiled");
    }
    
    @Test
    void testDrainBoiledContents() {
        ChocolateBoiler boiler = createBoiler();
        boiler.fill();
        boiler.boil();
        boiler.drain();
        assertTrue(boiler.isEmpty(), "Boiler should be empty after draining");
        assertFalse(boiler.isBoiled(), "Boiled state should be reset to false after draining");
    }
    
    @Test
    void testCannotBoilEmptyContents() {
        ChocolateBoiler boiler = createBoiler();
        boiler.boil();
        assertTrue(boiler.isEmpty(), "Boiler should remain empty");
        assertFalse(boiler.isBoiled(), "Empty contents cannot be boiled");
    }
    
    @Test
    void testCannotFillFullBoiler() {
        ChocolateBoiler boiler = createBoiler();
        boiler.fill();
        boolean wasEmptyBeforeSecondFill = boiler.isEmpty();
        boiler.fill();
        assertFalse(boiler.isEmpty(), "Boiler should remain full");
        assertFalse(wasEmptyBeforeSecondFill, "Boiler should have been full before second fill");
    }
    
    @Test
    void testCannotDrainEmptyBoiler() {
        ChocolateBoiler boiler = createBoiler();
        boiler.drain();
        assertTrue(boiler.isEmpty(), "Boiler should remain empty");
        assertFalse(boiler.isBoiled(), "Empty boiler's boiled state should be false");
    }
    
    @Test
    void testCanDrainUnboiledContents() { // Renamed to reflect actual behavior
        ChocolateBoiler boiler = createBoiler();
        boiler.fill();
        boiler.drain();
        assertTrue(boiler.isEmpty(), "Boiler should be empty after draining unboiled contents");
        assertFalse(boiler.isBoiled(), "Boiled state should be false after draining");
    }
    
    @Test
    void testFullOperationCycle() {
        ChocolateBoiler boiler = createBoiler();
        // Initial state
        assertTrue(boiler.isEmpty());
        assertFalse(boiler.isBoiled());
        
        // Fill
        boiler.fill();
        assertFalse(boiler.isEmpty());
        assertFalse(boiler.isBoiled());
        
        // Boil
        boiler.boil();
        assertFalse(boiler.isEmpty());
        assertTrue(boiler.isBoiled());
        
        // Drain
        boiler.drain();
        assertTrue(boiler.isEmpty());
        assertFalse(boiler.isBoiled()); // Boiled state is reset on drain
        
        // Ready for next cycle
        boiler.fill();
        assertFalse(boiler.isEmpty());
        assertFalse(boiler.isBoiled());
    }
}
