package MusicPlayer.app;

import MusicPlayer.builder.Track;
import MusicPlayer.command.AddTrackCommand;
import MusicPlayer.command.ClearQueueCommand;
import MusicPlayer.command.JumpToTrackCommand;
import MusicPlayer.command.MoveTrackCommand;
import MusicPlayer.command.QueueCommandExecutor;
import MusicPlayer.command.RemoveTrackCommand;
import MusicPlayer.modes.RepeatOneMode;
import MusicPlayer.modes.RepeatPlaylistMode;
import MusicPlayer.modes.SequentialMode;
import MusicPlayer.modes.ShuffleMode;
import MusicPlayer.state.MusicPlayer;

import java.util.List;
import java.util.Scanner;

public class PlaybackMenu {

    private final PlayerApp app;
    private final MusicPlayer player;
    private final Scanner scanner;

    private final QueueCommandExecutor commandExecutor = new QueueCommandExecutor();

    public PlaybackMenu(PlayerApp app) {
        this.app = app;
        this.player = app.getPlayer();
        this.scanner = app.getScanner();
    }

    public void open() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("Воспроизведение");
            printStatus();
            System.out.println("1 - Play");
            System.out.println("2 - Stop");
            System.out.println("3 - Next");
            System.out.println("4 - Previous");
            System.out.println("5 - Сменить режим");
            System.out.println("6 - Очередь");
            System.out.println("0 - Назад");
            System.out.print("> ");

            switch (scanner.nextLine().trim()) {
                case "1" -> player.play();
                case "2" -> player.stop();
                case "3" -> pressNext();
                case "4" -> pressPrevious();
                case "5" -> changeMode();
                case "6" -> manageQueue();
                case "0" -> inMenu = false;
                default  -> System.out.println("Неизвестная команда.");
            }
        }
    }

    private void printStatus() {
        System.out.println("Режим: " + player.getPlaybackMode().getName()
                + " Состояние: " + player.getStateName()
                + " Треков в очереди: " + player.getQueue().size());
        int idx = player.getCurrentIndex();
        List<Track> queue = player.getQueue();
        if (idx >= 0 && idx < queue.size()) {
            Track track = queue.get(idx);
            System.out.println("Трек: " + track
                    + " " + formatTime(player.getTrackProgress())
                    + "/" + formatTime(track.getDurationSeconds()));
        }
    }

    private String formatTime(int seconds) {
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }

    private void pressNext() {
        if (player.getQueue().isEmpty()) {
            System.out.println("Очередь пуста.");
            return;
        }
        if (!player.next()) {
            System.out.println("Конец плейлиста.");
            player.stop();
        }
    }

    private void pressPrevious() {
        if (player.getQueue().isEmpty()) {
            System.out.println("Очередь пуста.");
            return;
        }
        player.previous();
    }

    private void changeMode() {
        System.out.println("1 - Последовательный");
        System.out.println("2 - Случайный");
        System.out.println("3 - Повтор трека");
        System.out.println("4 - Повтор плейлиста");
        System.out.println("0 - Отмена");
        System.out.print("> ");

        switch (scanner.nextLine().trim()) {
            case "1" -> player.setPlaybackMode(new SequentialMode());
            case "2" -> player.setPlaybackMode(new ShuffleMode());
            case "3" -> player.setPlaybackMode(new RepeatOneMode());
            case "4" -> player.setPlaybackMode(new RepeatPlaylistMode());
            case "0" -> { return; }
            default  -> { System.out.println("Неизвестный режим."); return; }
        }
        System.out.println("Режим: " + player.getPlaybackMode().getName());
    }

    private void manageQueue() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("Очередь");
            showQueue();
            System.out.println("1 - Удалить трек");
            System.out.println("2 - Добавить трек из библиотеки");
            System.out.println("3 - Переместить вверх");
            System.out.println("4 - Переместить вниз");
            System.out.println("5 - Перейти к треку");
            System.out.println("6 - Очистить очередь");
            System.out.println("0 - Назад");
            System.out.print("> ");

            switch (scanner.nextLine().trim()) {
                case "1" -> removeFromQueue();
                case "2" -> addToQueue();
                case "3" -> moveInQueue(-1);
                case "4" -> moveInQueue(+1);
                case "5" -> jumpToTrack();
                case "6" -> clearQueue();
                case "0" -> inMenu = false;
                default  -> System.out.println("Неизвестная команда.");
            }
        }
    }

    private void showQueue() {
        List<Track> queue = player.getQueue();
        if (queue.isEmpty()) {
            System.out.println("Очередь пуста.");
            return;
        }
        int current = player.getCurrentIndex();
        for (int i = 0; i < queue.size(); i++) {
            if (i == current) {
                System.out.println((i + 1) + ". " + queue.get(i) + " <");
            } else {
                System.out.println((i + 1) + ". " + queue.get(i));
            }
        }
    }

    private void removeFromQueue() {
        List<Track> queue = player.getQueue();
        if (queue.isEmpty()) { System.out.println("Очередь пуста."); return; }
        System.out.print("Номер трека (0 - отмена): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= queue.size()) { System.out.println("Отмена."); return; }
            commandExecutor.executeCommand(new RemoveTrackCommand(player, idx));
        } catch (NumberFormatException e) {
            System.out.println("Введите число.");
        }
    }

    private void addToQueue() {
        List<Track> library = app.getLibrary();
        if (library.isEmpty()) { System.out.println("Библиотека пуста."); return; }
        for (int i = 0; i < library.size(); i++) {
            System.out.println((i + 1) + ". " + library.get(i));
        }
        System.out.print("Номер трека (0 - отмена): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= library.size()) { System.out.println("Отмена."); return; }
            commandExecutor.executeCommand(new AddTrackCommand(player, library.get(idx)));
        } catch (NumberFormatException e) {
            System.out.println("Введите число.");
        }
    }

    private void moveInQueue(int direction) {
        List<Track> queue = player.getQueue();
        if (queue.size() < 2) { System.out.println("Недостаточно треков."); return; }
        System.out.print("Номер трека (0 - отмена): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            int newIdx = idx + direction;
            if (idx < 0 || idx >= queue.size() || newIdx < 0 || newIdx >= queue.size()) {
                System.out.println("Невозможно переместить.");
                return;
            }
            commandExecutor.executeCommand(new MoveTrackCommand(player, idx, direction));
        } catch (NumberFormatException e) {
            System.out.println("Введите число.");
        }
    }

    private void jumpToTrack() {
        List<Track> queue = player.getQueue();
        if (queue.isEmpty()) { System.out.println("Очередь пуста."); return; }
        System.out.print("Номер трека (0 - отмена): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= queue.size()) { System.out.println("Отмена."); return; }
            commandExecutor.executeCommand(new JumpToTrackCommand(player, idx));
        } catch (NumberFormatException e) {
            System.out.println("Введите число.");
        }
    }

    private void clearQueue() {
        if (player.getQueue().isEmpty()) { System.out.println("Очередь уже пуста."); return; }
        System.out.print("Очистить очередь? (д/н): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("д")) {
            commandExecutor.executeCommand(new ClearQueueCommand(player));
        }
    }
}
