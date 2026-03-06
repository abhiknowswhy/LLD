package questions.lld.Jukebox;

import java.util.*;

/**
 * Jukebox: manages a song library, queue, and playback.
 */
public class Jukebox {
    private final SongLibrary library;
    private final Deque<Song> queue;
    private final Map<String, Playlist> playlists;
    private Song currentSong;
    private boolean playing;

    public Jukebox() {
        this.library = new SongLibrary();
        this.queue = new LinkedList<>();
        this.playlists = new LinkedHashMap<>();
        this.playing = false;
    }

    // --- Library ---
    public void addSongToLibrary(Song song) {
        library.addSong(song);
    }

    public SongLibrary getLibrary() { return library; }

    // --- Queue Management ---
    public void addToQueue(Song song) {
        queue.addLast(song);
    }

    public void addToQueue(Playlist playlist) {
        for (Song song : playlist.getSongs()) {
            queue.addLast(song);
        }
    }

    public Song removeFromQueue() {
        return queue.pollFirst();
    }

    public List<Song> getQueue() {
        return List.copyOf(queue);
    }

    public int getQueueSize() { return queue.size(); }

    // --- Playback ---
    public Song play() {
        if (currentSong == null) {
            currentSong = queue.pollFirst();
        }
        if (currentSong != null) {
            playing = true;
        }
        return currentSong;
    }

    public void pause() {
        playing = false;
    }

    public Song skip() {
        currentSong = queue.pollFirst();
        playing = currentSong != null;
        return currentSong;
    }

    public Song getCurrentSong() { return currentSong; }
    public boolean isPlaying() { return playing; }

    // --- Playlist Management ---
    public Playlist createPlaylist(String name) {
        Playlist playlist = new Playlist(name);
        playlists.put(name, playlist);
        return playlist;
    }

    public Playlist getPlaylist(String name) {
        return playlists.get(name);
    }

    public Collection<Playlist> getAllPlaylists() {
        return playlists.values();
    }

    // --- Shuffle ---
    public void shuffleQueue() {
        List<Song> list = new ArrayList<>(queue);
        Collections.shuffle(list);
        queue.clear();
        queue.addAll(list);
    }
}
