package pattern.boiler;

/**
 * Interface defining the operations available on a chocolate boiler.
 */
public interface ChocolateBoiler {
    void fill();
    void drain();
    void boil();
    boolean isEmpty();
    boolean isBoiled();
}
