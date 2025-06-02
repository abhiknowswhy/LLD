package examples.djview.view;

public interface ViewInterface {
    void createView();
    void createControls();
    void enableStopMenuItem();
    void disableStopMenuItem();
    void enableStartMenuItem();
    void disableStartMenuItem();
    void updateBPM();
    void updateBeat();
}
