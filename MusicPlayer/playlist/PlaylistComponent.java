package MusicPlayer.playlist;

import MusicPlayer.builder.Track;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface PlaylistComponent {

    String getName();

    int getTotalDuration();

    void print(String indent);

    default Optional<Track> asTrack() {
        return Optional.empty();
    }

    default List<PlaylistComponent> getChildren() {
        return Collections.emptyList();
    }

    default void collectContainers(List<Playlist> result) {}
}
