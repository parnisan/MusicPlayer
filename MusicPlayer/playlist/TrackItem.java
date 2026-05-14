package MusicPlayer.playlist;

import MusicPlayer.builder.Track;

import java.util.Optional;

public class TrackItem implements PlaylistComponent {

    private final Track track;

    public TrackItem(Track track) {
        this.track = track;
    }

    @Override
    public Optional<Track> asTrack() {
        return Optional.of(track);
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
    public void print(String indent) {
        System.out.println(indent + "- " + track);
    }

    @Override
    public String toString() {
        return "TrackItem{track=" + track + '}';
    }
}
