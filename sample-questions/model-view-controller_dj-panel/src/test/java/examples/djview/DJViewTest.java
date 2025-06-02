package examples.djview;

import org.junit.Test;
import examples.djview.model.*;
import examples.djview.controller.*;

public class DJViewTest {
    @Test
    public void testBeatModelInitialization() {
        BeatModelInterface model = new BeatModel();
        ControllerInterface controller = new BeatController(model);
        
        // Verify initial state
        assert model.getBPM() == 90;
        
        // Test BPM changes
        model.setBPM(100);
        assert model.getBPM() == 100;
        
        // Clean up
        model.off();
    }
}
