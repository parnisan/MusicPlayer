package MusicPlayer.playlist;

import MusicPlayer.builder.Track;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Playlist implements PlaylistComponent, Iterable<Track> {

    private final String name;
    private final List<PlaylistComponent> children;

    public Playlist(String name) {
        if (name == null || name.isBlank()) {
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
    public void print(String indent) {
        int dur = getTotalDuration();
        System.out.println(indent + "[" + name + "] " + dur / 60 + ":" + String.format("%02d", dur % 60));
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

    @Override
    public Iterator<Track> iterator() {
        return new Iterator<Track>() {

            private final Deque<Iterator<PlaylistComponent>> stack = new ArrayDeque<>();
            private Track nextTrack = null;

            {
                stack.push(children.iterator());
                advance();
            }

            private void advance() {
                nextTrack = null;
                while (!stack.isEmpty() && nextTrack == null) {
                    Iterator<PlaylistComponent> top = stack.peek();
                    if (!top.hasNext()) {
                        stack.pop();
                    } else {
                        PlaylistComponent component = top.next();
                        Optional<Track> maybeTrack = component.asTrack();
                        if (maybeTrack.isPresent()) {
                            nextTrack = maybeTrack.get();
                        } else {
                            stack.push(component.getChildren().iterator());
                        }
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return nextTrack != null;
            }

            @Override
            public Track next() {
                if (nextTrack == null) throw new NoSuchElementException();
                Track result = nextTrack;
                advance();
                return result;
            }
        };
    }

    @Override
    public String toString() {
        return "Playlist{name='" + name + "', children=" + children.size() + '}';
    }
}
