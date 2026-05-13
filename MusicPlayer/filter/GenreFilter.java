package MusicPlayer.filter;

import MusicPlayer.builder.Track;

import java.util.List;


public class GenreFilter implements TrackFilter {

    private final String genre;

    public GenreFilter(String genre) {
        this.genre = genre;
    }

    @Override
    public List<Track> apply(List<Track> tracks) {
        List<Track> result = new java.util.ArrayList<>();
        for (Track track : tracks) {
            if (track.getGenre().equalsIgnoreCase(genre)) {
                result.add(track);
            }
        }
        return result;
    }
}
