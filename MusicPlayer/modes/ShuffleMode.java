package MusicPlayer.modes;

import MusicPlayer.builder.Track;

import java.util.List;
import java.util.Random;


public class ShuffleMode implements PlaybackMode {

    private final Random random = new Random();

    @Override
    public String getName() {
        return "Случайный";
    }

    @Override
    public int nextIndex(List<Track> tracks, int currentIndex) {
        if (tracks.size() <= 1) {
            return 0;
        }
        int next;
        do {
            next = random.nextInt(tracks.size());
        } while (next == currentIndex);
        return next;
    }

    @Override
    public int previousIndex(List<Track> tracks, int currentIndex) {
        return nextIndex(tracks, currentIndex);
    }
}
