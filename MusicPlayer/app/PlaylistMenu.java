package MusicPlayer.app;

import MusicPlayer.builder.Track;
import MusicPlayer.playlist.Playlist;
import MusicPlayer.playlist.PlaylistComponent;
import MusicPlayer.playlist.PlaylistManager;
import MusicPlayer.playlist.TrackItem;

import java.util.List;
import java.util.Scanner;


public class PlaylistMenu {

    private final PlayerApp app;
    private final TrackMenu trackMenu;
    private final Scanner scanner;

    private final PlaylistManager playlistManager = new PlaylistManager();

    public PlaylistMenu(PlayerApp app, TrackMenu trackMenu) {
        this.app = app;
        this.trackMenu = trackMenu;
        this.scanner = app.getScanner();
    }

    public void open() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println();
            System.out.println("--- Плейлисты ---");
            System.out.println("1 - Моя библиотека");
            System.out.println("2 - Добавить трек в плейлист");
            System.out.println("3 - Создать вложенный плейлист");
            System.out.println("4 - Удалить элемент из плейлиста");
            System.out.println("5 - Загрузить плейлист в очередь");
            System.out.println("0 - Назад");
            System.out.print("> ");

            switch (scanner.nextLine().trim()) {
                case "1" -> app.getRootPlaylist().print("    ");
                case "2" -> addTrackToPlaylist();
                case "3" -> createNestedPlaylist();
                case "4" -> removeFromPlaylist();
                case "5" -> loadQueueFromPlaylist();
                case "0" -> inMenu = false;
                default  -> System.out.println("Неизвестная команда.");
            }
        }
    }

    private List<Playlist> collectAllPlaylists() {
        return playlistManager.collectAllPlaylists(app.getRootPlaylist());
    }

    private Playlist choosePlaylist(String prompt) {
        List<Playlist> all = collectAllPlaylists();
        System.out.println("Доступные плейлисты:");
        for (int i = 0; i < all.size(); i++) {
            Playlist p = all.get(i);
            int dur = p.getTotalDuration();
            System.out.printf("  %d. %s (%d:%02d)%n", i + 1, p.getName(), dur / 60, dur % 60);
        }
        System.out.println("  0. Отмена");
        System.out.print(prompt);
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= all.size()) return null;
            return all.get(idx);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void addTrackToPlaylist() {
        if (app.getLibrary().isEmpty()) {
            System.out.println("Библиотека пуста. Сначала добавьте треки.");
            return;
        }
        Playlist target = choosePlaylist("В какой плейлист добавить? > ");
        if (target == null) { System.out.println("Отмена."); return; }
        trackMenu.showLibrary();
        System.out.print("Номер трека (0 - отмена): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= app.getLibrary().size()) { System.out.println("Отмена."); return; }
            target.add(new TrackItem(app.getLibrary().get(idx)));
            System.out.println("Трек добавлен в '" + target.getName() + "'.");
        } catch (NumberFormatException e) {
            System.out.println("Введите число.");
        }
    }

    private void createNestedPlaylist() {
        Playlist target = choosePlaylist("В каком плейлисте создать? > ");
        if (target == null) { System.out.println("Отмена."); return; }
        System.out.print("Название нового плейлиста: ");
        String name = scanner.nextLine().trim();
        if (name.isBlank()) { System.out.println("Имя не может быть пустым."); return; }
        target.add(new Playlist(name));
        System.out.println("Плейлист '" + name + "' создан в '" + target.getName() + "'.");
    }

    private void removeFromPlaylist() {
        Playlist target = choosePlaylist("Из какого плейлиста удалить? > ");
        if (target == null) { System.out.println("Отмена."); return; }
        List<PlaylistComponent> children = target.getChildren();
        if (children.isEmpty()) { System.out.println("Плейлист '" + target.getName() + "' пуст."); return; }
        System.out.println("Содержимое '" + target.getName() + "':");
        for (int i = 0; i < children.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, children.get(i).getName());
        }
        System.out.print("Номер для удаления (0 - отмена): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= children.size()) { System.out.println("Отмена."); return; }
            PlaylistComponent removed = children.get(idx);
            target.remove(removed);
            System.out.println("'" + removed.getName() + "' удалён из '" + target.getName() + "'.");
        } catch (NumberFormatException e) {
            System.out.println("Введите число.");
        }
    }

    private void loadQueueFromPlaylist() {
        Playlist target = choosePlaylist("Какой плейлист загрузить? > ");
        if (target == null) { System.out.println("Отмена."); return; }

        List<Track> tracks = playlistManager.collectTracks(target);

        if (tracks.isEmpty()) {
            System.out.println("Плейлист '" + target.getName() + "' пуст.");
            return;
        }
        app.getPlayer().setQueue(tracks);
        System.out.println("Очередь загружена из '" + target.getName()
                + "': " + tracks.size() + " треков.");
    }
}
