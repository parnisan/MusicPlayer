package MusicPlayer.filter;

import MusicPlayer.builder.Track;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByArtistFilter implements TrackFilter {

    @Override
    public List<Track> apply(List<Track> tracks) {
        List<Track> sorted = new ArrayList<>(tracks);
        sorted.sort(Comparator.comparing(Track::getArtist, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }
}
