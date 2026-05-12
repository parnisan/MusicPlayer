package MusicPlayer.state;

public class StoppedState implements PlayerState {

    @Override
    public String getName() { return "Остановлен"; }

    @Override
    public void play(MusicPlayer player) {
        if (player.getQueue().isEmpty()) {
            System.out.println("[Плеер] Очередь пуста. Загрузите плейлист.");
            return;
        }
        player.setTrackProgress(0);
        player.setState(new PlayingState());
        player.notifyTrackChanged(player.getQueue().get(player.getCurrentIndex()));
        player.getCurrentState().play(player);
    }

    @Override
    public void pause(MusicPlayer player) {
        System.out.println("[Плеер] Нельзя поставить на паузу — воспроизведение не запущено.");
    }

    @Override
    public void stop(MusicPlayer player) {
        System.out.println("[Плеер] Уже остановлен.");
    }
}
