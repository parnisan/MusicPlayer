package MusicPlayer.modes;

import MusicPlayer.builder.Track;

import java.util.List;


public class SequentialMode implements PlaybackMode {

    @Override
    public String getName() {
        return "Последовательный";
    }

    @Override
    public int nextIndex(List<Track> tracks, int currentIndex) {
        int next = currentIndex + 1;
        return next < tracks.size() ? next : -1;
    }

    @Override
    public int previousIndex(List<Track> tracks, int currentIndex) {
        return currentIndex > 0 ? currentIndex - 1 : 0;
    }
}
