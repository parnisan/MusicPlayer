package MusicPlayer.observer;

import MusicPlayer.builder.Track;

public class ConsoleDisplayObserver implements PlayerObserver {

    private static final String NOW_PLAYING_PREFIX = "Сейчас играет: ";
    private static final String STATE_PREFIX = "Состояние: ";

    @Override
    public void onTrackChanged(Track track) {
        System.out.println(NOW_PLAYING_PREFIX + track);
    }

    @Override
    public void onStateChanged(String stateName) {
        System.out.println(STATE_PREFIX + stateName);
    }
}
