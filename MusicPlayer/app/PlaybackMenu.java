package MusicPlayer.app;

import MusicPlayer.builder.Track;
import MusicPlayer.modes.RepeatOneMode;
import MusicPlayer.modes.RepeatPlaylistMode;
import MusicPlayer.modes.SequentialMode;
import MusicPlayer.modes.ShuffleMode;
import MusicPlayer.state.MusicPlayer;

import java.util.List;
import java.util.Scanner;

public class PlaybackMenu {

    private final MusicPlayer player;
    private final Scanner scanner;

    public PlaybackMenu(PlayerApp app) {
        this.player = app.getPlayer();
        this.scanner = app.getScanner();
    }

    public void open() {
        boolean inMenuFlag = true;
        while (inMenuFlag) {
            System.out.println("Управление воспроизведением");
            printStatus();
            System.out.println("1 - Play");
            System.out.println("2 - Pause");
            System.out.println("3 - Next");
            System.out.println("4 - Previous");
            System.out.println("5 - Сменить режим");
            System.out.println("6 - Очистить очередь");
            System.out.println("0 - Назад");
            System.out.print("> ");

            switch (scanner.nextLine().trim()) {
                case "1":
                    player.play();
                    break;
                case "2":
                    player.pause();
                    break;
                case "3":
                    pressNext();
                    break;
                case "4":
                    pressPrevious();
                    break;
                case "5":
                    changeMode();
                    break;
                case "6":
                    clearQueue();
                    break;
                case "0":
                    inMenuFlag = false;
                    break;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }

    private void printStatus() {
        List<Track> queue = player.getQueue();
        System.out.println("Режим: " + player.getPlaybackMode().getName()
                + " Состояние: " + player.getStateName()
                + " Треков в очереди: " + queue.size());
        int idx = player.getCurrentIndex();
        if (idx >= 0 && idx < queue.size()) {
            System.out.println("Трек: " + queue.get(idx));
        }
    }

    private void pressNext() {
        if (player.getQueue().isEmpty()) {
            System.out.println("Очередь пуста.");
            return;
        }
        if (!player.next()) {
            System.out.println("Конец плейлиста.");
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
        System.out.println("Режим воспроизведения");
        System.out.println("1 - Последовательный");
        System.out.println("2 - Случайный");
        System.out.println("3 - Повтор трека");
        System.out.println("4 - Повтор плейлиста");
        System.out.println("0 - Назад");
        System.out.print("> ");

        switch (scanner.nextLine().trim()) {
            case "1":
                player.setPlaybackMode(new SequentialMode());
                break;
            case "2":
                player.setPlaybackMode(new ShuffleMode());
                break;
            case "3":
                player.setPlaybackMode(new RepeatOneMode());
                break;
            case "4":
                player.setPlaybackMode(new RepeatPlaylistMode());
                break;
            case "0":
                return;
            default:
                System.out.println("Неизвестный режим.");
                return;
        }
        System.out.println("Режим: " + player.getPlaybackMode().getName());
    }

    private void clearQueue() {
        if (player.getQueue().isEmpty()) {
            System.out.println("Очередь уже пуста.");
            return;
        }
        System.out.print("Очистить очередь? (д/н): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("д")) {
            player.getQueue().clear();
            player.setCurrentIndex(-1);
            System.out.println("Очередь очищена.");
        }
    }
}
