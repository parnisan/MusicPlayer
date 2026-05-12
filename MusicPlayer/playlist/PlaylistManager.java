package MusicPlayer.playlist;

import MusicPlayer.builder.Track;

import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {

    public List<Track> collectTracks(Playlist playlist) {
        List<Track> tracks = new ArrayList<>();
        for (Track track : playlist) {
            tracks.add(track);
        }
        return tracks;
    }

    public List<Playlist> collectAllPlaylists(Playlist root) {
        List<Playlist> result = new ArrayList<>();
        root.collectContainers(result);
        return result;
    }
}
