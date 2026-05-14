package MusicPlayer.state;

import MusicPlayer.builder.Track;

public class PlayingState implements PlayerState {

    @Override
    public String getName() {
        return "Воспроизведение";
    }

    @Override
    public void play(MusicPlayer player) {
        Track track = player.getQueue().get(player.getCurrentIndex());
        player.notifyTrackChanged(track);
    }

    @Override
    public void pause(MusicPlayer player) {
        player.setState(new PausedState());
        System.out.println("Пауза.");
    }
}
