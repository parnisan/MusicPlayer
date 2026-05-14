package MusicPlayer.observer;

import MusicPlayer.builder.Track;

public interface PlayerObserver {

    void onTrackChanged(Track track);
}
