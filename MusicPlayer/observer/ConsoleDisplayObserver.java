package MusicPlayer.observer;

import MusicPlayer.builder.Track;

public class ConsoleDisplayObserver implements PlayerObserver {

    @Override
    public void onTrackChanged(Track track) {
        System.out.println("Сейчас играет: " + track);
    }
}
