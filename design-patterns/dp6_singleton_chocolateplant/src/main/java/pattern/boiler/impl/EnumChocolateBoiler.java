package pattern.boiler.impl;

import pattern.boiler.ChocolateBoiler;

/**
 * Thread-safe implementation using enum.
 * This is the most concise way to create a thread-safe singleton in Java.
 */
public enum EnumChocolateBoiler implements ChocolateBoiler {
    INSTANCE;
    
    private boolean empty = true; // Initialize field directly
    private boolean boiled = false; // Initialize field directly
    
    EnumChocolateBoiler() {
        // No initialization needed as fields are already initialized
        System.out.println("Creating enum instance of Chocolate Boiler");
    }
    
    public static EnumChocolateBoiler getInstance() {
        System.out.println("Returning instance of Enum Chocolate Boiler");
        return INSTANCE;
    }
    
    @Override
    public void fill() {
        if (isEmpty()) {
            empty = false;
            boiled = false;
            System.out.println("Filled the boiler with a milk/chocolate mixture");
        } else {
            System.out.println("WARNING: Cannot fill - boiler already full!");
        }
    }

    @Override
    public final void drain() { // Make final to ensure consistent behavior
        if (!isEmpty()) {
            if (!isBoiled()) {
                System.out.println("WARNING: Draining without boiling the contents!");
            }
            empty = true;
            boiled = false;
            System.out.println("Drained the milk and chocolate mixture");
        } else {
            System.out.println("WARNING: Cannot drain - boiler already empty!");
        }
    }

    @Override
    public void boil() {
        if (!isEmpty() && !isBoiled()) {
            boiled = true;
            System.out.println("Boiled the contents to a boil");
        } else if (isEmpty()) {
            System.out.println("WARNING: Cannot boil - fill the contents first!");
        } else {
            System.out.println("WARNING: Contents already boiled!");
        }
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    @Override
    public boolean isBoiled() {
        return boiled;
    }
}
