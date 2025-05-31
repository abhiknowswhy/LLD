package pattern.boiler.impl;

import pattern.boiler.AbstractChocolateBoiler;

/**
 * Thread-safe implementation using synchronized method.
 */
public class SynchronizedChocolateBoiler extends AbstractChocolateBoiler {
    private static SynchronizedChocolateBoiler uniqueInstance;
    
    private SynchronizedChocolateBoiler() {
        super(); // This will handle resetState()
    }
    
    public static synchronized SynchronizedChocolateBoiler getInstance() {
        if (uniqueInstance == null) {
            System.out.println("Creating unique instance of Synchronized Chocolate Boiler");
            uniqueInstance = new SynchronizedChocolateBoiler();
        }
        System.out.println("Returning instance of Synchronized Chocolate Boiler");
        return uniqueInstance;
    }
}
