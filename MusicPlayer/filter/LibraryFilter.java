package MusicPlayer.filter;

import MusicPlayer.builder.Track;

import java.util.ArrayList;
import java.util.List;

public class LibraryFilter implements TrackFilter {

    private final List<Track> library;

    public LibraryFilter(List<Track> library) {
        this.library = library;
    }

    @Override
    public List<Track> getTracks() {
        return new ArrayList<>(library);
    }
}
