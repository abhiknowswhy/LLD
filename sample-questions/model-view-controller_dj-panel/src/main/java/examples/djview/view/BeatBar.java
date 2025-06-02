package examples.djview.view;

import javax.swing.*;

public class BeatBar extends JProgressBar {
    private static final long serialVersionUID = 2L;
    JProgressBar progressBar;
 
    public BeatBar() {
        setMaximum(100);
        setValue(0);
        setStringPainted(true);
    }
}
