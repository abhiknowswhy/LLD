package questions.lld.Jukebox;

import java.util.*;

public class SongLibrary {
    private final Map<String, Song> songsById;
    private final Map<String, List<Song>> songsByArtist;

    public SongLibrary() {
        this.songsById = new LinkedHashMap<>();
        this.songsByArtist = new HashMap<>();
    }

    public void addSong(Song song) {
        songsById.put(song.getId(), song);
        songsByArtist
                .computeIfAbsent(song.getArtist().toLowerCase(), k -> new ArrayList<>())
                .add(song);
    }

    public Song getSongById(String id) {
        return songsById.get(id);
    }

    public List<Song> searchByTitle(String query) {
        String q = query.toLowerCase();
        return songsById.values().stream()
                .filter(s -> s.getTitle().toLowerCase().contains(q))
                .toList();
    }

    public List<Song> searchByArtist(String artist) {
        return songsByArtist.getOrDefault(artist.toLowerCase(), Collections.emptyList());
    }

    public Collection<Song> getAllSongs() {
        return songsById.values();
    }

    public int size() { return songsById.size(); }
}
