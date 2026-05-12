package MusicPlayer.state;

public class PausedState implements PlayerState {

    @Override
    public String getName() { return "Пауза"; }

    @Override
    public void play(MusicPlayer player) {
        System.out.println("[Плеер] Возобновление с "
                + formatTime(player.getTrackProgress()) + ".");
        player.setState(new PlayingState());
        player.getCurrentState().play(player);
    }

    @Override
    public void pause(MusicPlayer player) {
        System.out.println("[Плеер] Уже на паузе.");
    }

    @Override
    public void stop(MusicPlayer player) {
        System.out.println("[Плеер] Остановка. Прогресс сброшен.");
        player.setTrackProgress(0);
        player.setState(new StoppedState());
    }

    private String formatTime(int seconds) {
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
}
