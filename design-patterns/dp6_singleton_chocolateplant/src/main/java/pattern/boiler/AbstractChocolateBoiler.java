package pattern.boiler;

/**
 * Abstract base class providing common functionality for chocolate boilers.
 */
public abstract class AbstractChocolateBoiler implements ChocolateBoiler {
    private boolean empty = true; // Initialize field directly
    private boolean boiled = false; // Initialize field directly
    
    protected AbstractChocolateBoiler() {
        // No initialization needed as fields are already initialized
    }

    @Override
    public final void fill() { // Make final to ensure consistent behavior
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
    public final void boil() { // Make final to ensure consistent behavior
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
    public final boolean isEmpty() { // Make final to ensure consistent behavior
        return empty;
    }

    @Override
    public final boolean isBoiled() { // Make final to ensure consistent behavior
        return boiled;
    }
}
