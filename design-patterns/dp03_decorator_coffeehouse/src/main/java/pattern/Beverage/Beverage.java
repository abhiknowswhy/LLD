package pattern.Beverage;

public abstract class Beverage {
    protected String description = "Unknown Beverage";
    public enum Size { TALL, GRANDE, VENTI };
    public Size size = Size.TALL;

    public String getDescription() {
        return size.toString() + " " + description;
    }

    public void setSize(Size size) {
        this.size = size;
    }
    
    public Size getSize() {
        return this.size;
    }

    public abstract double cost();
}
