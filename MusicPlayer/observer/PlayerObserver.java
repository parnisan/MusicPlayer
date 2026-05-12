package MusicPlayer.observer;

import MusicPlayer.builder.Track;

public interface PlayerObserver {

    void onTrackChanged(Track track);

    void onStateChanged(String stateName);
}
