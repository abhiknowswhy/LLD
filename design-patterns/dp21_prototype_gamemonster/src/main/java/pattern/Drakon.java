package pattern;

public class Drakon extends Monster {
    private int numHeads;
    private boolean canBreatheFire;

    public Drakon(String name, int numHeads, boolean canBreatheFire) {
        super(name, MonsterType.MYTHICAL, true, 600);
        this.numHeads = numHeads;
        this.canBreatheFire = canBreatheFire;
    }

    @Override
    public void specialAbility() {
        System.out.println(this.name + " spits poison from " + numHeads + " heads!");
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" I have %d heads. %s", 
            numHeads,
            canBreatheFire ? "I can breathe fire!" : "");
    }
}