package MusicPlayer.state;

public class PausedState implements PlayerState {

    @Override
    public String getName() {
        return "Пауза";
    }

    @Override
    public void play(MusicPlayer player) {
        if (player.getQueue().isEmpty()) {
            System.out.println("Очередь пуста. Загрузите плейлист.");
            return;
        }
        player.setState(new PlayingState());
        player.getCurrentState().play(player);
    }

    @Override
    public void pause(MusicPlayer player) {
        System.out.println("Уже на паузе.");
    }
}
