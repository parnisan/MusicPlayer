package MusicPlayer.command;

import java.util.ArrayList;
import java.util.List;


public class QueueCommandExecutor {

    private final List<QueueCommand> history = new ArrayList<>();

    public void executeCommand(QueueCommand command) {
        command.execute();
        history.add(command);
    }
}
