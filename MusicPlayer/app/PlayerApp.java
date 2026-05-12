package MusicPlayer.app;

import MusicPlayer.builder.Track;
import MusicPlayer.observer.ConsoleDisplayObserver;
import MusicPlayer.observer.PlaybackHistoryObserver;
import MusicPlayer.playlist.Playlist;
import MusicPlayer.playlist.TrackItem;
import MusicPlayer.state.MusicPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerApp {

    private final MusicPlayer player;
    private final PlaybackHistoryObserver historyObserver;

    private final List<Track> library = new ArrayList<>();
    private final Playlist rootPlaylist = new Playlist("Моя библиотека");

    private final Scanner scanner;

    public PlayerApp(Scanner scanner) {
        this.scanner = scanner;
        this.player = new MusicPlayer();
        this.historyObserver = new PlaybackHistoryObserver();
        player.addObserver(new ConsoleDisplayObserver());
        player.addObserver(historyObserver);
        loadDemoData();
    }

    public MusicPlayer getPlayer() { return player; }
    public List<Track> getLibrary() { return library; }
    public Playlist getRootPlaylist() { return rootPlaylist; }
    public Scanner getScanner() { return scanner; }
    public PlaybackHistoryObserver getHistoryObserver() { return historyObserver; }

    private void loadDemoData() {
        Track t1 = new Track.Builder("Stairway to Heaven", "Led Zeppelin")
                .genre("Rock").durationSeconds(400).bitrate(320).build();
        Track t2 = new Track.Builder("Smells Like Teen Spirit", "Nirvana")
                .genre("Rock").durationSeconds(300).bitrate(320).build();
        Track t3 = new Track.Builder("So What", "Miles Davis")
                .genre("Jazz").durationSeconds(200).bitrate(320).build();
        Track t4 = new Track.Builder("Bohemian Rhapsody", "Queen")
                .genre("Rock").durationSeconds(10).bitrate(320).build();
        Track t5 = new Track.Builder("Take Five", "Dave Brubeck")
                .genre("Jazz").durationSeconds(10).bitrate(320).build();

        library.addAll(List.of(t1, t2, t3, t4, t5));

        Playlist rock = new Playlist("Rock");
        rock.add(new TrackItem(t1));
        rock.add(new TrackItem(t2));
        rock.add(new TrackItem(t4));

        Playlist jazz = new Playlist("Jazz");
        jazz.add(new TrackItem(t3));
        jazz.add(new TrackItem(t5));

        Playlist shorts = new Playlist("Short");
        shorts.add(new TrackItem(t4));
        shorts.add(new TrackItem(t5));

        rootPlaylist.add(rock);
        rootPlaylist.add(jazz);
        rootPlaylist.add(shorts);
    }
}
