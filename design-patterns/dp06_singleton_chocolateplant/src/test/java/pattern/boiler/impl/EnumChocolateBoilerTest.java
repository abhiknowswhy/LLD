package pattern.boiler.impl;

import org.junit.jupiter.api.Test;
import pattern.boiler.AbstractChocolateBoilerTest;
import pattern.boiler.ChocolateBoiler;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for EnumChocolateBoiler implementation.
 */
class EnumChocolateBoilerTest extends AbstractChocolateBoilerTest {
    
    @Override
    protected ChocolateBoiler createBoiler() {
        return EnumChocolateBoiler.getInstance();
    }
    
    @Test
    void testSingletonBehavior() {
        EnumChocolateBoiler first = EnumChocolateBoiler.getInstance();
        EnumChocolateBoiler second = EnumChocolateBoiler.getInstance();
        assertSame(first, second, "Multiple getInstance() calls should return the same instance");
    }
    
    @Test
    void testEnumSingleton() {
        EnumChocolateBoiler first = EnumChocolateBoiler.INSTANCE;
        EnumChocolateBoiler second = EnumChocolateBoiler.getInstance();
        assertSame(first, second, "Direct enum access and getInstance() should return same instance");
    }
    
    @Test
    void testThreadSafety() throws InterruptedException {
        Thread[] threads = new Thread[10];
        final EnumChocolateBoiler[] boilers = new EnumChocolateBoiler[10];
        
        for (int i = 0; i < threads.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> boilers[index] = EnumChocolateBoiler.getInstance());
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
        
        EnumChocolateBoiler firstInstance = boilers[0];
        for (int i = 1; i < boilers.length; i++) {
            assertSame(firstInstance, boilers[i], 
                    "All threads should get the same instance with enum singleton");
        }
    }
    
    @Test
    void testSerializationSafety() {
        EnumChocolateBoiler boiler1 = EnumChocolateBoiler.getInstance();
        boiler1.fill();
        
        // In a real application, you would serialize and deserialize here
        EnumChocolateBoiler boiler2 = EnumChocolateBoiler.getInstance();
        
        assertSame(boiler1, boiler2, "Enum singleton should maintain identity even after serialization");
        assertFalse(boiler2.isEmpty(), "State should be preserved");
    }
}
