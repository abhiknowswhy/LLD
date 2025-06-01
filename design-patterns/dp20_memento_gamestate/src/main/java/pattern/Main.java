package pattern;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create initial game state with health, level, timestamp, and inventory
        GameState initialState = new GameState(
            100,
            1,
            LocalDateTime.now(),
            List.of("Sword", "Shield")
        );
        MasterGameObject game = new MasterGameObject(initialState);
        System.out.println("Initial game state: " + game.getGameState());

        // Save state
        GameMemento<GameState> savedState = game.getCurrentState();

        // Change state to simulate advancing in the game
        GameState nextState = new GameState(
            80,
            2,
            LocalDateTime.now(),
            List.of("Sword", "Shield", "Potion")
        );
        game.setGameState(nextState);
        System.out.println("Game state after advancing: " + game.getGameState());

        // Restore to previous saved state
        game.restoreState(savedState);
        System.out.println("Game state after restoring: " + game.getGameState());
    }
}