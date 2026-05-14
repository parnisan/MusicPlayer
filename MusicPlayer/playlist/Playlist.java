package MusicPlayer.playlist;

import MusicPlayer.builder.Track;

import java.util.ArrayList;
import java.util.List;

public class Playlist implements PlaylistComponent {

    private final String name;
    private final List<PlaylistComponent> children;

    public Playlist(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя плейлиста не может быть пустым");
        }
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void add(PlaylistComponent component) {
        children.add(component);
    }

    public void remove(PlaylistComponent component) {
        children.remove(component);
    }

    @Override
    public List<PlaylistComponent> getChildren() {
        return children;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getTotalDuration() {
        int total = 0;
        for (PlaylistComponent child : children) {
            total += child.getTotalDuration();
        }
        return total;
    }

    @Override
    public List<Track> getTracks() {
        List<Track> result = new ArrayList<>();
        for (PlaylistComponent child : children) {
            result.addAll(child.getTracks());
        }
        return result;
    }

    @Override
    public void print(String indent) {
        int dur = getTotalDuration();
        System.out.println(indent + "[" + name + "] " + dur / 60 + ":" +  dur % 60);
        for (PlaylistComponent child : children) {
            child.print(indent + "  ");
        }
    }

    @Override
    public void collectContainers(List<Playlist> result) {
        result.add(this);
        for (PlaylistComponent child : children) {
            child.collectContainers(result);
        }
    }
}
