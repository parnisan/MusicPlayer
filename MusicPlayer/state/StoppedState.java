package MusicPlayer.state;

public class StoppedState implements PlayerState {

    private static final String STATE_NAME = "Остановлен";
    private static final String EMPTY_QUEUE = "Очередь пуста. Загрузите плейлист.";
    private static final String CANNOT_PAUSE = "Нельзя поставить на паузу - воспроизведение не запущено.";
    private static final String ALREADY_STOPPED = "Уже остановлен.";

    @Override
    public String getName() {
        return STATE_NAME;
    }

    @Override
    public void play(MusicPlayer player) {
        if (player.getQueue().isEmpty()) {
            System.out.println(EMPTY_QUEUE);
            return;
        }
        player.setTrackProgress(0);
        player.setState(new PlayingState());
        player.notifyTrackChanged(player.getQueue().get(player.getCurrentIndex()));
        player.getCurrentState().play(player);
    }

    @Override
    public void pause(MusicPlayer player) {
        System.out.println(CANNOT_PAUSE);
    }

    @Override
    public void stop(MusicPlayer player) {
        System.out.println(ALREADY_STOPPED);
    }
}
