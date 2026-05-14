package MusicPlayer.app;

import MusicPlayer.builder.Track;
import MusicPlayer.playlist.Playlist;
import MusicPlayer.playlist.PlaylistComponent;
import MusicPlayer.playlist.TrackItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlaylistMenu {

    private final PlayerApp app;
    private final TrackMenu trackMenu;
    private final Scanner scanner;

    public PlaylistMenu(PlayerApp app, TrackMenu trackMenu) {
        this.app = app;
        this.trackMenu = trackMenu;
        this.scanner = app.getScanner();
    }

    public void open() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("Плейлисты");
            System.out.println("1 - Моя библиотека");
            System.out.println("2 - Добавить трек в плейлист");
            System.out.println("3 - Создать вложенный плейлист");
            System.out.println("4 - Удалить элемент из плейлиста");
            System.out.println("5 - Загрузить плейлист в очередь");
            System.out.println("0 - Назад");
            System.out.print("> ");

            switch (scanner.nextLine().trim()) {
                case "1":
                    app.getRootPlaylist().print("  ");
                    break;
                case "2":
                    addTrackToPlaylist();
                    break;
                case "3":
                    createNestedPlaylist();
                    break;
                case "4":
                    removeFromPlaylist();
                    break;
                case "5":
                    loadQueueFromPlaylist();
                    break;
                case "0":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }

    private List<Playlist> collectAllPlaylists() {
        List<Playlist> result = new ArrayList<>();
        app.getRootPlaylist().collectContainers(result);
        return result;
    }

    private Playlist choosePlaylist(String prompt) {
        List<Playlist> playlistList = collectAllPlaylists();
        System.out.println("Плейлисты:");
        for (int i = 0; i < playlistList.size(); i++) {
            Playlist p = playlistList.get(i);
            int dur = p.getTotalDuration();
            System.out.println((i + 1) + ". " + p.getName() + " " + dur / 60 + ":" + dur % 60);
        }
        System.out.println("0. Отмена");
        System.out.print(prompt);
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= playlistList.size()) {
                return null;
            }
            return playlistList.get(idx);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void addTrackToPlaylist() {
        if (app.getLibrary().isEmpty()) {
            System.out.println("Треков нет. Сначала добавьте треки.");
            return;
        }
        Playlist target = choosePlaylist("В какой плейлист добавить? > ");
        if (target == null) {
            System.out.println("Отмена.");
            return;
        }
        trackMenu.showLibrary();
        System.out.print("Номер трека (0 - отмена): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= app.getLibrary().size()) {
                System.out.println("Отмена.");
                return;
            }
            target.add(new TrackItem(app.getLibrary().get(idx)));
            System.out.println("Трек добавлен в '" + target.getName() + "'.");
        } catch (NumberFormatException e) {
            System.out.println("Введите число.");
        }
    }

    private void createNestedPlaylist() {
        Playlist target = choosePlaylist("В каком плейлисте создать? > ");
        if (target == null) {
            System.out.println("Отмена.");
            return;
        }
        System.out.print("Название нового плейлиста: ");
        String name = scanner.nextLine().trim();
        if (name.trim().isEmpty()) {
            System.out.println("Имя не может быть пустым.");
            return;
        }
        target.add(new Playlist(name));
        System.out.println("Плейлист '" + name + "' создан в '" + target.getName() + "'.");
    }

    private void removeFromPlaylist() {
        Playlist target = choosePlaylist("Из какого плейлиста удалить? > ");
        if (target == null) {
            System.out.println("Отмена.");
            return;
        }
        List<PlaylistComponent> childList = target.getChildren();
        if (childList.isEmpty()) {
            System.out.println("Плейлист '" + target.getName() + "' пуст.");
            return;
        }
        System.out.println("Содержимое '" + target.getName() + "':");
        for (int i = 0; i < childList.size(); i++) {
            System.out.println((i + 1) + ". " + childList.get(i).getName());
        }
        System.out.print("Номер для удаления (0 - отмена): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= childList.size()) {
                System.out.println("Отмена.");
                return;
            }
            PlaylistComponent removed = childList.get(idx);
            target.remove(removed);
            System.out.println("'" + removed.getName() + "' удалён из '" + target.getName() + "'.");
        } catch (NumberFormatException e) {
            System.out.println("Введите число.");
        }
    }

    private void loadQueueFromPlaylist() {
        Playlist target = choosePlaylist("Какой плейлист загрузить? > ");
        if (target == null) {
            System.out.println("Отмена.");
            return;
        }
        List<Track> trackList = target.getTracks();
        if (trackList.isEmpty()) {
            System.out.println("Плейлист '" + target.getName() + "' пуст.");
            return;
        }
        app.getPlayer().setQueue(trackList);
        System.out.println("Очередь загружена из '" + target.getName() + "': " + trackList.size() + " треков.");
    }
}
