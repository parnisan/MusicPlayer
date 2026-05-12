package MusicPlayer.filter;

import MusicPlayer.builder.Track;

import java.util.List;
import java.util.Objects;

public class TrackFilterContext {

    private TrackFilter strategy;

    public void setStrategy(TrackFilter strategy) {
        this.strategy = strategy;
    }

    public List<Track> apply(List<Track> tracks) {
        if (Objects.isNull(strategy)) {
            throw new IllegalStateException("TrackFilter не установлен.");
        }
        return strategy.apply(tracks);
    }
}
