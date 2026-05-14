package MusicPlayer.app;

import MusicPlayer.observer.ConsoleDisplayObserver;
import MusicPlayer.observer.PlaybackHistoryObserver;
import MusicPlayer.playlist.Playlist;
import MusicPlayer.state.MusicPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import MusicPlayer.builder.Track;

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
    }

    public MusicPlayer getPlayer() { return player; }
    public List<Track> getLibrary() { return library; }
    public Playlist getRootPlaylist() { return rootPlaylist; }
    public Scanner getScanner() { return scanner; }
    public PlaybackHistoryObserver getHistoryObserver() { return historyObserver; }
}
