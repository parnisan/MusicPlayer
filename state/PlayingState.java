package MusicPlayer.state;

import MusicPlayer.builder.Track;

import java.io.IOException;

public class PlayingState implements PlayerState {

    private static final int STEP_SECONDS = 3;

    @Override
    public String getName() { return "Воспроизведение"; }

    @Override
    public void play(MusicPlayer player) {
        System.out.println("Нажмите Enter для паузы.");

        try {
            while (true) {
                Thread.sleep(STEP_SECONDS * 1000L);

                player.setTrackProgress(player.getTrackProgress() + STEP_SECONDS);

                if (player.getTrackProgress() >= currentTrackDuration(player)) {
                    if (!player.next()) {
                        System.out.println("Конец плейлиста.");
                        player.stop();
                        return;
                    }
                }

                printProgress(player);

                if (System.in.available() > 0) {
                    System.in.read();
                    player.pause();
                    return;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Воспроизведение прервано.");
        } catch (IOException e) {
            System.out.println("Ошибка ввода.");
        }
    }

    @Override
    public void pause(MusicPlayer player) {
        System.out.println("Пауза. Прогресс: " + formatTime(player.getTrackProgress()));
        player.setState(new PausedState());
    }

    @Override
    public void stop(MusicPlayer player) {
        System.out.println("Остановка.");
        player.setTrackProgress(0);
        player.setState(new StoppedState());
    }

    private int currentTrackDuration(MusicPlayer player) {
        return player.getQueue().get(player.getCurrentIndex()).getDurationSeconds();
    }

    private void printProgress(MusicPlayer player) {
        Track track = player.getQueue().get(player.getCurrentIndex());
        System.out.println(track.getTitle() + " - " + track.getArtist()
                + " " + formatTime(player.getTrackProgress())
                + "/" + formatTime(track.getDurationSeconds()));
    }

    private String formatTime(int seconds) {
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
}
