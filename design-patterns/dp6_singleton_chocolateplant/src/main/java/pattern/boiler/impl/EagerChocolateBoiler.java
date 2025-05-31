package pattern.boiler.impl;

import pattern.boiler.AbstractChocolateBoiler;

/**
 * Thread-safe implementation using eager instantiation.
 */
public class EagerChocolateBoiler extends AbstractChocolateBoiler {
    private static final EagerChocolateBoiler uniqueInstance = new EagerChocolateBoiler();
    
    private EagerChocolateBoiler() {
        super(); // This will handle resetState()
        System.out.println("Creating eagerly initialized instance of Chocolate Boiler");
    }
    
    public static EagerChocolateBoiler getInstance() {
        System.out.println("Returning instance of Eager Chocolate Boiler");
        return uniqueInstance;
    }
}
