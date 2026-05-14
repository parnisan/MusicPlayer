package MusicPlayer.playlist;

import MusicPlayer.builder.Track;

import java.util.ArrayList;
import java.util.List;

public class TrackItem implements PlaylistComponent {

    private final Track track;

    public TrackItem(Track track) {
        this.track = track;
    }

    @Override
    public String getName() {
        return track.getTitle();
    }

    @Override
    public int getTotalDuration() {
        return track.getDurationSeconds();
    }

    @Override
    public List<Track> getTracks() {
        List<Track> result = new ArrayList<>();
        result.add(track);
        return result;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "- " + track);
    }

}
