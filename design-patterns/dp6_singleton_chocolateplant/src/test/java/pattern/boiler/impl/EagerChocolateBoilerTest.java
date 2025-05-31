package pattern.boiler.impl;

import org.junit.jupiter.api.Test;
import pattern.boiler.AbstractChocolateBoilerTest;
import pattern.boiler.ChocolateBoiler;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for EagerChocolateBoiler implementation.
 */
class EagerChocolateBoilerTest extends AbstractChocolateBoilerTest {
    
    @Override
    protected ChocolateBoiler createBoiler() {
        return EagerChocolateBoiler.getInstance();
    }
    
    @Test
    void testSingletonBehavior() {
        EagerChocolateBoiler first = EagerChocolateBoiler.getInstance();
        EagerChocolateBoiler second = EagerChocolateBoiler.getInstance();
        assertSame(first, second, "Multiple getInstance() calls should return the same instance");
    }
    
    @Test
    void testEagerInitialization() {
        // Even before first getInstance() call, instance should exist
        EagerChocolateBoiler first = EagerChocolateBoiler.getInstance();
        assertNotNull(first, "Instance should be created eagerly");
        
        // State should be properly initialized
        assertTrue(first.isEmpty(), "Eager instance should be empty initially");
        assertFalse(first.isBoiled(), "Eager instance should not be boiled initially");
    }
    
    @Test
    void testThreadSafety() throws InterruptedException {
        Thread[] threads = new Thread[10];
        final EagerChocolateBoiler[] boilers = new EagerChocolateBoiler[10];
        
        for (int i = 0; i < threads.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> boilers[index] = EagerChocolateBoiler.getInstance());
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        EagerChocolateBoiler firstInstance = boilers[0];
        for (int i = 1; i < boilers.length; i++) {
            assertSame(firstInstance, boilers[i], 
                    "All threads should get the same instance due to eager initialization");
        }
    }
}
