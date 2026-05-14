package MusicPlayer.app;

import MusicPlayer.builder.Track;
import MusicPlayer.filter.ArtistFilter;
import MusicPlayer.filter.GenreFilter;
import MusicPlayer.filter.SortByArtistFilter;
import MusicPlayer.filter.SortByDurationFilter;
import MusicPlayer.filter.TrackFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FilterMenu {

    private static final String HEADER = "Фильтрация";
    private static final String EMPTY_LIBRARY = "Библиотека пуста.";
    private static final String OPTION_GENRE = "1 - По жанру";
    private static final String OPTION_ARTIST = "2 - По исполнителю";
    private static final String OPTION_SORT_DURATION = "3 - Сортировка по длительности";
    private static final String OPTION_SORT_ARTIST = "4 - Сортировка по исполнителю";
    private static final String OPTION_BACK = "0 - Назад";
    private static final String PROMPT = "> ";
    private static final String GENRE_PROMPT = "Жанр: ";
    private static final String ARTIST_PROMPT = "Исполнитель: ";
    private static final String UNKNOWN_COMMAND = "Неизвестная команда.";
    private static final String NOT_FOUND = "Ничего не найдено.";
    private static final String RESULT_PREFIX = "Результат (";
    private static final String RESULT_SUFFIX = "):";
    private static final String LOAD_PROMPT = "Загрузить результат в очередь? (д/н): ";
    private static final String CONFIRM_ANSWER = "д";
    private static final String QUEUE_LOADED_PREFIX = "Очередь загружена: ";
    private static final String QUEUE_LOADED_SUFFIX = " треков.";

    private final PlayerApp app;
    private final Scanner scanner;

    public FilterMenu(PlayerApp app) {
        this.app = app;
        this.scanner = app.getScanner();
    }

    public void open() {
        if (app.getLibrary().isEmpty()) {
            System.out.println(EMPTY_LIBRARY);
            return;
        }

        System.out.println(HEADER);
        System.out.println(OPTION_GENRE);
        System.out.println(OPTION_ARTIST);
        System.out.println(OPTION_SORT_DURATION);
        System.out.println(OPTION_SORT_ARTIST);
        System.out.println(OPTION_BACK);
        System.out.print(PROMPT);

        String choice = scanner.nextLine().trim();
        if (choice.equals("0")) {
            return;
        }

        TrackFilter filter = buildFilter(choice);
        if (filter == null) {
            return;
        }

        List<Track> result = filter.apply(app.getLibrary());

        if (result.isEmpty()) {
            System.out.println(NOT_FOUND);
            return;
        }

        System.out.println(RESULT_PREFIX + result.size() + RESULT_SUFFIX);
        for (int i = 0; i < result.size(); i++) {
            System.out.println((i + 1) + ". " + result.get(i));
        }

        System.out.print(LOAD_PROMPT);
        if (scanner.nextLine().trim().equalsIgnoreCase(CONFIRM_ANSWER)) {
            app.getPlayer().setQueue(new ArrayList<>(result));
            System.out.println(QUEUE_LOADED_PREFIX + result.size() + QUEUE_LOADED_SUFFIX);
        }
    }

    private TrackFilter buildFilter(String choice) {
        switch (choice) {
            case "1":
                System.out.print(GENRE_PROMPT);
                return new GenreFilter(scanner.nextLine().trim());
            case "2":
                System.out.print(ARTIST_PROMPT);
                return new ArtistFilter(scanner.nextLine().trim());
            case "3":
                return new SortByDurationFilter();
            case "4":
                return new SortByArtistFilter();
            default:
                System.out.println(UNKNOWN_COMMAND);
                return null;
        }
    }
}
