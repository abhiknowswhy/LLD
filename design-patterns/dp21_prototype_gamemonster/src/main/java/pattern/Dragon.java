package pattern;

public class Dragon extends Monster {
    private boolean hasWings;
    private boolean canBreatheFire;

    public Dragon(String name, boolean hasWings, boolean canBreatheFire) {
        super(name, MonsterType.DRAGON, true, 800);
        this.hasWings = hasWings;
        this.canBreatheFire = canBreatheFire;
    }

    @Override
    public void specialAbility() {
        if (canBreatheFire) {
            System.out.println(this.name + " breathes fire!");
        } else if (hasWings) {
            System.out.println(this.name + " soars through the sky!");
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" %s%s", 
            hasWings ? "I have wings. " : "",
            canBreatheFire ? "I can breathe fire!" : "");
    }
}