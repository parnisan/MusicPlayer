package MusicPlayer.filter;

import MusicPlayer.builder.Track;

import java.util.ArrayList;
import java.util.List;

public class SortByArtistFilter implements TrackFilter {

    private final TrackFilter wrapped;

    public SortByArtistFilter(TrackFilter wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public List<Track> getTracks() {
        List<Track> result = new ArrayList<>(wrapped.getTracks());
        for (int i = 0; i < result.size() - 1; i++) {
            for (int j = i + 1; j < result.size(); j++) {
                if (result.get(i).getArtist().compareToIgnoreCase(result.get(j).getArtist()) > 0) {
                    Track temp = result.get(i);
                    result.set(i, result.get(j));
                    result.set(j, temp);
                }
            }
        }
        return result;
    }
}
