package examples.djview.controller;

import examples.djview.model.*;
import examples.djview.view.*;

/**
 * An extension of BeatController that allows injecting a view for testing purposes.
 * This allows tests to run without creating a real UI.
 */
public class TestAwareBeatController implements ControllerInterface {
    BeatModelInterface model;
    ViewInterface view;
    
    /**
     * Constructor for normal usage with real view
     */
    public TestAwareBeatController(BeatModelInterface model) {
        this.model = model;
        view = new DJView(this, model);
        view.createView();
        view.createControls();
        view.disableStopMenuItem();
        view.enableStartMenuItem();
        model.initialize();
    }
    
    /**
     * Constructor for testing that accepts a mock view
     */
    public TestAwareBeatController(BeatModelInterface model, ViewInterface view) {
        this.model = model;
        this.view = view;
        if (!(view instanceof DJView)) { // Skip UI creation for mock views
            model.initialize();
        }
    }
    
    public void start() {
        model.on();
        view.disableStartMenuItem();
        view.enableStopMenuItem();
    }
    
    public void stop() {
        model.off();
        view.disableStopMenuItem();
        view.enableStartMenuItem();
    }
    
    public void increaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm + 1);
    }
    
    public void decreaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm - 1);
    }
    
    public void setBPM(int bpm) {
        model.setBPM(bpm);
    }
}
