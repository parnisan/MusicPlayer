package MusicPlayer.app;

import MusicPlayer.builder.Track;
import MusicPlayer.command.AddTrackCommand;
import MusicPlayer.command.ClearQueueCommand;
import MusicPlayer.command.JumpToTrackCommand;
import MusicPlayer.command.MoveTrackCommand;
import MusicPlayer.command.RemoveTrackCommand;
import MusicPlayer.modes.RepeatOneMode;
import MusicPlayer.modes.RepeatPlaylistMode;
import MusicPlayer.modes.SequentialMode;
import MusicPlayer.modes.ShuffleMode;
import MusicPlayer.state.MusicPlayer;

import java.util.List;
import java.util.Scanner;

public class PlaybackMenu {

    private static final String HEADER = "Воспроизведение";
    private static final String QUEUE_HEADER = "Очередь";
    private static final String MODE_HEADER = "Режим воспроизведения";
    private static final String OPTION_PLAY = "1 - Play";
    private static final String OPTION_STOP = "2 - Stop";
    private static final String OPTION_NEXT = "3 - Next";
    private static final String OPTION_PREVIOUS = "4 - Previous";
    private static final String OPTION_MODE = "5 - Сменить режим";
    private static final String OPTION_QUEUE = "6 - Очередь";
    private static final String OPTION_BACK = "0 - Назад";
    private static final String OPTION_REMOVE_TRACK = "1 - Удалить трек";
    private static final String OPTION_ADD_TRACK = "2 - Добавить трек из библиотеки";
    private static final String OPTION_MOVE_UP = "3 - Переместить вверх";
    private static final String OPTION_MOVE_DOWN = "4 - Переместить вниз";
    private static final String OPTION_JUMP = "5 - Перейти к треку";
    private static final String OPTION_CLEAR = "6 - Очистить очередь";
    private static final String PROMPT = "> ";
    private static final String UNKNOWN_COMMAND = "Неизвестная команда.";
    private static final String UNKNOWN_MODE = "Неизвестный режим.";
    private static final String EMPTY_QUEUE = "Очередь пуста.";
    private static final String END_OF_PLAYLIST = "Конец плейлиста.";
    private static final String MODE_CHANGED_PREFIX = "Режим: ";
    private static final String STATUS_MODE_PREFIX = "Режим: ";
    private static final String STATUS_STATE_PREFIX = " Состояние: ";
    private static final String STATUS_COUNT_PREFIX = " Треков в очереди: ";
    private static final String STATUS_TRACK_PREFIX = "Трек: ";
    private static final String CURRENT_TRACK_MARKER = " <";
    private static final String TRACK_PROMPT = "Номер трека (0 - отмена): ";
    private static final String CANCEL = "Отмена.";
    private static final String NUMBER_ERROR = "Введите число.";
    private static final String NOT_ENOUGH_TRACKS = "Недостаточно треков.";
    private static final String CANNOT_MOVE = "Невозможно переместить.";
    private static final String CLEAR_CONFIRM_PROMPT = "Очистить очередь? (д/н): ";
    private static final String CONFIRM_ANSWER = "д";
    private static final String QUEUE_ALREADY_EMPTY = "Очередь уже пуста.";
    private static final String EMPTY_LIBRARY = "Библиотека пуста.";
    private static final int MOVE_UP = -1;
    private static final int MOVE_DOWN = 1;

    private final PlayerApp app;
    private final MusicPlayer player;
    private final Scanner scanner;

    public PlaybackMenu(PlayerApp app) {
        this.app = app;
        this.player = app.getPlayer();
        this.scanner = app.getScanner();
    }

    public void open() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println(HEADER);
            printStatus();
            System.out.println(OPTION_PLAY);
            System.out.println(OPTION_STOP);
            System.out.println(OPTION_NEXT);
            System.out.println(OPTION_PREVIOUS);
            System.out.println(OPTION_MODE);
            System.out.println(OPTION_QUEUE);
            System.out.println(OPTION_BACK);
            System.out.print(PROMPT);

            switch (scanner.nextLine().trim()) {
                case "1" -> player.play();
                case "2" -> player.stop();
                case "3" -> pressNext();
                case "4" -> pressPrevious();
                case "5" -> changeMode();
                case "6" -> manageQueue();
                case "0" -> inMenu = false;
                default  -> System.out.println(UNKNOWN_COMMAND);
            }
        }
    }

    private void printStatus() {
        System.out.println(STATUS_MODE_PREFIX + player.getPlaybackMode().getName()
                + STATUS_STATE_PREFIX + player.getStateName()
                + STATUS_COUNT_PREFIX + player.getQueue().size());
        int idx = player.getCurrentIndex();
        List<Track> queue = player.getQueue();
        if (idx >= 0 && idx < queue.size()) {
            Track track = queue.get(idx);
            System.out.println(STATUS_TRACK_PREFIX + track
                    + " " + formatTime(player.getTrackProgress())
                    + "/" + formatTime(track.getDurationSeconds()));
        }
    }

    private String formatTime(int seconds) {
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }

    private void pressNext() {
        if (player.getQueue().isEmpty()) {
            System.out.println(EMPTY_QUEUE);
            return;
        }
        if (!player.next()) {
            System.out.println(END_OF_PLAYLIST);
            player.stop();
        }
    }

    private void pressPrevious() {
        if (player.getQueue().isEmpty()) {
            System.out.println(EMPTY_QUEUE);
            return;
        }
        player.previous();
    }

    private void changeMode() {
        System.out.println(MODE_HEADER);
        System.out.println("1 - Последовательный");
        System.out.println("2 - Случайный");
        System.out.println("3 - Повтор трека");
        System.out.println("4 - Повтор плейлиста");
        System.out.println(OPTION_BACK);
        System.out.print(PROMPT);

        switch (scanner.nextLine().trim()) {
            case "1" -> player.setPlaybackMode(new SequentialMode());
            case "2" -> player.setPlaybackMode(new ShuffleMode());
            case "3" -> player.setPlaybackMode(new RepeatOneMode());
            case "4" -> player.setPlaybackMode(new RepeatPlaylistMode());
            case "0" -> { return; }
            default  -> { System.out.println(UNKNOWN_MODE); return; }
        }
        System.out.println(MODE_CHANGED_PREFIX + player.getPlaybackMode().getName());
    }

    private void manageQueue() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println(QUEUE_HEADER);
            showQueue();
            System.out.println(OPTION_REMOVE_TRACK);
            System.out.println(OPTION_ADD_TRACK);
            System.out.println(OPTION_MOVE_UP);
            System.out.println(OPTION_MOVE_DOWN);
            System.out.println(OPTION_JUMP);
            System.out.println(OPTION_CLEAR);
            System.out.println(OPTION_BACK);
            System.out.print(PROMPT);

            switch (scanner.nextLine().trim()) {
                case "1" -> removeFromQueue();
                case "2" -> addToQueue();
                case "3" -> moveInQueue(MOVE_UP);
                case "4" -> moveInQueue(MOVE_DOWN);
                case "5" -> jumpToTrack();
                case "6" -> clearQueue();
                case "0" -> inMenu = false;
                default  -> System.out.println(UNKNOWN_COMMAND);
            }
        }
    }

    private void showQueue() {
        List<Track> queue = player.getQueue();
        if (queue.isEmpty()) {
            System.out.println(EMPTY_QUEUE);
            return;
        }
        int current = player.getCurrentIndex();
        for (int i = 0; i < queue.size(); i++) {
            if (i == current) {
                System.out.println((i + 1) + ". " + queue.get(i) + CURRENT_TRACK_MARKER);
            } else {
                System.out.println((i + 1) + ". " + queue.get(i));
            }
        }
    }

    private void removeFromQueue() {
        List<Track> queue = player.getQueue();
        if (queue.isEmpty()) {
            System.out.println(EMPTY_QUEUE);
            return;
        }
        System.out.print(TRACK_PROMPT);
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= queue.size()) {
                System.out.println(CANCEL);
                return;
            }
            new RemoveTrackCommand(player, idx).execute();
        } catch (NumberFormatException e) {
            System.out.println(NUMBER_ERROR);
        }
    }

    private void addToQueue() {
        List<Track> library = app.getLibrary();
        if (library.isEmpty()) {
            System.out.println(EMPTY_LIBRARY);
            return;
        }
        for (int i = 0; i < library.size(); i++) {
            System.out.println((i + 1) + ". " + library.get(i));
        }
        System.out.print(TRACK_PROMPT);
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= library.size()) {
                System.out.println(CANCEL);
                return;
            }
            new AddTrackCommand(player, library.get(idx)).execute();
        } catch (NumberFormatException e) {
            System.out.println(NUMBER_ERROR);
        }
    }

    private void moveInQueue(int direction) {
        List<Track> queue = player.getQueue();
        if (queue.size() < 2) {
            System.out.println(NOT_ENOUGH_TRACKS);
            return;
        }
        System.out.print(TRACK_PROMPT);
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            int newIdx = idx + direction;
            if (idx < 0 || idx >= queue.size() || newIdx < 0 || newIdx >= queue.size()) {
                System.out.println(CANNOT_MOVE);
                return;
            }
            new MoveTrackCommand(player, idx, direction).execute();
        } catch (NumberFormatException e) {
            System.out.println(NUMBER_ERROR);
        }
    }

    private void jumpToTrack() {
        List<Track> queue = player.getQueue();
        if (queue.isEmpty()) {
            System.out.println(EMPTY_QUEUE);
            return;
        }
        System.out.print(TRACK_PROMPT);
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= queue.size()) {
                System.out.println(CANCEL);
                return;
            }
            new JumpToTrackCommand(player, idx).execute();
        } catch (NumberFormatException e) {
            System.out.println(NUMBER_ERROR);
        }
    }

    private void clearQueue() {
        if (player.getQueue().isEmpty()) {
            System.out.println(QUEUE_ALREADY_EMPTY);
            return;
        }
        System.out.print(CLEAR_CONFIRM_PROMPT);
        if (scanner.nextLine().trim().equalsIgnoreCase(CONFIRM_ANSWER)) {
            new ClearQueueCommand(player).execute();
        }
    }
}
