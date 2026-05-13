package MusicPlayer.filter;

import MusicPlayer.builder.Track;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByDurationFilter implements TrackFilter {

    @Override
    public List<Track> apply(List<Track> tracks) {
        List<Track> sorted = new ArrayList<>(tracks);
        sorted.sort(Comparator.comparingInt(Track::getDurationSeconds));
        return sorted;
    }
}
