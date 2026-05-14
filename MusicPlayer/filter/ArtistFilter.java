package MusicPlayer.filter;

import MusicPlayer.builder.Track;

import java.util.ArrayList;
import java.util.List;

public class ArtistFilter implements TrackFilter {

    private final TrackFilter wrapped;
    private final String artist;

    public ArtistFilter(TrackFilter wrapped, String artist) {
        this.wrapped = wrapped;
        this.artist = artist;
    }

    @Override
    public List<Track> getTracks() {
        List<Track> result = new ArrayList<>();
        for (Track track : wrapped.getTracks()) {
            if (track.getArtist().equalsIgnoreCase(artist)) {
                result.add(track);
            }
        }
        return result;
    }
}
