package MusicPlayer.app;

import MusicPlayer.builder.Track;

import java.util.List;
import java.util.Scanner;

public class TrackMenu {

    private final PlayerApp app;
    private final Scanner scanner;

    public TrackMenu(PlayerApp app) {
        this.app = app;
        this.scanner = app.getScanner();
    }

    public void open() {
        boolean inMenuFlag = true;
        while (inMenuFlag) {
            System.out.println("Треки");
            System.out.println("1 - Добавить трек");
            System.out.println("2 - Показать все треки");
            System.out.println("0 - Назад");
            System.out.print("> ");

            switch (scanner.nextLine().trim()) {
                case "1":
                    createTrack();
                    break;
                case "2":
                    showLibrary();
                    break;
                case "0":
                    inMenuFlag = false;
                    break;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }

    private void createTrack() {
        System.out.print("Название: ");
        String title = scanner.nextLine().trim();
        System.out.print("Исполнитель: ");
        String artist = scanner.nextLine().trim();
        System.out.print("Жанр (Enter - пропустить): ");
        String genre = scanner.nextLine().trim();
        System.out.print("Длительность сек (Enter - пропустить): ");
        String durStr = scanner.nextLine().trim();
        System.out.print("Битрейт кбит/с (Enter - пропустить): ");
        String brStr = scanner.nextLine().trim();

        try {
            Track.Builder b = new Track.Builder(title, artist);
            if (!genre.isEmpty()) b.genre(genre);
            if (!durStr.isEmpty()) b.durationSeconds(Integer.parseInt(durStr));
            if (!brStr.isEmpty()) b.bitrate(Integer.parseInt(brStr));
            Track track = b.build();
            app.getLibrary().add(track);
            System.out.println("Добавлен: " + track);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public void showLibrary() {
        List<Track> library = app.getLibrary();
        if (library.isEmpty()) {
            System.out.println("Библиотека пуста.");
            return;
        }
        for (int i = 0; i < library.size(); i++) {
            System.out.println((i + 1) + ". " + library.get(i));
        }
    }
}
