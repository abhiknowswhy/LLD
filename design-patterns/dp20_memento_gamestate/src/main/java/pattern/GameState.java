package pattern;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Immutable state object holding various game state properties.
 */
public class GameState {
    private final int health;
    private final int level;
    private final LocalDateTime saveTime;
    private final List<String> inventory;

    public GameState(int health, int level, LocalDateTime saveTime, List<String> inventory) {
        this.health = health;
        this.level = level;
        this.saveTime = saveTime;
        this.inventory = new ArrayList<>(inventory);
    }

    public int getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public LocalDateTime getSaveTime() {
        return saveTime;
    }

    public List<String> getInventory() {
        return new ArrayList<>(inventory);
    }

    @Override
    public String toString() {
        return "GameState{" +
                "health=" + health +
                ", level=" + level +
                ", saveTime=" + saveTime +
                ", inventory=" + inventory +
                '}';
    }
}
