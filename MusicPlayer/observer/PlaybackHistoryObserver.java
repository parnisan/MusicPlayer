package MusicPlayer.observer;

import MusicPlayer.builder.Track;

import java.util.ArrayDeque;
import java.util.Deque;

public class PlaybackHistoryObserver implements PlayerObserver {

    private static final int MAX_HISTORY = 20;
    private static final String EMPTY_HISTORY = "История пуста.";
    private static final String HISTORY_HEADER = "История прослушивания:";

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
            System.out.println(EMPTY_HISTORY);
            return;
        }
        System.out.println(HISTORY_HEADER);
        int i = 1;
        for (Track track : history) {
            System.out.println(i + ". " + track);
            i++;
        }
    }
}
