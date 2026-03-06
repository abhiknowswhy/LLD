package questions.lld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import questions.lld.Jukebox.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JukeboxTest {

    private Jukebox jukebox;
    private Song s1, s2, s3;

    @BeforeEach
    void setUp() {
        jukebox = new Jukebox();
        s1 = new Song("1", "Song A", "Artist X", 200);
        s2 = new Song("2", "Song B", "Artist Y", 180);
        s3 = new Song("3", "Song C", "Artist X", 240);
        jukebox.addSongToLibrary(s1);
        jukebox.addSongToLibrary(s2);
        jukebox.addSongToLibrary(s3);
    }

    @Test
    void testAddToLibrary() {
        assertEquals(3, jukebox.getLibrary().size());
        assertEquals(s1, jukebox.getLibrary().getSongById("1"));
    }

    @Test
    void testSearchByTitle() {
        List<Song> results = jukebox.getLibrary().searchByTitle("Song A");
        assertEquals(1, results.size());
        assertEquals("Song A", results.get(0).getTitle());
    }

    @Test
    void testSearchByArtist() {
        List<Song> results = jukebox.getLibrary().searchByArtist("Artist X");
        assertEquals(2, results.size());
    }

    @Test
    void testQueueAndPlay() {
        jukebox.addToQueue(s1);
        jukebox.addToQueue(s2);
        assertEquals(2, jukebox.getQueueSize());

        Song current = jukebox.play();
        assertEquals(s1, current);
        assertTrue(jukebox.isPlaying());
        assertEquals(1, jukebox.getQueueSize());
    }

    @Test
    void testSkip() {
        jukebox.addToQueue(s1);
        jukebox.addToQueue(s2);
        jukebox.play();

        Song next = jukebox.skip();
        assertEquals(s2, next);
        assertTrue(jukebox.isPlaying());
    }

    @Test
    void testSkipToEmpty() {
        jukebox.addToQueue(s1);
        jukebox.play();

        Song next = jukebox.skip();
        assertNull(next);
        assertFalse(jukebox.isPlaying());
    }

    @Test
    void testPause() {
        jukebox.addToQueue(s1);
        jukebox.play();
        assertTrue(jukebox.isPlaying());

        jukebox.pause();
        assertFalse(jukebox.isPlaying());
    }

    @Test
    void testPlaylist() {
        Playlist pl = jukebox.createPlaylist("My Playlist");
        pl.addSong(s1);
        pl.addSong(s3);
        assertEquals(2, pl.size());
        assertEquals("My Playlist", pl.getName());
    }

    @Test
    void testQueuePlaylist() {
        Playlist pl = jukebox.createPlaylist("Mix");
        pl.addSong(s1);
        pl.addSong(s2);
        pl.addSong(s3);

        jukebox.addToQueue(pl);
        assertEquals(3, jukebox.getQueueSize());

        jukebox.play();
        assertEquals(s1, jukebox.getCurrentSong());
    }

    @Test
    void testShuffleQueue() {
        for (int i = 0; i < 20; i++) {
            jukebox.addToQueue(new Song("x" + i, "Song " + i, "A", 100));
        }
        List<Song> before = jukebox.getQueue();
        jukebox.shuffleQueue();
        List<Song> after = jukebox.getQueue();
        assertEquals(before.size(), after.size());
        // Shuffled list should contain same songs (order may differ)
        assertTrue(after.containsAll(before));
    }

    @Test
    void testSongEquality() {
        Song dup = new Song("1", "Different Title", "Different Artist", 100);
        assertEquals(s1, dup); // same id
    }

    @Test
    void testFormattedDuration() {
        Song s = new Song("x", "Test", "A", 125);
        assertEquals("2:05", s.getFormattedDuration());
    }

    @Test
    void testRemoveFromPlaylist() {
        Playlist pl = jukebox.createPlaylist("Temp");
        pl.addSong(s1);
        pl.addSong(s2);
        pl.removeSong(s1);
        assertEquals(1, pl.size());
    }
}
