package MusicPlayer.filter;

import MusicPlayer.builder.Track;

import java.util.List;

public class TrackFilterContext {

    private TrackFilter strategy;

    public void setStrategy(TrackFilter strategy) {
        this.strategy = strategy;
    }

    public List<Track> apply(List<Track> tracks) {
        if (strategy == null) {
            throw new IllegalStateException("TrackFilter не установлен.");
        }
        return strategy.apply(tracks);
    }
}
