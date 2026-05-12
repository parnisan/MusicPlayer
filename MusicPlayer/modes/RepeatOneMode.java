package MusicPlayer.modes;

import MusicPlayer.builder.Track;

import java.util.List;


public class RepeatOneMode implements PlaybackMode {

    @Override
    public String getName() {
        return "Повтор трека";
    }

    @Override
    public int nextIndex(List<Track> tracks, int currentIndex) {
        return currentIndex;
    }

    @Override
    public int previousIndex(List<Track> tracks, int currentIndex) {
        return currentIndex;
    }
}
