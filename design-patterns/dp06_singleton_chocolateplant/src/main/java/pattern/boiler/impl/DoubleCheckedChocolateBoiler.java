package pattern.boiler.impl;

import pattern.boiler.AbstractChocolateBoiler;

/**
 * Thread-safe implementation using double-checked locking.
 */
public class DoubleCheckedChocolateBoiler extends AbstractChocolateBoiler {
    private static volatile DoubleCheckedChocolateBoiler uniqueInstance;
    
    private DoubleCheckedChocolateBoiler() {
        super(); // This will handle resetState()
    }
    
    public static DoubleCheckedChocolateBoiler getInstance() {
        if (uniqueInstance == null) {
            synchronized (DoubleCheckedChocolateBoiler.class) {
                if (uniqueInstance == null) {
                    System.out.println("Creating unique instance of Double-Checked Chocolate Boiler");
                    uniqueInstance = new DoubleCheckedChocolateBoiler();
                }
            }
        }
        System.out.println("Returning instance of Double-Checked Chocolate Boiler");
        return uniqueInstance;
    }
}
