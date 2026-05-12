package MusicPlayer.command;

import MusicPlayer.state.MusicPlayer;

public class JumpToTrackCommand implements QueueCommand {

    private final MusicPlayer player;
    private final int idx;

    public JumpToTrackCommand(MusicPlayer player, int idx) {
        this.player = player;
        this.idx = idx;
    }

    @Override
    public void execute() {
        player.stop();
        player.setCurrentIndex(idx);
        player.setTrackProgress(0);
        System.out.println("Текущий трек: " + player.getQueue().get(idx));
    }
}
