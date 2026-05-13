package MusicPlayer.observer;

import MusicPlayer.builder.Track;

import java.util.ArrayDeque;
import java.util.Deque;

public class PlaybackHistoryObserver implements PlayerObserver {

    private static final int MAX_HISTORY = 20;
    private final Deque<Track> history = new ArrayDeque<>();

    @Override
    public void onTrackChanged(Track track) {
        if (history.size() >= MAX_HISTORY) {
            history.pollFirst();
        }
        history.addLast(track);
    }

    @Override
    public void onStateChanged(String stateName) {}

    public void printHistory() {
        if (history.isEmpty()) {
            System.out.println("История пуста.");
            return;
        }
        System.out.println("История прослушивания:");
        int i = 1;
        for (Track track : history) {
            System.out.println(i + ". " + track);
            i++;
        }
    }
}
