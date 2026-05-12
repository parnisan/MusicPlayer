package MusicPlayer.command;

import MusicPlayer.builder.Track;
import MusicPlayer.state.MusicPlayer;

import java.util.List;

public class RemoveTrackCommand implements QueueCommand {

    private final MusicPlayer player;
    private final int idx;

    public RemoveTrackCommand(MusicPlayer player, int idx) {
        this.player = player;
        this.idx = idx;
    }

    @Override
    public void execute() {
        List<Track> queue = player.getQueue();
        Track removed = queue.remove(idx);
        int current = player.getCurrentIndex();

        if (queue.isEmpty()) {
            player.setCurrentIndex(-1);
            player.stop();
        } else if (idx == current) {
            player.stop();
            player.setCurrentIndex(Math.min(current, queue.size() - 1));
        } else if (idx < current) {
            player.setCurrentIndex(current - 1);
        }

        System.out.println("'" + removed.getTitle() + "' удалён из очереди.");
    }
}
