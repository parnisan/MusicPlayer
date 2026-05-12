package MusicPlayer.command;

import MusicPlayer.builder.Track;
import MusicPlayer.state.MusicPlayer;

public class AddTrackCommand implements QueueCommand {

    private final MusicPlayer player;
    private final Track track;


    public AddTrackCommand(MusicPlayer player, Track track) {
        this.player = player;
        this.track = track;
    }

    @Override
    public void execute() {
        player.getQueue().add(track);
        if (player.getCurrentIndex() < 0) {
            player.setCurrentIndex(0);
        }
        System.out.println("'" + track.getTitle() + "' добавлен в очередь.");
    }
}
