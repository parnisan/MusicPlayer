package MusicPlayer.state;

import MusicPlayer.builder.Track;

import java.io.IOException;

public class PlayingState implements PlayerState {

    private static final String STATE_NAME = "Воспроизведение";
    private static final String PRESS_ENTER = "Нажмите Enter для паузы.";
    private static final String END_OF_PLAYLIST = "Конец плейлиста.";
    private static final String INTERRUPTED = "Воспроизведение прервано.";
    private static final String IO_ERROR = "Ошибка ввода.";
    private static final String PAUSE_PREFIX = "Пауза. Прогресс: ";
    private static final String STOPPING = "Остановка.";
    private static final String PROGRESS_SEPARATOR = " - ";
    private static final String TIME_SEPARATOR = "/";
    private static final int STEP_SECONDS = 3;

    @Override
    public String getName() {
        return STATE_NAME;
    }

    @Override
    public void play(MusicPlayer player) {
        System.out.println(PRESS_ENTER);

        try {
            while (true) {
                Thread.sleep(STEP_SECONDS * 1000L);

                player.setTrackProgress(player.getTrackProgress() + STEP_SECONDS);

                if (player.getTrackProgress() >= currentTrackDuration(player)) {
                    if (!player.next()) {
                        System.out.println(END_OF_PLAYLIST);
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
            System.out.println(INTERRUPTED);
        } catch (IOException e) {
            System.out.println(IO_ERROR);
        }
    }

    @Override
    public void pause(MusicPlayer player) {
        System.out.println(PAUSE_PREFIX + formatTime(player.getTrackProgress()));
        player.setState(new PausedState());
    }

    @Override
    public void stop(MusicPlayer player) {
        System.out.println(STOPPING);
        player.setTrackProgress(0);
        player.setState(new StoppedState());
    }

    private int currentTrackDuration(MusicPlayer player) {
        return player.getQueue().get(player.getCurrentIndex()).getDurationSeconds();
    }

    private void printProgress(MusicPlayer player) {
        Track track = player.getQueue().get(player.getCurrentIndex());
        System.out.println(track.getTitle() + PROGRESS_SEPARATOR + track.getArtist()
                + " " + formatTime(player.getTrackProgress())
                + TIME_SEPARATOR + formatTime(track.getDurationSeconds()));
    }

    private String formatTime(int seconds) {
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
}
