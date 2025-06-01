package pattern;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class GameStateTest {

    @Test
    void testImmutabilityAndAccessors() {
        LocalDateTime time = LocalDateTime.of(2025,6,2,12,0);
        List<String> inv = new ArrayList<>();
        inv.add("A"); inv.add("B");
        GameState state = new GameState(10, 3, time, inv);
        
        // modifying original list shouldn't affect GameState
        inv.add("C");
        assertEquals(List.of("A","B"), state.getInventory());

        // Accessor list should be defensive copy
        List<String> returned = state.getInventory();
        returned.add("X");
        assertEquals(List.of("A","B"), state.getInventory());

        assertEquals(10, state.getHealth());
        assertEquals(3, state.getLevel());
        assertEquals(time, state.getSaveTime());
    }
}
