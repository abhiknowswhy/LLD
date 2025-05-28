package pattern;

public class Frankenstein extends Monster {
    private int bodyPartCount;
    private boolean electricPowered;

    public Frankenstein(String name, int bodyPartCount, boolean electricPowered) {
        super(name, MonsterType.ARTIFICIAL, false, 400); // Frankenstein is not inherently hostile
        this.bodyPartCount = bodyPartCount;
        this.electricPowered = electricPowered;
    }

    @Override
    public void specialAbility() {
        if (electricPowered) {
            System.out.println(this.name + " channels electricity through " + bodyPartCount + " body parts!");
        } else {
            System.out.println(this.name + " struggles with existential crisis!");
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" Made of %d body parts. %s", 
            bodyPartCount,
            electricPowered ? "Powered by electricity!" : "Currently unpowered.");
    }
}
