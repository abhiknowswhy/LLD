package pattern;

public abstract class Monster implements Cloneable {
    protected String name;
    protected boolean isHostile;
    protected MonsterType type;
    protected int powerLevel;

    public enum MonsterType {
        DRAGON, MYTHICAL, ARTIFICIAL, MUTANT
    }

    public Monster(String name, MonsterType type, boolean isHostile, int powerLevel) {
        this.name = name;
        this.type = type;
        this.isHostile = isHostile;
        this.powerLevel = powerLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public MonsterType getType() {
        return this.type;
    }

    // Special ability that each monster type implements
    public abstract void specialAbility();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Monster copy() throws CloneNotSupportedException {
        return (Monster)this.clone();
    }
    
    @Override
    public String toString() {
        return String.format("I am %s, a %s monster with power level %d. %s", 
            this.name, 
            this.type.toString().toLowerCase(), 
            this.powerLevel,
            this.isHostile ? "Beware, I am hostile!" : "I am friendly.");
    }
}