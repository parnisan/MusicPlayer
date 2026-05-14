package MusicPlayer.app;

import MusicPlayer.builder.Track;
import MusicPlayer.filter.ArtistFilter;
import MusicPlayer.filter.GenreFilter;
import MusicPlayer.filter.LibraryFilter;
import MusicPlayer.filter.SortByArtistFilter;
import MusicPlayer.filter.SortByDurationFilter;
import MusicPlayer.filter.TrackFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FilterMenu {

    private final PlayerApp app;
    private final Scanner scanner;

    public FilterMenu(PlayerApp app) {
        this.app = app;
        this.scanner = app.getScanner();
    }

    public void open() {
        if (app.getLibrary().isEmpty()) {
            System.out.println("Библиотека пуста.");
            return;
        }

        System.out.println("Фильтрация");
        System.out.println("1 - По жанру");
        System.out.println("2 - По исполнителю");
        System.out.println("3 - Сортировка по длительности");
        System.out.println("4 - Сортировка по исполнителю");
        System.out.println("0 - Назад");
        System.out.print("> ");

        String choice = scanner.nextLine().trim();
        if (choice.equals("0")) return;

        TrackFilter source = new LibraryFilter(app.getLibrary());
        TrackFilter filter = buildFilter(choice, source);
        if (filter == null) return;


        List<Track> result = filter.getTracks();

        if (result.isEmpty()) {
            System.out.println("Ничего не найдено.");
            return;
        }

        System.out.println("Результат (" + result.size() + "):");
        for (int i = 0; i < result.size(); i++) {
            System.out.println((i + 1) + ". " + result.get(i));
        }

        System.out.print("Загрузить результат в очередь? (д/н): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("д")) {
            app.getPlayer().setQueue(result);
            System.out.println("Очередь загружена: " + result.size() + " треков.");
        }
    }

    private TrackFilter buildFilter(String choice, TrackFilter source) {
        switch (choice) {
            case "1":
                System.out.print("Жанр: ");
                return new GenreFilter(source, scanner.nextLine().trim());
            case "2":
                System.out.print("Исполнитель: ");
                return new ArtistFilter(source, scanner.nextLine().trim());
            case "3":
                return new SortByDurationFilter(source);
            case "4":
                return new SortByArtistFilter(source);
            default:
                System.out.println("Неизвестная команда.");
                return null;
        }
    }
}
