package MusicPlayer.modes;

import MusicPlayer.builder.Track;

import java.util.List;


public interface PlaybackMode {

    String getName();

    int nextIndex(List<Track> tracks, int currentIndex);

    int previousIndex(List<Track> tracks, int currentIndex);
}
