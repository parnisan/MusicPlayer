package MusicPlayer.filter;

import MusicPlayer.builder.Track;

import java.util.ArrayList;
import java.util.List;


public class ArtistFilter implements TrackFilter {

    private final String artist;

    public ArtistFilter(String artist) {
        this.artist = artist;
    }

    @Override
    public List<Track> apply(List<Track> tracks) {
        List<Track> result = new ArrayList<>();
        for (var track : tracks) {
            if (track.getArtist().equalsIgnoreCase(artist)) {
                result.add(track);
            }
        }
        return result;
    }
}
