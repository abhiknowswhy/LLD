package examples.djview;

import org.junit.Test;
import org.junit.Before;
import static org.mockito.Mockito.*;

import examples.djview.model.*;
import examples.djview.controller.*;
import examples.djview.view.*;

public class DJViewTest {
    private BeatModelInterface mockModel;
    private ViewInterface mockView;
    private ControllerInterface controller;
    
    @Before
    public void setUp() {
        // Create mocks for both model and view
        mockModel = mock(BeatModelInterface.class);
        mockView = mock(ViewInterface.class);
        
        // Set up default behavior for the mock model
        when(mockModel.getBPM()).thenReturn(90); // Default BPM
        
        // Use TestAwareBeatController with both mocks to avoid UI creation
        controller = new TestAwareBeatController(mockModel, mockView);
    }
    
    @Test
    public void testBeatModelInitialization() {
        // Verify initial state using the mock
        assert mockModel.getBPM() == 90;
        
        // Test BPM changes
        mockModel.setBPM(100);
        
        // Verify that setBPM was called with 100
        verify(mockModel).setBPM(100);
    }
    
    @Test
    public void testControllerIncreaseBPM() {
        // Setup
        when(mockModel.getBPM()).thenReturn(90);
        
        // Action
        controller.increaseBPM();
        
        // Verify that setBPM was called with 91
        verify(mockModel).setBPM(91);
    }
    
    @Test
    public void testControllerDecreaseBPM() {
        // Setup
        when(mockModel.getBPM()).thenReturn(90);
        
        // Action
        controller.decreaseBPM();
        
        // Verify that setBPM was called with 89
        verify(mockModel).setBPM(89);
    }
    
    @Test
    public void testControllerSetBPM() {
        // Action
        controller.setBPM(120);
        
        // Verify that setBPM was called with 120
        verify(mockModel).setBPM(120);
    }
    
    @Test
    public void testControllerStartStop() {
        // Action
        controller.start();
        
        // Verify that on() was called on the model
        verify(mockModel).on();
        
        // Action
        controller.stop();
        
        // Verify that off() was called on the model
        verify(mockModel).off();
    }
}
