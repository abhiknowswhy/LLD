package examples.djview.model;

import java.util.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import java.io.ByteArrayOutputStream;
import java.net.URL;

public class BeatModel implements BeatModelInterface, Runnable {
    List<BeatObserver> beatObservers = new ArrayList<BeatObserver>();
    List<BPMObserver> bpmObservers = new ArrayList<BPMObserver>();
    int bpm = 90;
    Thread thread;
    boolean stop = false;
    Clip clip;
    // Store the audio data in memory to avoid disk access
    byte[] audioData;
    AudioFormat audioFormat;
    
    public void initialize() {
        try {
            System.out.println("Initializing audio...");
            URL url = getClass().getResource("/clap.wav");
            System.out.println("Resource URL: " + url);
            if (url == null) {
                throw new RuntimeException("Could not find clap.wav in resources");
            }
            
            // Read and cache the entire audio file into memory
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            audioFormat = audioStream.getFormat();
            
            // Read the entire audio file into a byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            
            System.out.println("Caching audio data in memory...");
            while ((bytesRead = audioStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            
            audioData = byteArrayOutputStream.toByteArray();
            System.out.println("Audio data cached: " + audioData.length + " bytes");
            
            // Close the original stream since we've cached the data
            audioStream.close();
            
            // Create a new audio stream from the cached data
            AudioInputStream cachedStream = new AudioInputStream(
                new java.io.ByteArrayInputStream(audioData),
                audioFormat,
                audioData.length / audioFormat.getFrameSize()
            );
            
            // Get a clip that supports the audio format
            Line.Info lineInfo = new Line.Info(Clip.class);
            if (!AudioSystem.isLineSupported(lineInfo)) {
                throw new RuntimeException("Audio line not supported");
            }
            
            clip = (Clip) AudioSystem.getLine(lineInfo);
            clip.open(cachedStream);
            
            // Set volume to maximum
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float maxGain = gainControl.getMaximum();
                System.out.println("Setting clip volume to maximum gain: " + maxGain);
                gainControl.setValue(maxGain);
            } else {
                System.out.println("Volume control not supported for this clip");
            }
            
            // Test the clip
            clip.setFramePosition(0);
            clip.start();
            System.out.println("Testing sound playback...");
            Thread.sleep(500); // Wait longer to hear the test sound
            clip.stop();
            clip.setFramePosition(0);
            
            System.out.println("Audio initialized and tested successfully");
        }
        catch(Exception ex) {
            System.err.println("Error: Can't load clip");
            ex.printStackTrace();
        }
    }

    public void on() {
        bpm = 90;
        thread = new Thread(this);
        stop = false;
        thread.start();
    }

    public void off() {
        stopBeat();
        stop = true;
    }    
    
    public void run() {
        while (!stop) {
            try {
                long interval = (long) (60000.0 / getBPM());
                long startTime = System.currentTimeMillis();
                
                System.out.println("Playing beat with BPM: " + getBPM() + ", interval: " + interval + "ms");
                playBeat();
                notifyBeatObservers();
                
                // Calculate time to next beat
                long endTime = System.currentTimeMillis();
                long elapsed = endTime - startTime;
                long sleepTime = interval - elapsed;
                
                System.out.println("Beat played, sleeping for: " + sleepTime + "ms");
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (Exception e) {
                System.err.println("Error in beat thread: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void setBPM(int bpm) {
        this.bpm = bpm;
        notifyBPMObservers();
    }

    public int getBPM() {
        return bpm;
    }

    public void registerObserver(BeatObserver o) {
        beatObservers.add(o);
    }

    public void notifyBeatObservers() {
        for(int i = 0; i < beatObservers.size(); i++) {
            BeatObserver observer = (BeatObserver)beatObservers.get(i);
            observer.updateBeat();
        }
    }

    public void registerObserver(BPMObserver o) {
        bpmObservers.add(o);
    }

    public void notifyBPMObservers() {
        for(int i = 0; i < bpmObservers.size(); i++) {
            BPMObserver observer = (BPMObserver)bpmObservers.get(i);
            observer.updateBPM();
        }
    }

    public void removeObserver(BeatObserver o) {
        int i = beatObservers.indexOf(o);
        if (i >= 0) {
            beatObservers.remove(i);
        }
    }

    public void removeObserver(BPMObserver o) {
        int i = bpmObservers.indexOf(o);
        if (i >= 0) {
            bpmObservers.remove(i);
        }
    }    
    
    public void playBeat() {
        if (clip != null) {
            try {
                System.out.println("Playing beat at BPM: " + bpm);
                
                // Check if clip is still valid
                if (!clip.isOpen()) {
                    System.err.println("Warning: Clip is not open, recreating from cached data");
                    
                    // Recreate the clip from our cached data if necessary
                    if (audioData != null && audioFormat != null) {
                        try {
                            AudioInputStream cachedStream = new AudioInputStream(
                                new java.io.ByteArrayInputStream(audioData),
                                audioFormat,
                                audioData.length / audioFormat.getFrameSize()
                            );
                            
                            Line.Info lineInfo = new Line.Info(Clip.class);
                            clip = (Clip) AudioSystem.getLine(lineInfo);
                            clip.open(cachedStream);
                            
                            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                                gainControl.setValue(gainControl.getMaximum());
                            }
                            
                            System.out.println("Successfully recreated clip from cached data");
                        } catch (Exception ex) {
                            System.err.println("Failed to recreate clip: " + ex.getMessage());
                            return;
                        }
                    } else {
                        System.err.println("Can't recreate clip - no cached data available");
                        return;
                    }
                }
                
                System.out.println("Clip is open, starting playback...");
                
                // Stop the clip if it's still playing
                if (clip.isRunning()) {
                    clip.stop();
                }
                
                clip.setFramePosition(0);
                clip.start();
                
                // Don't wait for the clip to finish if the BPM is high
                // This allows overlapping sounds at higher BPMs
                if (bpm < 150) {
                    System.out.println("Waiting for clip to finish...");
                    while (clip.isActive()) {
                        Thread.sleep(1);
                    }
                    System.out.println("Clip finished playing");
                } else {
                    System.out.println("BPM too high, not waiting for clip to finish");
                }
            } catch (Exception e) {
                System.err.println("Error playing beat: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Warning: Clip not initialized");
        }
    }

    public void stopBeat() {
        if (clip != null) {
            try {
                System.out.println("Stopping beat");
                clip.setFramePosition(0);
                clip.stop();
            } catch (Exception e) {
                System.err.println("Error stopping beat: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
