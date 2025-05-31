package pattern.boiler.impl;

import pattern.boiler.AbstractChocolateBoiler;

/**
 * Simple singleton implementation - not thread safe.
 */
public class SimpleChocolateBoiler extends AbstractChocolateBoiler {
    private static SimpleChocolateBoiler uniqueInstance;
    
    private SimpleChocolateBoiler() {
        super(); // This will handle resetState()
    }
    
    public static SimpleChocolateBoiler getInstance() {
        if (uniqueInstance == null) {
            System.out.println("Creating unique instance of Simple Chocolate Boiler");
            uniqueInstance = new SimpleChocolateBoiler();
        }
        System.out.println("Returning instance of Simple Chocolate Boiler");
        return uniqueInstance;
    }
}
