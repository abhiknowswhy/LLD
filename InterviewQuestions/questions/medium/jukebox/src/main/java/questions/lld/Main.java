package questions.lld;

import questions.lld.Jukebox.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Jukebox Demo ===\n");

        Jukebox jukebox = new Jukebox();

        // Add songs
        Song s1 = new Song("1", "Bohemian Rhapsody", "Queen", 355);
        Song s2 = new Song("2", "Hotel California", "Eagles", 391);
        Song s3 = new Song("3", "Stairway to Heaven", "Led Zeppelin", 482);
        Song s4 = new Song("4", "We Will Rock You", "Queen", 122);
        jukebox.addSongToLibrary(s1);
        jukebox.addSongToLibrary(s2);
        jukebox.addSongToLibrary(s3);
        jukebox.addSongToLibrary(s4);

        // Create playlist
        Playlist rock = jukebox.createPlaylist("Classic Rock");
        rock.addSong(s1);
        rock.addSong(s2);
        rock.addSong(s3);

        // Queue playlist
        jukebox.addToQueue(rock);
        System.out.println("Queue size: " + jukebox.getQueueSize());

        // Play
        Song current = jukebox.play();
        System.out.println("Now playing: " + current);
        System.out.println("Is playing: " + jukebox.isPlaying());

        // Skip
        current = jukebox.skip();
        System.out.println("Skipped to: " + current);

        // Search
        System.out.println("\nSearch 'queen': " + jukebox.getLibrary().searchByArtist("queen"));
    }
}