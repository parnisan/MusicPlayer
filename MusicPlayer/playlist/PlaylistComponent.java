package MusicPlayer.playlist;

import MusicPlayer.builder.Track;

import java.util.Collections;
import java.util.List;

public interface PlaylistComponent {

    String getName();

    int getTotalDuration();

    void print(String indent);

    List<Track> getTracks();

    default List<PlaylistComponent> getChildren() {
        return Collections.emptyList();
    }

    default void collectContainers(List<Playlist> result) {}
}
