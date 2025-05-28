package pattern;

public class Godzilla extends Monster {
    private boolean atomicBreath;
    private int regenerationRate;

    public Godzilla(String name, boolean atomicBreath, int regenerationRate) {
        super(name, MonsterType.MUTANT, true, 1000);
        this.atomicBreath = atomicBreath;
        this.regenerationRate = regenerationRate;
    }

    @Override
    public void specialAbility() {
        if (atomicBreath) {
            System.out.println(this.name + " unleashes devastating atomic breath!");
        } else {
            System.out.println(this.name + " regenerates with power level " + regenerationRate + "!");
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" %s Regeneration rate: %d", 
            atomicBreath ? "I have atomic breath!" : "",
            regenerationRate);
    }
}
