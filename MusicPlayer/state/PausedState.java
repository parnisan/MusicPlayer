package MusicPlayer.state;

public class PausedState implements PlayerState {

    private static final String STATE_NAME = "Пауза";
    private static final String RESUME_PREFIX = "Возобновление с ";
    private static final String ALREADY_PAUSED = "Уже на паузе.";
    private static final String STOPPING = "Остановка.";

    @Override
    public String getName() {
        return STATE_NAME;
    }

    @Override
    public void play(MusicPlayer player) {
        System.out.println(RESUME_PREFIX + formatTime(player.getTrackProgress()));
        player.setState(new PlayingState());
        player.getCurrentState().play(player);
    }

    @Override
    public void pause(MusicPlayer player) {
        System.out.println(ALREADY_PAUSED);
    }

    @Override
    public void stop(MusicPlayer player) {
        System.out.println(STOPPING);
        player.setTrackProgress(0);
        player.setState(new StoppedState());
    }

    private String formatTime(int seconds) {
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
}
