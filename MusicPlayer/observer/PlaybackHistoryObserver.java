package MusicPlayer.observer;

import MusicPlayer.builder.Track;

import java.util.ArrayList;
import java.util.List;

public class PlaybackHistoryObserver implements PlayerObserver {

    private static final int MAX_HISTORY = 20;

    private final List<Track> history = new ArrayList<>();

    @Override
    public void onTrackChanged(Track track) {
        if (history.size() >= MAX_HISTORY) history.remove(0);
        history.add(track);
    }

    public void printHistory() {
        if (history.isEmpty()) {
            System.out.println("История пуста.");
            return;
        }
        System.out.println("История прослушивания:");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }
}
