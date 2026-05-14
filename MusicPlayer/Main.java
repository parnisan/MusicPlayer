package MusicPlayer;

import MusicPlayer.app.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        PlayerApp app = new PlayerApp(scanner);

        TrackMenu trackMenu = new TrackMenu(app);
        PlaylistMenu playlistMenu = new PlaylistMenu(app, trackMenu);
        PlaybackMenu playbackMenu = new PlaybackMenu(app);
        FilterMenu filterMenu = new FilterMenu(app);

        boolean runningFlag = true;
        while (runningFlag) {
            System.out.println();
            System.out.println("1. Треки");
            System.out.println("2. Плейлисты");
            System.out.println("3. Воспроизведение");
            System.out.println("4. Фильтрация");
            System.out.println("5. История");
            System.out.println("0. Выход");
            System.out.print("> ");

            switch (scanner.nextLine().trim()) {
                case "1":
                    trackMenu.open();
                    break;
                case "2":
                    playlistMenu.open();
                    break;
                case "3":
                    playbackMenu.open();
                    break;
                case "4":
                    filterMenu.open();
                    break;
                case "5":
                    app.getHistoryObserver().printHistory();
                    break;
                case "0":
                    runningFlag = false;
                    break;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }
}
