package MusicPlayer.app;

import MusicPlayer.builder.Track;
import MusicPlayer.playlist.Playlist;
import MusicPlayer.playlist.PlaylistComponent;
import MusicPlayer.playlist.PlaylistManager;
import MusicPlayer.playlist.TrackItem;

import java.util.List;
import java.util.Scanner;

public class PlaylistMenu {

    private static final String HEADER = "Плейлисты";
    private static final String OPTION_LIBRARY = "1 - Моя библиотека";
    private static final String OPTION_ADD_TRACK = "2 - Добавить трек в плейлист";
    private static final String OPTION_CREATE_NESTED = "3 - Создать вложенный плейлист";
    private static final String OPTION_REMOVE = "4 - Удалить элемент из плейлиста";
    private static final String OPTION_LOAD_QUEUE = "5 - Загрузить плейлист в очередь";
    private static final String OPTION_BACK = "0 - Назад";
    private static final String PROMPT = "> ";
    private static final String UNKNOWN_COMMAND = "Неизвестная команда.";
    private static final String EMPTY_LIBRARY = "Библиотека пуста. Сначала добавьте треки.";
    private static final String CANCEL = "Отмена.";
    private static final String NUMBER_ERROR = "Введите число.";
    private static final String PLAYLISTS_HEADER = "Плейлисты:";
    private static final String CANCEL_ITEM = "0. Отмена";
    private static final String TRACK_PROMPT = "Номер трека (0 - отмена): ";
    private static final String TRACK_ADDED_PREFIX = "Трек добавлен в '";
    private static final String TRACK_ADDED_SUFFIX = "'.";
    private static final String ADD_TO_PROMPT = "В какой плейлист добавить? > ";
    private static final String CREATE_IN_PROMPT = "В каком плейлисте создать? > ";
    private static final String NEW_PLAYLIST_NAME_PROMPT = "Название нового плейлиста: ";
    private static final String NAME_EMPTY = "Имя не может быть пустым.";
    private static final String PLAYLIST_CREATED_PREFIX = "Плейлист '";
    private static final String PLAYLIST_CREATED_MIDDLE = "' создан в '";
    private static final String PLAYLIST_CREATED_SUFFIX = "'.";
    private static final String REMOVE_FROM_PROMPT = "Из какого плейлиста удалить? > ";
    private static final String PLAYLIST_EMPTY_PREFIX = "Плейлист '";
    private static final String PLAYLIST_EMPTY_SUFFIX = "' пуст.";
    private static final String CONTENT_OF_PREFIX = "Содержимое '";
    private static final String CONTENT_OF_SUFFIX = "':";
    private static final String REMOVE_PROMPT = "Номер для удаления (0 - отмена): ";
    private static final String REMOVED_PREFIX = "'";
    private static final String REMOVED_MIDDLE = "' удалён из '";
    private static final String REMOVED_SUFFIX = "'.";
    private static final String LOAD_PLAYLIST_PROMPT = "Какой плейлист загрузить? > ";
    private static final String QUEUE_LOADED_PREFIX = "Очередь загружена из '";
    private static final String QUEUE_LOADED_MIDDLE = "': ";
    private static final String QUEUE_LOADED_SUFFIX = " треков.";

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
            System.out.println(HEADER);
            System.out.println(OPTION_LIBRARY);
            System.out.println(OPTION_ADD_TRACK);
            System.out.println(OPTION_CREATE_NESTED);
            System.out.println(OPTION_REMOVE);
            System.out.println(OPTION_LOAD_QUEUE);
            System.out.println(OPTION_BACK);
            System.out.print(PROMPT);

            switch (scanner.nextLine().trim()) {
                case "1" -> app.getRootPlaylist().print("    ");
                case "2" -> addTrackToPlaylist();
                case "3" -> createNestedPlaylist();
                case "4" -> removeFromPlaylist();
                case "5" -> loadQueueFromPlaylist();
                case "0" -> inMenu = false;
                default  -> System.out.println(UNKNOWN_COMMAND);
            }
        }
    }

    private List<Playlist> collectAllPlaylists() {
        return playlistManager.collectAllPlaylists(app.getRootPlaylist());
    }

    private Playlist choosePlaylist(String prompt) {
        List<Playlist> playlistList = collectAllPlaylists();
        System.out.println(PLAYLISTS_HEADER);
        for (int i = 0; i < playlistList.size(); i++) {
            Playlist p = playlistList.get(i);
            int dur = p.getTotalDuration();
            System.out.println((i + 1) + ". " + p.getName() + " " + dur / 60 + ":" + String.format("%02d", dur % 60));
        }
        System.out.println(CANCEL_ITEM);
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
            System.out.println(EMPTY_LIBRARY);
            return;
        }
        Playlist target = choosePlaylist(ADD_TO_PROMPT);
        if (target == null) {
            System.out.println(CANCEL);
            return;
        }
        trackMenu.showLibrary();
        System.out.print(TRACK_PROMPT);
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= app.getLibrary().size()) {
                System.out.println(CANCEL);
                return;
            }
            target.add(new TrackItem(app.getLibrary().get(idx)));
            System.out.println(TRACK_ADDED_PREFIX + target.getName() + TRACK_ADDED_SUFFIX);
        } catch (NumberFormatException e) {
            System.out.println(NUMBER_ERROR);
        }
    }

    private void createNestedPlaylist() {
        Playlist target = choosePlaylist(CREATE_IN_PROMPT);
        if (target == null) {
            System.out.println(CANCEL);
            return;
        }
        System.out.print(NEW_PLAYLIST_NAME_PROMPT);
        String name = scanner.nextLine().trim();
        if (name.isBlank()) {
            System.out.println(NAME_EMPTY);
            return;
        }
        target.add(new Playlist(name));
        System.out.println(PLAYLIST_CREATED_PREFIX + name + PLAYLIST_CREATED_MIDDLE + target.getName() + PLAYLIST_CREATED_SUFFIX);
    }

    private void removeFromPlaylist() {
        Playlist target = choosePlaylist(REMOVE_FROM_PROMPT);
        if (target == null) {
            System.out.println(CANCEL);
            return;
        }
        List<PlaylistComponent> childList = target.getChildren();
        if (childList.isEmpty()) {
            System.out.println(PLAYLIST_EMPTY_PREFIX + target.getName() + PLAYLIST_EMPTY_SUFFIX);
            return;
        }
        System.out.println(CONTENT_OF_PREFIX + target.getName() + CONTENT_OF_SUFFIX);
        for (int i = 0; i < childList.size(); i++) {
            System.out.println((i + 1) + ". " + childList.get(i).getName());
        }
        System.out.print(REMOVE_PROMPT);
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= childList.size()) {
                System.out.println(CANCEL);
                return;
            }
            PlaylistComponent removed = childList.get(idx);
            target.remove(removed);
            System.out.println(REMOVED_PREFIX + removed.getName() + REMOVED_MIDDLE + target.getName() + REMOVED_SUFFIX);
        } catch (NumberFormatException e) {
            System.out.println(NUMBER_ERROR);
        }
    }

    private void loadQueueFromPlaylist() {
        Playlist target = choosePlaylist(LOAD_PLAYLIST_PROMPT);
        if (target == null) {
            System.out.println(CANCEL);
            return;
        }
        List<Track> trackList = playlistManager.collectTracks(target);
        if (trackList.isEmpty()) {
            System.out.println(PLAYLIST_EMPTY_PREFIX + target.getName() + PLAYLIST_EMPTY_SUFFIX);
            return;
        }
        app.getPlayer().setQueue(trackList);
        System.out.println(QUEUE_LOADED_PREFIX + target.getName() + QUEUE_LOADED_MIDDLE + trackList.size() + QUEUE_LOADED_SUFFIX);
    }
}
