package MusicPlayer.filter;

import MusicPlayer.builder.Track;

import java.util.ArrayList;
import java.util.List;

public class GenreFilter implements TrackFilter {

    private final TrackFilter wrapped;
    private final String genre;

    public GenreFilter(TrackFilter wrapped, String genre) {
        this.wrapped = wrapped;
        this.genre = genre;
    }

    @Override
    public List<Track> getTracks() {
        List<Track> result = new ArrayList<>();
        for (Track track : wrapped.getTracks()) {
            if (track.getGenre().equalsIgnoreCase(genre)) {
                result.add(track);
            }
        }
        return result;
    }
}
