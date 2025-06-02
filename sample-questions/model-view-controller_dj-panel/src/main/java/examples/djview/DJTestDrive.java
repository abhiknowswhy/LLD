package examples.djview;

import examples.djview.controller.*;
import examples.djview.model.*;
import java.net.URL;
import javax.sound.sampled.*;

public class DJTestDrive {
    public static void main(String[] args) {
        // Print audio system information to help diagnose issues
        System.out.println("Available audio devices:");
        try {
            Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
            for (Mixer.Info info : mixerInfos) {
                System.out.println("Mixer: " + info.getName() + " - " + info.getDescription());
            }
        } catch (Exception e) {
            System.out.println("Error listing audio devices: " + e.getMessage());
        }
        
        // Test playing a simple tone to check if audio works at all
        try {
            System.out.println("Testing audio with a simple tone...");
            testAudioWithTone();
            System.out.println("Tone test complete");
        } catch (Exception e) {
            System.out.println("Error playing test tone: " + e.getMessage());
        }
        
        // Add debug to check if resource exists
        System.out.println("Checking if clap.wav resource exists...");
        URL resourceUrl = DJTestDrive.class.getResource("/clap.wav");
        System.out.println("Resource URL: " + resourceUrl);
        
        // Continue with normal application flow
        BeatModelInterface model = new BeatModel();
        ControllerInterface controller = new BeatController(model);
        
        // Start the model with a default BPM
        controller.start();
        System.out.println("Model started with initial BPM");
    }
    
    // Test method to generate and play a simple tone
    private static void testAudioWithTone() throws LineUnavailableException {
        try (SourceDataLine line = AudioSystem.getSourceDataLine(new AudioFormat(44100, 16, 1, true, false))) {
            line.open();
            line.start();
            
            byte[] buffer = new byte[44100];
            for (int i = 0; i < buffer.length; i++) {
                double angle = i / (44100.0 / 440.0) * 2.0 * Math.PI;
                buffer[i] = (byte)(Math.sin(angle) * 100);
            }
            
            // Set a higher gain if possible
            if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(gainControl.getMaximum());
                System.out.println("Increased volume to maximum");
            }
            
            line.write(buffer, 0, buffer.length);
            line.drain();
        }
    }
}
