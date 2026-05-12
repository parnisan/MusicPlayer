package MusicPlayer.command;

import MusicPlayer.state.MusicPlayer;

public class ClearQueueCommand implements QueueCommand {

    private final MusicPlayer player;

    public ClearQueueCommand(MusicPlayer player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.stop();
        player.getQueue().clear();
        player.setCurrentIndex(-1);
        System.out.println("Очередь очищена.");
    }
}
