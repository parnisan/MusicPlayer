package MusicPlayer.app;

import MusicPlayer.builder.Track;
import MusicPlayer.filter.ArtistFilter;
import MusicPlayer.filter.GenreFilter;
import MusicPlayer.filter.LibraryFilter;
import MusicPlayer.filter.SortByArtistFilter;
import MusicPlayer.filter.SortByDurationFilter;
import MusicPlayer.filter.TrackFilter;

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

        TrackFilter filter = new LibraryFilter(app.getLibrary());

        boolean filtering = true;
        while (filtering) {
            List<Track> current = filter.getTracks();
            if (current.isEmpty()) {
                System.out.println("Ничего не найдено.");
                return;
            }
            System.out.println("Треки: ");
            for (int i = 0; i < current.size(); i++) {
                System.out.println("    " + (i + 1) + ". " + current.get(i));
            }
            System.out.println("1 - По жанру");
            System.out.println("2 - По исполнителю");
            System.out.println("3 - Сортировка по длительности");
            System.out.println("4 - Сортировка по исполнителю");
            System.out.println("5 - Загрузить в очередь");
            System.out.println("0 - Отмена");
            System.out.print("> ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.print("Жанр: ");
                    filter = new GenreFilter(filter, scanner.nextLine().trim());
                    break;
                case "2":
                    System.out.print("Исполнитель: ");
                    filter = new ArtistFilter(filter, scanner.nextLine().trim());
                    break;
                case "3":
                    filter = new SortByDurationFilter(filter);
                    break;
                case "4":
                    filter = new SortByArtistFilter(filter);
                    break;
                case "5":
                    app.getPlayer().setQueue(current);
                    System.out.println("Очередь загружена: " + current.size() + " треков.");
                    filtering = false;
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }
}
