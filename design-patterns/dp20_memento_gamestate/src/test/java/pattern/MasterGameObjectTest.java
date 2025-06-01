package pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

class MasterGameObjectTest {

    private GameState initialState;
    private MasterGameObject game;

    @BeforeEach
    void setUp() {
        initialState = new GameState(
            100,
            1,
            LocalDateTime.of(2025, 6, 2, 10, 0),
            List.of("Key", "Map")
        );
        game = new MasterGameObject(initialState);
    }

    @Test
    void testSaveAndRestoreState() {
        GameMemento<GameState> memento = game.getCurrentState();

        // change the game state
        GameState newState = new GameState(
            50,
            2,
            LocalDateTime.of(2025, 6, 2, 11, 0),
            List.of("Key", "Map", "Potion")
        );
        game.setGameState(newState);
        assertEquals(50, game.getGameState().getHealth());
        assertEquals(2, game.getGameState().getLevel());
        assertEquals(List.of("Key", "Map", "Potion"), game.getGameState().getInventory());

        // restore
        game.restoreState(memento);
        GameState restored = game.getGameState();
        assertEquals(100, restored.getHealth());
        assertEquals(1, restored.getLevel());
        assertEquals(List.of("Key", "Map"), restored.getInventory());
        assertEquals(initialState.getSaveTime(), restored.getSaveTime());
    }

    @Test
    void testRestoreWithNullMementoThrows() {
        assertThrows(IllegalArgumentException.class, () -> game.restoreState(null));
    }
}
