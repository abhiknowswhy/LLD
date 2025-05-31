package pattern.boiler.impl;

import org.junit.jupiter.api.Test;
import pattern.boiler.AbstractChocolateBoilerTest;
import pattern.boiler.ChocolateBoiler;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SynchronizedChocolateBoiler implementation.
 */
class SynchronizedChocolateBoilerTest extends AbstractChocolateBoilerTest {
    
    @Override
    protected ChocolateBoiler createBoiler() {
        return SynchronizedChocolateBoiler.getInstance();
    }
    
    @Test
    void testSingletonBehavior() {
        SynchronizedChocolateBoiler first = SynchronizedChocolateBoiler.getInstance();
        SynchronizedChocolateBoiler second = SynchronizedChocolateBoiler.getInstance();
        assertSame(first, second, "Multiple getInstance() calls should return the same instance");
    }
    
    @Test
    void testThreadSafety() throws InterruptedException {
        // Create multiple threads that try to get the instance simultaneously
        Thread[] threads = new Thread[10];
        final SynchronizedChocolateBoiler[] boilers = new SynchronizedChocolateBoiler[10];
        
        for (int i = 0; i < threads.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> boilers[index] = SynchronizedChocolateBoiler.getInstance());
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        
        // Verify that all threads got the same instance
        SynchronizedChocolateBoiler firstInstance = boilers[0];
        for (int i = 1; i < boilers.length; i++) {
            assertSame(firstInstance, boilers[i], 
                    "All threads should get the same instance");
        }
    }
    
    @Test
    void testStateConsistency() throws InterruptedException {
        final SynchronizedChocolateBoiler boiler = SynchronizedChocolateBoiler.getInstance();
        
        // Create threads that perform operations
        Thread fillThread = new Thread(boiler::fill);
        Thread boilThread = new Thread(boiler::boil);
        Thread drainThread = new Thread(boiler::drain);
        
        // Start threads in order
        fillThread.start();
        fillThread.join();
        boilThread.start();
        boilThread.join();
        drainThread.start();
        drainThread.join();
        
        // Final state should be empty and boiled
        assertTrue(boiler.isEmpty(), "Boiler should be empty after cycle completion");
        assertFalse(boiler.isBoiled(), "Contents should not be marked as boiled");
    }
}
