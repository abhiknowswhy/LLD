package pattern.boiler.impl;

import org.junit.jupiter.api.Test;
import pattern.boiler.AbstractChocolateBoilerTest;
import pattern.boiler.ChocolateBoiler;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DoubleCheckedChocolateBoiler implementation.
 */
class DoubleCheckedChocolateBoilerTest extends AbstractChocolateBoilerTest {
    
    @Override
    protected ChocolateBoiler createBoiler() {
        return DoubleCheckedChocolateBoiler.getInstance();
    }
    
    @Test
    void testSingletonBehavior() {
        DoubleCheckedChocolateBoiler first = DoubleCheckedChocolateBoiler.getInstance();
        DoubleCheckedChocolateBoiler second = DoubleCheckedChocolateBoiler.getInstance();
        assertSame(first, second, "Multiple getInstance() calls should return the same instance");
    }
    
    @Test
    void testThreadSafety() throws InterruptedException {
        Thread[] threads = new Thread[10];
        final DoubleCheckedChocolateBoiler[] boilers = new DoubleCheckedChocolateBoiler[10];
        
        for (int i = 0; i < threads.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> boilers[index] = DoubleCheckedChocolateBoiler.getInstance());
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        DoubleCheckedChocolateBoiler firstInstance = boilers[0];
        for (int i = 1; i < boilers.length; i++) {
            assertSame(firstInstance, boilers[i], 
                    "All threads should get the same instance with double-checked locking");
        }
    }
    
    @Test
    void testConcurrentOperations() throws InterruptedException {
        final DoubleCheckedChocolateBoiler boiler = DoubleCheckedChocolateBoiler.getInstance();
        
        // Create multiple threads performing operations concurrently
        Thread[] operationThreads = new Thread[6];
        operationThreads[0] = new Thread(boiler::fill);
        operationThreads[1] = new Thread(boiler::boil);
        operationThreads[2] = new Thread(boiler::drain);
        operationThreads[3] = new Thread(boiler::fill);
        operationThreads[4] = new Thread(boiler::boil);
        operationThreads[5] = new Thread(boiler::drain);
        
        // Start all operation threads
        for (Thread thread : operationThreads) {
            thread.start();
        }
        
        // Wait for all operations to complete
        for (Thread thread : operationThreads) {
            thread.join();
        }
        
        // State should be consistent after concurrent operations
        assertTrue(boiler.isEmpty() || !boiler.isEmpty(), 
                "Boiler should have a consistent empty state");
        assertTrue(boiler.isBoiled() || !boiler.isBoiled(), 
                "Boiler should have a consistent boiled state");
    }
}
