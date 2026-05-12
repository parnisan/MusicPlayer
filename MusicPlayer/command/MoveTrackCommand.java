package MusicPlayer.command;

import MusicPlayer.builder.Track;
import MusicPlayer.state.MusicPlayer;

import java.util.List;

public class MoveTrackCommand implements QueueCommand {

    private final MusicPlayer player;
    private final int idx;
    private final int direction;
    public MoveTrackCommand(MusicPlayer player, int idx, int direction) {
        this.player = player;
        this.idx = idx;
        this.direction = direction;
    }

    @Override
    public void execute() {
        List<Track> queue = player.getQueue();
        int newIdx = idx + direction;
        Track moved = queue.remove(idx);
        queue.add(newIdx, moved);

        int current = player.getCurrentIndex();
        if (idx == current) {
            player.setCurrentIndex(newIdx);
        } else if (idx < current && newIdx >= current) {
            player.setCurrentIndex(current - 1);
        } else if (idx > current && newIdx <= current) {
            player.setCurrentIndex(current + 1);
        }

        System.out.println("Трек перемещён.");
    }
}
