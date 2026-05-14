package MusicPlayer.state;

public interface PlayerState {

    String getName();

    void play(MusicPlayer player);

    void pause(MusicPlayer player);
}
