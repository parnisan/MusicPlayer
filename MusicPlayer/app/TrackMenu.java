package MusicPlayer.app;

import MusicPlayer.builder.Track;

import java.util.List;
import java.util.Scanner;

public class TrackMenu {

    private static final String HEADER = "Треки";
    private static final String OPTION_ADD = "1 - Добавить трек";
    private static final String OPTION_SHOW = "2 - Показать все треки";
    private static final String OPTION_BACK = "0 - Назад";
    private static final String PROMPT = "> ";
    private static final String UNKNOWN_COMMAND = "Неизвестная команда.";
    private static final String TITLE_PROMPT = "Название: ";
    private static final String ARTIST_PROMPT = "Исполнитель: ";
    private static final String GENRE_PROMPT = "Жанр (Enter - пропустить): ";
    private static final String DURATION_PROMPT = "Длительность сек (Enter - пропустить): ";
    private static final String BITRATE_PROMPT = "Битрейт кбит/с (Enter - пропустить): ";
    private static final String ADDED_PREFIX = "Добавлен: ";
    private static final String ERROR_PREFIX = "Ошибка: ";
    private static final String EMPTY_LIBRARY = "Библиотека пуста.";

    private final PlayerApp app;
    private final Scanner scanner;

    public TrackMenu(PlayerApp app) {
        this.app = app;
        this.scanner = app.getScanner();
    }

    public void open() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println(HEADER);
            System.out.println(OPTION_ADD);
            System.out.println(OPTION_SHOW);
            System.out.println(OPTION_BACK);
            System.out.print(PROMPT);

            switch (scanner.nextLine().trim()) {
                case "1" -> createTrack();
                case "2" -> showLibrary();
                case "0" -> inMenu = false;
                default  -> System.out.println(UNKNOWN_COMMAND);
            }
        }
    }

    private void createTrack() {
        System.out.print(TITLE_PROMPT);
        String title = scanner.nextLine().trim();
        System.out.print(ARTIST_PROMPT);
        String artist = scanner.nextLine().trim();
        System.out.print(GENRE_PROMPT);
        String genre = scanner.nextLine().trim();
        System.out.print(DURATION_PROMPT);
        String durStr = scanner.nextLine().trim();
        System.out.print(BITRATE_PROMPT);
        String brStr = scanner.nextLine().trim();

        try {
            Track.Builder b = new Track.Builder(title, artist);
            if (!genre.isBlank())  b.genre(genre);
            if (!durStr.isBlank()) b.durationSeconds(Integer.parseInt(durStr));
            if (!brStr.isBlank())  b.bitrate(Integer.parseInt(brStr));
            Track track = b.build();
            app.getLibrary().add(track);
            System.out.println(ADDED_PREFIX + track);
        } catch (IllegalArgumentException e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
        }
    }

    public void showLibrary() {
        List<Track> library = app.getLibrary();
        if (library.isEmpty()) {
            System.out.println(EMPTY_LIBRARY);
            return;
        }
        for (int i = 0; i < library.size(); i++) {
            System.out.println((i + 1) + ". " + library.get(i));
        }
    }
}
