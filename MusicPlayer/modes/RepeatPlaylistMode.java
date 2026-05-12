package MusicPlayer.modes;

import MusicPlayer.builder.Track;

import java.util.List;


public class RepeatPlaylistMode implements PlaybackMode {

    @Override
    public String getName() {
        return "Повтор плейлиста";
    }

    @Override
    public int nextIndex(List<Track> tracks, int currentIndex) {
        return (currentIndex + 1) % tracks.size();
    }

    @Override
    public int previousIndex(List<Track> tracks, int currentIndex) {
        return (currentIndex - 1 + tracks.size()) % tracks.size();
    }
}
