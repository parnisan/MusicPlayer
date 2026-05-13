package MusicPlayer.filter;

import MusicPlayer.builder.Track;

import java.util.List;

public interface TrackFilter {

    List<Track> apply(List<Track> tracks);
}
