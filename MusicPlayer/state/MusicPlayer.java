package MusicPlayer.state;

import MusicPlayer.modes.PlaybackMode;
import MusicPlayer.modes.SequentialMode;
import MusicPlayer.builder.Track;
import MusicPlayer.observer.PlayerObserver;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {

    private PlayerState currentState;

    private List<Track> queue = new ArrayList<>();
    private int currentIndex = -1;

    private PlaybackMode playbackMode = new SequentialMode();

    private final List<PlayerObserver> observers = new ArrayList<>();

    public MusicPlayer() {
        this.currentState = new PausedState();
    }

    public void play() {
        currentState.play(this);
    }

    public void pause() {
        currentState.pause(this);
    }

    public boolean next() {
        if (queue.isEmpty()) return false;
        int nextIdx = playbackMode.nextIndex(queue, currentIndex);
        if (nextIdx < 0) return false;
        currentIndex = nextIdx;
        notifyTrackChanged(queue.get(currentIndex));
        return true;
    }

    public void previous() {
        if (queue.isEmpty()) return;
        currentIndex = playbackMode.previousIndex(queue, currentIndex);
        notifyTrackChanged(queue.get(currentIndex));
    }

    public void setState(PlayerState state) {
        this.currentState = state;
    }

    public PlayerState getCurrentState() { return currentState; }
    public String getStateName() { return currentState.getName(); }

    public List<Track> getQueue() { return queue; }
    public int getCurrentIndex() { return currentIndex; }

    public void setQueue(List<Track> newQueue) {
        this.queue = new ArrayList<>(newQueue);
        this.currentIndex = queue.isEmpty() ? -1 : 0;
    }

    public void setCurrentIndex(int index) { this.currentIndex = index; }

    public PlaybackMode getPlaybackMode() { return playbackMode; }
    public void setPlaybackMode(PlaybackMode mode) { this.playbackMode = mode; }

    public void addObserver(PlayerObserver observer) { observers.add(observer); }

    public void notifyTrackChanged(Track track) {
        for (PlayerObserver observer : observers) {
            observer.onTrackChanged(track);
        }
    }

}
