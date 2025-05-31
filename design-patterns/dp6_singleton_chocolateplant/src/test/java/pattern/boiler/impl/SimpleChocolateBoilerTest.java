package pattern.boiler.impl;

import org.junit.jupiter.api.Test;
import pattern.boiler.AbstractChocolateBoilerTest;
import pattern.boiler.ChocolateBoiler;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SimpleChocolateBoiler implementation.
 */
class SimpleChocolateBoilerTest extends AbstractChocolateBoilerTest {
    
    @Override
    protected ChocolateBoiler createBoiler() {
        return SimpleChocolateBoiler.getInstance();
    }
    
    @Test
    void testSingletonBehavior() {
        SimpleChocolateBoiler first = SimpleChocolateBoiler.getInstance();
        SimpleChocolateBoiler second = SimpleChocolateBoiler.getInstance();
        assertSame(first, second, "Multiple getInstance() calls should return the same instance");
    }
    
    @Test
    void testStateIsolation() {
        SimpleChocolateBoiler boiler1 = SimpleChocolateBoiler.getInstance();
        SimpleChocolateBoiler boiler2 = SimpleChocolateBoiler.getInstance();
        
        boiler1.fill();
        assertFalse(boiler2.isEmpty(), "State should be shared between instances");
        
        boiler1.boil();
        assertTrue(boiler2.isBoiled(), "Boiled state should be shared between instances");
        
        boiler1.drain();
        assertTrue(boiler2.isEmpty(), "Empty state should be shared between instances");
    }
}
